package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoteTests {

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
        String username = "andrew";
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
    public void testNoteCreation() {
        // CREATE NOTE
        String title = "Test Note Title";
        String description = "Test Note Description.";
        home.createNote(title, description, driver);
        result.goHome();
        Note note = home.readFirstNote(driver);

        // VALIDATE NOTE EXISTS
        assertEquals(title, note.noteTitle);
        assertEquals(description, note.noteDescription);
    }

    @Test
    public void testNoteEdit() {
        // CREATE NOTE
        String title = "Test Note Title";
        String description = "Test Note Description.";
        home.createNote(title, description, driver);
        result.goHome();

        // EDIT NOTE
        String newTitle = "Updated Note Title";
        String newDescription = "Updated Note Description.";
        home.updateNote(newTitle, newDescription, driver);
        result.goHome();
        Note note = home.readFirstNote(driver);

        // VALIDATE NOTE HAS BEEN UPDATED
        assertEquals(newTitle, note.noteTitle);
        assertEquals(newDescription, note.noteDescription);
    }

    @Test
    public void testNoteDelete() {
        // CREATE NOTE
        String title = "Test Note Title";
        String description = "Test Note Description.";
        home.createNote(title, description, driver);
        result.goHome();

        // DELETE NOTE
        home.deleteNote(driver);
        result.goHome();

        // VALIDATE NOTE HAS BEEN UPDATED
        assertEquals(home.checkNotesEmpty(driver), true);
    }
}
