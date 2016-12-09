/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stackleader.brms.training.model;

/**
 *
 * @author john.eckstein
 */
public class Borrower {
    private Customer customer;
    private Relationship relationship;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Relationship getRelationship() {
        return relationship;
    }

    public void setRelationship(Relationship relationship) {
        this.relationship = relationship;
    }
    
    

    public enum Relationship {
        APPLICANT,ADDITIONAL;
    }
}
