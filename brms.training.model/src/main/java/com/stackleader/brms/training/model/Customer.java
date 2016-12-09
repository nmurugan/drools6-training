package com.stackleader.brms.training.model;

import com.google.common.collect.Lists;
import java.util.Date;
import java.util.List;

/**
 *
 * @author john.eckstein
 */
public class Customer {
    private String customerKey;
    private Date sinceDate;
    private String sin;
    private String firstName;
    private List<String> currentProducts;
    private boolean customer;
    private int age;
    private Employment employment;
    private int creditScore;
    private Status status;
    private List<Identity> identities;
    
    private String addrCountry;

    public Customer() {
        currentProducts = Lists.newArrayList();
    }

    public List<Identity> getIdentities() {
        return identities;
    }

    public void setIdentities(List<Identity> identities) {
        this.identities = identities;
    }

    
    public String getCustomerKey() {
        return customerKey;
    }

    public void setCustomerKey(String customerKey) {
        this.customerKey = customerKey;
    }
    
    public String getAddrCountry() {
        return addrCountry;
    }

    public void setAddrCountry(String addrCountry) {
        this.addrCountry = addrCountry;
    }
    
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    
    public enum Status {
        DECEASED, CLOSED, OPEN;
    }
    
    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    
    public Employment getEmployment() {
        return employment;
    }

    public void setEmployment(Employment employment) {
        this.employment = employment;
    }
    
    public enum Employment {
        STUDENT, SELFEMPLOYED;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    
    public boolean isCustomer() {
        return customer;
    }

    public void setCustomer(boolean customer) {
        this.customer = customer;
    }
    
    public List<String> getCurrentProducts() {
        return currentProducts;
    }

    public void setCurrentProducts(List<String> currentProducts) {
        this.currentProducts = currentProducts;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getSin() {
        return sin;
    }

    public void setSin(String sin) {
        this.sin = sin;
    }
    
    public Date getSinceDate() {
        return sinceDate;
    }

    public void setSinceDate(Date sinceDate) {
        this.sinceDate = sinceDate;
    }
    
    
}
