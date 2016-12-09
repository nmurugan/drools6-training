package com.stackleader.brms.training.rules;

import com.google.common.collect.Lists;
import com.stackleader.brms.training.model.Borrower;
import com.stackleader.brms.training.model.CreditCardApplication;
import com.stackleader.brms.training.model.Customer;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.command.Command;
import org.kie.api.runtime.ExecutionResults;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.command.CommandFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author john.eckstein
 */
public class Ex1DroolsFeaturesTest {

    private static final Logger LOG = LoggerFactory.getLogger(Ex1DroolsFeaturesTest.class);

    private KieContainer kcont;

    @Before
    public void before() {
        KieServices ks = KieServices.Factory.get();

        kcont = ks.newKieClasspathContainer(getClass().getClassLoader());

    }

    @Test
    public void testExists() {
        KieBase kbase = kcont.getKieBase("existsKbase");
        StatelessKieSession ksession = kbase.newStatelessKieSession();
        ksession.setGlobal("LOG", LOG);

        Customer customer1 = new Customer();
        Customer customer2 = new Customer();

        List<Command> cmds = Lists.newArrayList();
        cmds.add(CommandFactory.newInsertElements(Lists.newArrayList(customer1, customer2)));
        ExecutionResults results = ksession.execute(CommandFactory.newBatchExecution(cmds));
    }

    @Test
    public void testNot() {
        KieBase kbase = kcont.getKieBase("notKbase");
        StatelessKieSession ksession = kbase.newStatelessKieSession();
        ksession.setGlobal("LOG", LOG);

        List<Command> cmds = Lists.newArrayList();
        ExecutionResults results = ksession.execute(CommandFactory.newBatchExecution(cmds));
    }

    @Test
    public void testDateLiteral() {
        KieBase kbase = kcont.getKieBase("dateKbase");
        StatelessKieSession ksession = kbase.newStatelessKieSession();
        ksession.setGlobal("LOG", LOG);

        Customer customer = new Customer();
        customer.setSinceDate(new Date());

        List<Command> cmds = Lists.newArrayList();
        cmds.add(CommandFactory.newInsert(customer));
        ExecutionResults results = ksession.execute(CommandFactory.newBatchExecution(cmds));
    }

    @Test
    public void testMatches() {
        KieBase kbase = kcont.getKieBase("matchesKbase");
        StatelessKieSession ksession = kbase.newStatelessKieSession();
        ksession.setGlobal("LOG", LOG);

        Customer customer = new Customer();
        customer.setSin("100000000");

        List<Command> cmds = Lists.newArrayList();
        cmds.add(CommandFactory.newInsert(customer));
        ExecutionResults results = ksession.execute(CommandFactory.newBatchExecution(cmds));
    }
    
    @Test
    public void testContains() {
        KieBase kbase = kcont.getKieBase("containsKbase");
        StatelessKieSession ksession = kbase.newStatelessKieSession();
        ksession.setGlobal("LOG", LOG);

        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.getCurrentProducts().addAll(Lists.newArrayList("TRAVEL","REWARDS"));

        List<Command> cmds = Lists.newArrayList();
        cmds.add(CommandFactory.newInsert(customer));
        ExecutionResults results = ksession.execute(CommandFactory.newBatchExecution(cmds));
    }
    
    @Test
    public void testMemberOf() {
        KieBase kbase = kcont.getKieBase("memberOfKbase");
        StatelessKieSession ksession = kbase.newStatelessKieSession();
        ksession.setGlobal("LOG", LOG);

        CreditCardApplication application = new CreditCardApplication();
        application.setProductCode("TRAVEL");
        
        List<Command> cmds = Lists.newArrayList();
        cmds.add(CommandFactory.newInsert(application));
        ExecutionResults results = ksession.execute(CommandFactory.newBatchExecution(cmds));
    }
    
    @Test
    public void testFrom() {
        KieBase kbase = kcont.getKieBase("fromKbase");
        StatelessKieSession ksession = kbase.newStatelessKieSession();
        ksession.setGlobal("LOG", LOG);

        Borrower borrower = new Borrower();
        borrower.setRelationship(Borrower.Relationship.APPLICANT);
        Customer customer = new Customer();
        customer.setFirstName("Adam");
        borrower.setCustomer(customer);
        
        List<Command> cmds = Lists.newArrayList();
        cmds.add(CommandFactory.newInsert(borrower));
        ExecutionResults results = ksession.execute(CommandFactory.newBatchExecution(cmds));
    }
    
    @Test
    public void testCollect() {
        KieBase kbase = kcont.getKieBase("collectKbase");
        StatelessKieSession ksession = kbase.newStatelessKieSession();
        ksession.setGlobal("LOG", LOG);

        Borrower borrower1 = new Borrower();
        Borrower borrower2 = new Borrower();
        
        
        List<Command> cmds = Lists.newArrayList();
        cmds.add(CommandFactory.newInsertElements(Lists.newArrayList(borrower1, borrower2)));
        ExecutionResults results = ksession.execute(CommandFactory.newBatchExecution(cmds));
    }
    
    @Test
    public void testForall() {
        KieBase kbase = kcont.getKieBase("forallKbase");
        StatelessKieSession ksession = kbase.newStatelessKieSession();
        ksession.setGlobal("LOG", LOG);

        Borrower borrower1 = new Borrower();
        borrower1.setCustomer(new Customer());
        borrower1.getCustomer().setCustomer(true);
        Borrower borrower2 = new Borrower();
        borrower2.setCustomer(new Customer());
        borrower2.getCustomer().setCustomer(true);
        
        
        List<Command> cmds = Lists.newArrayList();
        cmds.add(CommandFactory.newInsertElements(Lists.newArrayList(borrower1, borrower2)));
        ExecutionResults results = ksession.execute(CommandFactory.newBatchExecution(cmds));
    }
    
    @Test
    public void testNullcheck() {
        KieBase kbase = kcont.getKieBase("nullcheckKbase");
        StatelessKieSession ksession = kbase.newStatelessKieSession();
        ksession.setGlobal("LOG", LOG);

        CreditCardApplication application = new CreditCardApplication();
              
        List<Command> cmds = Lists.newArrayList();
        cmds.add(CommandFactory.newInsert(application));
        ExecutionResults results = ksession.execute(CommandFactory.newBatchExecution(cmds));
    }
}
