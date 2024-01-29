package com.tus.cipher.dto.accounts;

import javax.persistence.Entity;

// @Entity
public class NetEng extends SuppEng {
    
    public NetEng () {
    	
    }
    
	public NetEng (Long id, String username, String password, String role) {
		super(id, username, password, role);
	}
}
