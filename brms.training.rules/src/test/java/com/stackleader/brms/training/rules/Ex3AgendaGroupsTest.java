/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stackleader.brms.training.rules;

import com.google.common.collect.Lists;
import com.stackleader.brms.training.model.CreditCardApplication;
import com.stackleader.brms.training.model.Customer;
import com.stackleader.brms.training.model.Decision;
import java.util.List;
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

/**
 *
 * @author john.eckstein
 */
public class Ex3AgendaGroupsTest {

    private static final Logger LOG = LoggerFactory.getLogger(Ex3AgendaGroupsTest.class);

    private KieContainer kcont;

    @Before
    public void before() {
        KieServices ks = KieServices.Factory.get();
        kcont = ks.newKieClasspathContainer(getClass().getClassLoader());
    }

    @Test
    public void testAgendaGroupsInRules() {
        KieBase kbase = kcont.getKieBase("ex3InRulesKbase");
        StatelessKieSession ksession = kbase.newStatelessKieSession();
        ksession.setGlobal("LOG", LOG);
        ksession.addEventListener(new DebugAgendaEventListener());
        Customer customer = new Customer();
        customer.setEmployment(Customer.Employment.STUDENT);
        customer.setAge(21);
        CreditCardApplication application = new CreditCardApplication();
        application.setProductCode("TRAVEL");

        List<Command> cmds = Lists.newArrayList();
        cmds.add(CommandFactory.newSetGlobal("decision", new Decision(), true));
        cmds.add(CommandFactory.newSetGlobal("transactionKey", "123", true));
        cmds.add(CommandFactory.newInsert(customer));
        cmds.add(CommandFactory.newInsert(application));
        ExecutionResults results = ksession.execute(CommandFactory.newBatchExecution(cmds));
        Decision decision = (Decision) results.getValue("decision");
        Assert.assertTrue(decision.isApproved());
    }

    @Test
    public void testAgendaGroupsInApi() {
        KieBase kbase = kcont.getKieBase("ex3InApiKbase");
        KieSession ksession = kbase.newKieSession();
        ksession.setGlobal("LOG", LOG);
        ksession.setGlobal("transactionKey", "123");
        Decision decision = new Decision();
        ksession.setGlobal("decision", decision);
        ksession.addEventListener(new DebugAgendaEventListener());
        Customer customer = new Customer();
        customer.setEmployment(Customer.Employment.STUDENT);
        customer.setAge(21);
        CreditCardApplication application = new CreditCardApplication();
        application.setProductCode("TRAVEL");
        
        ksession.insert(customer);
        ksession.insert(application);
        
        
        ksession.getAgenda().getAgendaGroup("decision").setFocus();
        ksession.getAgenda().getAgendaGroup("product").setFocus();
        ksession.getAgenda().getAgendaGroup("demographics").setFocus();
        
        ksession.fireAllRules();
        ksession.dispose();

        Assert.assertTrue(decision.isApproved());
    }
}
