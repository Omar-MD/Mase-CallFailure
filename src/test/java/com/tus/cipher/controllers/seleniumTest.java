package com.tus.cipher.controllers;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

@Ignore
public class seleniumTest {
    @Test
    void test() throws Exception {
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com");
        driver.quit();
    }
}
