package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialTests {

    @LocalServerPort
    private Integer port;

    private static WebDriver driver;
    private LoginPage login;
    private HomePage home;
    private ResultPage result;

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
        String firstName = "Andrew";
        String lastName = "Burkhart";
        String username = "credsUser";
        String password = "reallyreallybadpassword";

        driver.get("http://localhost:" + port + "/signup");
        SignupPage signup = new SignupPage(driver);
        signup.signup(firstName, lastName, username, password);

        driver.get("http://localhost:" + port + "/login");
        login = new LoginPage(driver);
        home = new HomePage(driver);
        result = new ResultPage(driver);

        login.login(username, password);
    }

    @AfterEach
    public void afterEach() {
        home.logout();
    }

    @Test
    public void credentialCreationTest() {
        // CREATE CREDENTIAL
        String url = "https://google.com";
        String username = "andrew";
        String password = "reallyreallybadpassword";
        home.createCredential(url, username, password, driver);
        result.goHome();
        Credential credential = home.readFirstCredential(driver);

        // VALIDATE CREDENTIAL EXISTS
        assertEquals(url, credential.url);
        assertEquals(username, credential.username);

        // CLEAN UP AFTER TEST
        home.deleteCredential(driver);
        result.goHome();
    }

    @Test
    public void credentialEditTest() {
        // CREATE CREDENTIAL
        String url = "https://google.com";
        String username = "andrew";
        String password = "reallyreallybadpassword";
        home.createCredential(url, username, password, driver);
        result.goHome();

        // EDIT CREDENTIAL
        String newUrl = "https://amazon.com";
        String newUsername = "andrewb";
        String newPassword = "stillaprettybadpassword";
        home.updateCredential(newUrl, newUsername, newPassword, driver);
        result.goHome();
        Credential credential = home.readFirstCredential(driver);

        // VALIDATE CREDENTIAL HAS BEEN UPDATED
        assertEquals(newUrl, credential.url);
        assertEquals(newUsername, credential.username);

        // CLEAN UP AFTER TEST
        home.deleteCredential(driver);
        result.goHome();
    }

    @Test
    public void credentialDeleteTest() {
        // CREATE CREDENTIAL
        String url = "https://google.come";
        String username = "andrew";
        String password = "reallyreallybadpassword";
        home.createCredential(url, username, password, driver);
        result.goHome();

        // DELETE CREDENTIAL
        home.deleteCredential(driver);
        result.goHome();

        // VALIDATE CREDENTIAL HAS BEEN UPDATED
        assertEquals(home.checkCredentialsEmpty(driver), true);
    }
}
