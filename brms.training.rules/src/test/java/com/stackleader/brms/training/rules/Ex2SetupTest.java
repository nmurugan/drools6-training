package com.stackleader.brms.training.rules;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.stackleader.brms.training.model.Customer;
import com.stackleader.brms.training.model.ValidationMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.command.Command;
import org.kie.api.runtime.ExecutionResults;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.command.CommandFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Ex2SetupTest {

    private static final Logger LOG = LoggerFactory.getLogger(Ex2SetupTest.class);

    private KieContainer kcont;

    @Before
    public void before() {
        KieServices ks = KieServices.Factory.get();
        kcont = ks.newKieClasspathContainer(getClass().getClassLoader());
    }

    @Test
    public void testBadGlobal() {
        KieBase kbase = kcont.getKieBase("ex2Kbase");
        StatelessKieSession ksession = kbase.newStatelessKieSession();
        ksession.setGlobal("LOG", LOG);
        ArrayList<ValidationMessage> validationMessages = Lists.newArrayList();
        ksession.setGlobal("validationExceptions", validationMessages);

        Customer customer = new Customer();

        List<Command> cmds = Lists.newArrayList();
        cmds.add(CommandFactory.newInsert(customer));
        ksession.execute(CommandFactory.newBatchExecution(cmds));
        ksession.execute(CommandFactory.newBatchExecution(cmds));
        Assert.assertEquals(2, validationMessages.size());
    }

    @Test
    public void testScopedGlobal() {
        KieBase kbase = kcont.getKieBase("ex2Kbase");
        StatelessKieSession ksession = kbase.newStatelessKieSession();
        ksession.setGlobal("LOG", LOG);

        Customer customer = new Customer();

        ExecutionResults results1 = scopedExecute(ksession, "123", customer);
        ExecutionResults results2 = scopedExecute(ksession, "456", customer);

        List<ValidationMessage> validationExceptions1 = (List<ValidationMessage>) results1.getValue("validationExceptions");
        List<ValidationMessage> validationExceptions2 = (List<ValidationMessage>) results2.getValue("validationExceptions");

        Assert.assertEquals(1, validationExceptions1.size());
        Assert.assertEquals(1, validationExceptions2.size());
    }

    private ExecutionResults scopedExecute(StatelessKieSession ksession, String transactionKey, Customer customer) {
        List<Command> cmds = Lists.newArrayList();
        cmds.add(CommandFactory.newSetGlobal("validationExceptions", Lists.newArrayList(), true));
        cmds.add(CommandFactory.newSetGlobal("transactionKey", transactionKey, true));
        cmds.add(CommandFactory.newInsert(customer));
        ExecutionResults results = ksession.execute(CommandFactory.newBatchExecution(cmds));
        return results;
    }

    @Test
    public void testPerformance() {
        KieBase kbase = kcont.getKieBase("ex2Kbase");
        StatelessKieSession ksession = kbase.newStatelessKieSession();
        ksession.setGlobal("LOG", LOG);

        Customer customer = new Customer();

        Stopwatch stopwatch = Stopwatch.createStarted();
        ExecutionResults results1 = scopedExecute(ksession, "123", customer);
        LOG.info("Drools execution time: {} ms", stopwatch.stop().elapsed(TimeUnit.MILLISECONDS));
        stopwatch.reset().start();
        ExecutionResults results2 = scopedExecute(ksession, "456", customer);
        LOG.info("Drools execution time: {} ms", stopwatch.stop().elapsed(TimeUnit.MILLISECONDS));
    }

    @Test
    public void testStatefulAsStateless() {
        KieBase kbase = kcont.getKieBase("ex2Kbase");
        KieSession ksession = kbase.newKieSession();
        ksession.setGlobal("LOG", LOG);
        ArrayList<ValidationMessage> validationExceptions = Lists.newArrayList();
        ksession.setGlobal("validationExceptions", validationExceptions);

        Customer customer = new Customer();
        ksession.insert(customer);
        ksession.fireAllRules();
        ksession.dispose();
        Assert.assertEquals(1, validationExceptions.size());
    }

    @Test
    public void testAgendaListener() {
        KieBase kbase = kcont.getKieBase("ex2Kbase");
        StatelessKieSession ksession = kbase.newStatelessKieSession();
        ksession.addEventListener(new DebugAgendaEventListener());
        ksession.setGlobal("LOG", LOG);
        List<String> transactionKeys = Lists.newArrayList("123", "456");

        transactionKeys.forEach(key -> {
            Stopwatch stopwatch = Stopwatch.createStarted();

            Customer customer = new Customer();
            customer.setAge(30);

            ExecutionResults results1 = scopedExecute(ksession, key, customer);
            LOG.info("Drools execution time: {} ms", stopwatch.stop().elapsed(TimeUnit.MILLISECONDS));
        });

    }

}
