package com.tus.cipher.dto.accounts;

import javax.persistence.Entity;

// @Entity
public class SysAdm extends Account {
    
    public SysAdm () {
    	
    }
    
    public SysAdm (Long id, String username, String password, String role) {
		super(id, username, password, role);
	}	
}
