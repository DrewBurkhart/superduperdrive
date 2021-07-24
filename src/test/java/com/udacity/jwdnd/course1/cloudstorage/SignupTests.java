package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SignupTests {

    @LocalServerPort
    private Integer port;

    private static WebDriver driver;
    private SignupPage signup;
    private LoginPage login;
    private HomePage home;

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterAll
    public static void afterAll() {
        driver.quit();
    }

    @BeforeEach
    public void beforeEach() {
        driver.get("http://localhost:" + port + "/signup");
        signup = new SignupPage(driver);
        login = new LoginPage(driver);
        home = new HomePage(driver);
    }

    @Test
    public void signupTest() {
        // CREATE USER
        String firstName = "Andrew";
        String lastName = "Burkhart";
        String username = "signupUser";
        String password = "reallyreallybadpassword";

        signup.signup(firstName, lastName, username, password);
        login.login(username, password);

        // VALIDATE LOGIN SUCCESS
        assertEquals(home.checkAccess(), "Files");

        home.logout();

        // VALIDATE LOGOUT SUCCESS
        assertEquals(login.checkLogout(), "You have been logged out");

        driver.get("http://localhost:" + port + "/home");

        // VALIDATE HOME PAGE CAN NO LONGER BE ACCESSED
        assertEquals(login.checkAccess(), "Login");
    }
}
