package com.tus.cipher.services;


import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
public class DatabaseService {

    final private String mySQL_URL = "jdbc:mysql://localhost/mase_group_project";
    final private String H2_URL = "jdbc:h2:mem:testdb";

    private DataSource ds = null;

    private boolean testing_mode = false;
    
    @Bean
    public DataSource getDataSource() {
        /*
         * If your database setting are different from the default
         * of username="root and password="root"
         * here's what to do here
         * 
         * 1. print out your systems user name. ie. run the the
         * following line:
         * 
         *      System.out.println(System.getProperty("user.name"));
         * 
         * Or just run the project application and your should see a line
         *      
         *      "System Username: <system username>"
         * 
         * This will print out your system username, copy it
         * 
         * 2. add to the switch statement below where the case
         * is your system username, and then copy the example below
         * to change the username and password
         * to fit your system. 
         * 
         * After that you are done and the application 
         * should run. Ignore step 3 unless told otherwise or 
         * working with jenkins
         * 
         * 
         *
         * [The following step only concerns the database name]
         * 3. The SQL script that is executed when the server is started
         * creates a database called "mase_group_project". If you need to change 
         * the database name use the following line of code
         *  
         *      dataSourceProperties.setUrl("database_url");
         * 
         * 
         */

        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        // dataSourceBuilder.driverClassName("org.h2.Driver");
        

        String username = System.getProperty("user.name");
        System.out.println("System Username: " + username);

        switch(username) {
            case "shaneneary":
                testing_mode = false;
                dataSourceBuilder.url(mySQL_URL);
                dataSourceBuilder.username("root");
                dataSourceBuilder.password("root");
                break;
            default:
                testing_mode = true;
                dataSourceBuilder.url(mySQL_URL);
                dataSourceBuilder.username("root");
                dataSourceBuilder.password("root");
                break;
        }

        DataSource database = dataSourceBuilder.build();

        
        ds = database;

        return database;
    }

    @EventListener
    public void onApplicationReadyEvent(ApplicationReadyEvent event) {
        System.out.println("Populating database");
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(
            false, 
            false, 
            "UTF-8", 
            // Changes the SQL file depending on whether we are 
            // in testing mode or not
            (testing_mode ? new ClassPathResource("testing_database.sql") : new ClassPathResource("database_setup.sql"))
        );
        resourceDatabasePopulator.execute(ds);
    }
}

