package com.tus.cipher.dto.accounts;

import javax.persistence.Entity;

// @Entity
public class CustomerRep extends Account {

	public CustomerRep () {
    	
    }
    
    public CustomerRep (Long id, String username, String password, String role) {
    	super(id, username, password, role);
    }
}
