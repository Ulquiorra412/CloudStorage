package com.udacity.jwdnd.course1.cloudstorage.Test;

import com.udacity.jwdnd.course1.cloudstorage.Page.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.Page.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.Page.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static com.udacity.jwdnd.course1.cloudstorage.User.UserOne.*;
import static org.apache.commons.lang3.SystemUtils.USER_NAME;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoteTest {

    private static final String FIRST_NOTE_TITLE = "First note title";
    private static final String FIRST_NOTE_DESC = "First note description";
    private static final String SECOND_NOTE_TITLE = "Second note title";
    private static final String SECOND_NOTE_DESC = "Second note description";
    private static final String THIRD_NOTE_TITLE = "Third note title";
    private static final String THIRD_NOTE_DESC = "Third note description";
    private static final String EDITED_NOTE_TITLE = "Edited note title";

    private static WebDriver driver;

    @LocalServerPort
    private Integer port;

    private static boolean isInitialized = false;

    private HomePage homePage;

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
        if (!isInitialized) {
            SignupPage signupPage = new SignupPage(driver);
            driver.get("http://localhost:" + port + "/signup");
            signupPage.signup(FIRST_NAME, LAST_NAME, USER_NAME, PASSWORD);

            LoginPage loginPage = new LoginPage(driver);
            driver.get("http://localhost:" + port + "/login");
            loginPage.login(USER_NAME, PASSWORD);

            homePage = new HomePage(driver);

            driver.get("http://localhost:" + port);
            homePage.openNotesTab();
            homePage.openNoteModal();
            homePage.addNewNote(FIRST_NOTE_TITLE, FIRST_NOTE_DESC);

            driver.get("http://localhost:" + port);
            homePage.openNotesTab();
            homePage.openNoteModal();
            homePage.addNewNote(SECOND_NOTE_TITLE, SECOND_NOTE_DESC);

            isInitialized = true;
        }

        driver.get("http://localhost:" + port);
        homePage = new HomePage(driver);
        homePage.openNotesTab();
    }

    @Test
    public void createNote() {
        homePage.openNoteModal();

        assertTrue(homePage.isNoteModalOpened());

        homePage.addNewNote(THIRD_NOTE_TITLE, THIRD_NOTE_DESC);

        assertEquals("http://localhost:" + port + "/result", driver.getCurrentUrl());

        driver.get("http://localhost:" + port);
        homePage.openNotesTab();

        assertTrue(homePage.isNoteInList(THIRD_NOTE_TITLE));
    }

    @Test
    public void editNote() {
        homePage.openNoteModalForEdit(FIRST_NOTE_TITLE);

        assertTrue(homePage.isNoteModalOpened());

        homePage.changeNote(EDITED_NOTE_TITLE);

        assertEquals("http://localhost:" + port + "/result", driver.getCurrentUrl());

        driver.get("http://localhost:" + port);
        homePage.openNotesTab();

        assertTrue(homePage.isNoteInList(EDITED_NOTE_TITLE));
    }

    @Test
    public void deleteNote() {
        homePage.deleteNote(SECOND_NOTE_TITLE);

        assertEquals("http://localhost:" + port + "/result", driver.getCurrentUrl());

        driver.get("http://localhost:" + port);
        homePage.openNotesTab();

        assertFalse(homePage.isNoteInList(SECOND_NOTE_TITLE));
    }
}
