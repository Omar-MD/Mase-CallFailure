package com.tus.cipher.dto.accounts;

import javax.persistence.Entity;

// @Entity
public class SuppEng extends CustomerRep {
    
    public SuppEng () {
    	
    }

	public SuppEng (Long id, String username, String password, String role) {
		super(id, username, password, role);
	}
}
