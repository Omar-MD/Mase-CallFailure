package com.tus.cipher.services;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

// @Configuration
public class populateDatabaseService {

    @Autowired
    private DataSource dataSource;
    
    // @EventListener(ApplicationReadyEvent.class)
    @EventListener
    public void onApplicationReadyEvent(ApplicationReadyEvent event) {
        System.out.println("Populating database");
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(
            false, 
            false, 
            "UTF-8", 
            // new ClassPathResource("testing_database.sql")
            new ClassPathResource("database_setup.sql")
        );
        resourceDatabasePopulator.execute(dataSource);
    }
}
