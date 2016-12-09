/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stackleader.brms.training.rules;

import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.AgendaEventListener;
import org.kie.api.event.rule.AgendaGroupPoppedEvent;
import org.kie.api.event.rule.AgendaGroupPushedEvent;
import org.kie.api.event.rule.BeforeMatchFiredEvent;
import org.kie.api.event.rule.MatchCancelledEvent;
import org.kie.api.event.rule.MatchCreatedEvent;
import org.kie.api.event.rule.RuleFlowGroupActivatedEvent;
import org.kie.api.event.rule.RuleFlowGroupDeactivatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author john.eckstein
 */
public class DebugAgendaEventListener implements AgendaEventListener {
    
    private static final Logger LOG = LoggerFactory.getLogger(DebugAgendaEventListener.class);
    

    public DebugAgendaEventListener() {
    }
    
    

    @Override
    public void matchCreated(MatchCreatedEvent arg0) {
    }

    @Override
    public void matchCancelled(MatchCancelledEvent arg0) {
    }

    @Override
    public void beforeMatchFired(BeforeMatchFiredEvent arg0) {
        String transactionKey = (String) arg0.getKieRuntime().getGlobal("transactionKey");
        
        LOG.info("Transaction Id: {} | rule: {}", transactionKey, arg0.getMatch().getRule().getName());
    }

    @Override
    public void afterMatchFired(AfterMatchFiredEvent arg0) {
    }

    @Override
    public void agendaGroupPopped(AgendaGroupPoppedEvent arg0) {
    }

    @Override
    public void agendaGroupPushed(AgendaGroupPushedEvent arg0) {
    }

    @Override
    public void beforeRuleFlowGroupActivated(RuleFlowGroupActivatedEvent arg0) {
    }

    @Override
    public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent arg0) {
    }

    @Override
    public void beforeRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent arg0) {
    }

    @Override
    public void afterRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent arg0) {
    }
}
