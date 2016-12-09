/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stackleader.brms.training.rules;

import com.google.common.collect.Lists;
import com.stackleader.brms.training.model.Borrower;
import com.stackleader.brms.training.model.CreditCardApplication;
import com.stackleader.brms.training.model.Decision;
import com.stackleader.brms.training.model.ValidationMessage;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
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
public class Ex4CCApplicationApprovalTest {

    private static final Logger LOG = LoggerFactory.getLogger(Ex4CCApplicationApprovalTest.class);

    private KieContainer kcont;

    @Before
    public void before() {
        KieServices ks = KieServices.Factory.get();
        kcont = ks.newKieClasspathContainer(getClass().getClassLoader());
    }

    @Ignore
    @Test
    public void testValidApplication() {
        KieBase kbase = kcont.getKieBase("ex4FullAppKbase");
        StatelessKieSession ksession = kbase.newStatelessKieSession();
        ksession.setGlobal("LOG", LOG);
        ksession.addEventListener(new DebugAgendaEventListener());
        
        CreditCardApplication application = new CreditCardApplication();
        List<Borrower> borrowers = Lists.newArrayList();
        
        ExecutionResults results = execute(ksession, borrowers, application);
        Decision decision = (Decision) results.getValue("decision");
        Assert.assertTrue(decision.isApproved());
    }
    
    @Ignore
    @Test
    public void testCardholdersNotDeceased() {
        KieBase kbase = kcont.getKieBase("ex4FullAppKbase");
        StatelessKieSession ksession = kbase.newStatelessKieSession();
        ksession.setGlobal("LOG", LOG);
        ksession.addEventListener(new DebugAgendaEventListener());
        
        CreditCardApplication application = new CreditCardApplication();
        List<Borrower> borrowers = Lists.newArrayList();
        
        ExecutionResults results = execute(ksession, borrowers, application);
        Decision decision = (Decision) results.getValue("decision");
        List<ValidationMessage> validationExceptions = (List<ValidationMessage>) results.getValue("validationExceptions");
        Assert.assertFalse(decision.isApproved());
        Assert.assertTrue(validationExceptions.stream().filter(m -> m.getCode().equals("CC.3502")).findFirst().isPresent());
    }

    private ExecutionResults execute(StatelessKieSession ksession, List<Borrower> borrowers, CreditCardApplication application) {
        List<Command> cmds = Lists.newArrayList();
        cmds.add(CommandFactory.newSetGlobal("validationExceptions", Lists.newArrayList(), true));
        cmds.add(CommandFactory.newSetGlobal("decision", new Decision(), true));
        cmds.add(CommandFactory.newSetGlobal("transactionKey", "123", true));
        cmds.add(CommandFactory.newInsertElements(borrowers));
        cmds.add(CommandFactory.newInsert(application));
        ExecutionResults results = ksession.execute(CommandFactory.newBatchExecution(cmds));
        return results;
    }
}
