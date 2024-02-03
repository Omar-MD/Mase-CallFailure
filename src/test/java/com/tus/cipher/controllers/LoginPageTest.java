package com.tus.cipher.controllers;

import org.junit.Ignore;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;


// This test uses selenium and will likily only work on Shane's machine
// It will be considered a manual test for Sprint#0

@Disabled
// @SpringBootTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @SpringBootTest
// @AutoConfigureMockMvc
@SpringJUnitConfig
class LoginPageTest {

    @LocalServerPort
    private int port;

    // @Value("${webdriver.path}")
    // private String webdriverPath;

    // @Test
    // void testAdminLogin() {
    //     // Set the path to the chromedriver executable (make sure you have it downloaded)
    //     System.setProperty("webdriver.chrome.driver", "chromedriver");

    //     // Create a new instance of the Chrome driver
    //     WebDriver driver = new ChromeDriver();

    //     // Open the webpage
    //     driver.get("http://localhost:8081/");

    //     // Find the username input field by its ID and enter "admin"
    //     WebElement usernameInput = driver.findElement(By.id("username"));
    //     usernameInput.sendKeys("admin");

    //     // Find the password input field by its ID and enter "password"
    //     WebElement passwordInput = driver.findElement(By.id("password"));
    //     passwordInput.sendKeys("password");

    //     // Find the login button by its ID and click it
    //     WebElement loginButton = driver.findElement(By.id("loginSubmit"));
    //     loginButton.click();

    //     // Close the browser window
    //     driver.quit();
    // }

    @Ignore("Selenium test configuration only work for Shane's machine, and will likily not work elsewhere")
    @Test
    void testLoginPage() {
        // Set the path to the chromedriver executable

        System.setProperty("webdriver.chrome.driver", "chromedriver");
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        System.out.println("Setting up Chrome Driver");

        // Start the Selenium WebDriver
        WebDriver driver = new ChromeDriver();
        System.out.println("Entering Try Block");

        try {
            // int port = 8081;
            String url = "http://localhost:" + port + "/";
            System.out.println("Opening url: " + url);

            // Open the web page served by Spring Boot
            driver.get(url);
            // driver.get("src/main/resources/static/index.html");

            // driver.get("https://www.google.com");

            System.out.println("Getting elements from the web page served by Spring Boot");

            // Find the username input field and enter "admin"
            WebElement usernameInput = driver.findElement(By.id("username"));
            usernameInput.sendKeys("admin");

            // Find the password input field and enter "password"
            WebElement passwordInput = driver.findElement(By.id("password"));
            passwordInput.sendKeys("password");

            // Find the login button and click it
            WebElement loginButton = driver.findElement(By.id("loginSubmit"));
            loginButton.click();

            // Validate that the login was successful (you need to adapt this based on your application)
            // For example, check if the redirected URL or any element on the next page indicates a successful login
            // String currentUrl = driver.getCurrentUrl();
            // assertEquals("http://localhost:" + port + "/success", currentUrl);

        } finally {
            // Close the browser window
            driver.quit();
        }
    }
}
