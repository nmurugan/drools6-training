/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stackleader.brms.training.model;

import java.math.BigInteger;

/**
 *
 * @author john.eckstein
 */
public class CreditCardApplication {
    private String productCode;
    private BigInteger requestedCreditLimit;

    public BigInteger getRequestedCreditLimit() {
        return requestedCreditLimit;
    }

    public void setRequestedCreditLimit(BigInteger requestedCreditLimit) {
        this.requestedCreditLimit = requestedCreditLimit;
    }
    
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
    
    
}
