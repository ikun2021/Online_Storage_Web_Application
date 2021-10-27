package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {
	@LocalServerPort
	private int port;
	private WebDriver driver;
	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}
	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}
	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void getHomePageWithoutLogin() {
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void signupAndLogout() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/signup");
		driver.findElement(By.id("inputFirstName")).sendKeys("huo");
		driver.findElement(By.id("inputLastName")).sendKeys("geu");
		driver.findElement(By.id("inputUsername")).sendKeys("huge");
		driver.findElement(By.id("inputPassword")).sendKeys("123123");
		Thread.sleep(5000);
		driver.findElement(By.id("submit-signup")).click();
		driver.get("http://localhost:" + this.port + "/login");
		driver.findElement(By.id("inputUsername")).sendKeys("huge");
		driver.findElement(By.id("inputPassword")).sendKeys("123123");
		driver.findElement(By.id("submit-login")).click();
		Thread.sleep(5000);
		Assertions.assertEquals("Home", driver.getTitle());
		driver.findElement(By.id("logout-button")).click();
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void createNote() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/signup");
		driver.findElement(By.id("inputFirstName")).sendKeys("hu");
		driver.findElement(By.id("inputLastName")).sendKeys("ge");
		driver.findElement(By.id("inputUsername")).sendKeys("huge");
		driver.findElement(By.id("inputPassword")).sendKeys("123123");
		Thread.sleep(2000);
		driver.findElement(By.id("submit-signup")).click();
		driver.get("http://localhost:" + this.port + "/login");
		driver.findElement(By.id("inputUsername")).sendKeys("huge");
		driver.findElement(By.id("inputPassword")).sendKeys("123123");
		driver.findElement(By.id("submit-login")).click();
		driver.findElement(By.id("nav-notes-tab")).click();
		Thread.sleep(2000);
		// Create note
		try {
			driver.findElement(By.id("add-new-note")).click();
			Thread.sleep(2000);
			driver.findElement(By.id("note-title")).sendKeys("Note1");
			driver.findElement(By.id("note-description")).sendKeys("first");
			driver.findElement(By.id("submit-note")).click();
			Thread.sleep(5000);
			Assertions.assertEquals("Note1", driver.findElement(By.id("show-note-title")).getText());
			Assertions.assertEquals("first", driver.findElement(By.id("show-note-description")).getText());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	@Test
	public void deleteNote() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/signup");
		driver.findElement(By.id("inputFirstName")).sendKeys("hu");
		driver.findElement(By.id("inputLastName")).sendKeys("ge");
		driver.findElement(By.id("inputUsername")).sendKeys("huge");
		driver.findElement(By.id("inputPassword")).sendKeys("123123");
		Thread.sleep(2000);
		driver.findElement(By.id("submit-signup")).click();
		driver.get("http://localhost:" + this.port + "/login");
		driver.findElement(By.id("inputUsername")).sendKeys("huge");
		driver.findElement(By.id("inputPassword")).sendKeys("123123");
		driver.findElement(By.id("submit-login")).click();
		driver.findElement(By.id("nav-notes-tab")).click();
		Thread.sleep(2000);
		try {
			driver.findElement(By.id("add-new-note")).click();
			Thread.sleep(2000);
			driver.findElement(By.id("note-title")).sendKeys("Note1");
			driver.findElement(By.id("note-description")).sendKeys("first");
			driver.findElement(By.id("submit-note")).click();
			Thread.sleep(2000);
			Assertions.assertEquals("Note1", driver.findElement(By.id("show-note-title")).getText());
			Assertions.assertEquals("first", driver.findElement(By.id("show-note-description")).getText());
		} catch(Exception e) {
			System.out.println(e.toString());
		}
		// Delete  note
		Thread.sleep(2000);
		WebElement noteTable = driver.findElement(By.id("noteTable"));
		List<WebElement> noteLink = noteTable.findElements(By.tagName("a"));
		for (WebElement deleteNoteButton : noteLink) {
			deleteNoteButton.click();
		}
		Assertions.assertEquals(0,noteTable.findElements(By.tagName("td")).size());
	}

	@Test
	public void editNote() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/signup");
		driver.findElement(By.id("inputFirstName")).sendKeys("hu");
		driver.findElement(By.id("inputLastName")).sendKeys("ge");
		driver.findElement(By.id("inputUsername")).sendKeys("huge");
		driver.findElement(By.id("inputPassword")).sendKeys("123123");
		Thread.sleep(2000);
		driver.findElement(By.id("submit-signup")).click();
		driver.get("http://localhost:" + this.port + "/login");
		driver.findElement(By.id("inputUsername")).sendKeys("huge");
		driver.findElement(By.id("inputPassword")).sendKeys("123123");
		driver.findElement(By.id("submit-login")).click();
		driver.findElement(By.id("nav-notes-tab")).click();
		Thread.sleep(2000);
		try {
			driver.findElement(By.id("add-new-note")).click();
			Thread.sleep(2000);
			driver.findElement(By.id("note-title")).sendKeys("Note1");
			driver.findElement(By.id("note-description")).sendKeys("first");
			driver.findElement(By.id("submit-note")).click();
			Thread.sleep(2000);
			Assertions.assertEquals("Note1", driver.findElement(By.id("show-note-title")).getText());
			Assertions.assertEquals("first", driver.findElement(By.id("show-note-description")).getText());
		} catch(Exception e) {
			System.out.println(e.toString());
		}

		// Edit note
		Thread.sleep(2000);
		WebElement noteTable = driver.findElement(By.id("noteTable"));
		List<WebElement> noteList = noteTable.findElements(By.tagName("td"));
		for (WebElement row : noteList) {
			WebElement editButton;
			editButton = row.findElement(By.tagName("button"));
			editButton.click();
			Thread.sleep(2000);
			noteTable.findElement(By.id("note-title")).clear();
			noteTable.findElement(By.id("note-title")).sendKeys("Note111");
			noteTable.findElement(By.id("note-description")).clear();
			noteTable.findElement(By.id("note-description")).sendKeys("first111");
			noteTable.findElement(By.id("submit-note")).click();
			Thread.sleep(2000);
			Assertions.assertEquals("Note111", driver.findElement(By.id("show-note-title")).getText());
			Assertions.assertEquals("first111", driver.findElement(By.id("show-note-description")).getText());
		}
	}

	@Test
	public void createCredential() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/signup");
		driver.findElement(By.id("inputFirstName")).sendKeys("hu");
		driver.findElement(By.id("inputLastName")).sendKeys("ge");
		driver.findElement(By.id("inputUsername")).sendKeys("huge");
		driver.findElement(By.id("inputPassword")).sendKeys("123123");
		driver.findElement(By.id("submit-signup")).click();
		driver.get("http://localhost:" + this.port + "/login");
		driver.findElement(By.id("inputUsername")).sendKeys("huge");
		driver.findElement(By.id("inputPassword")).sendKeys("123123");
		driver.findElement(By.id("submit-login")).click();

		driver.findElement(By.id("nav-credentials-tab")).click();
		Thread.sleep(2000);

		// Create credential
		try {
			driver.findElement(By.id("add-new-credential")).click();
			Thread.sleep(2000);
			driver.findElement(By.id("credential-url")).sendKeys("www.baidu.com");
			driver.findElement(By.id("credential-username")).sendKeys("huge");
			driver.findElement(By.id("credential-password")).sendKeys("123123");
			driver.findElement(By.id("submit-credential")).click();
			Thread.sleep(5000);
			Assertions.assertEquals("Note1", driver.findElement(By.id("show-credential-url")).getText());
			Assertions.assertEquals("Note1", driver.findElement(By.id("show-credential-username")).getText());
			Assertions.assertEquals("first", driver.findElement(By.id("show-credential-password")).getText());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	@Test
	public void editCredential() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/signup");
		driver.findElement(By.id("inputFirstName")).sendKeys("hu");
		driver.findElement(By.id("inputLastName")).sendKeys("ge");
		driver.findElement(By.id("inputUsername")).sendKeys("huge");
		driver.findElement(By.id("inputPassword")).sendKeys("123123");
		driver.findElement(By.id("submit-signup")).click();
		driver.get("http://localhost:" + this.port + "/login");
		driver.findElement(By.id("inputUsername")).sendKeys("huge");
		driver.findElement(By.id("inputPassword")).sendKeys("123123");
		driver.findElement(By.id("submit-login")).click();
		driver.findElement(By.id("nav-credentials-tab")).click();
		Thread.sleep(1000);
		try {
			driver.findElement(By.id("add-new-credential")).click();
			Thread.sleep(2000);
			driver.findElement(By.id("credential-url")).sendKeys("www.baidu.com");
			driver.findElement(By.id("credential-username")).sendKeys("huge");
			driver.findElement(By.id("credential-password")).sendKeys("123123");
			driver.findElement(By.id("submit-credential")).click();
		} catch(Exception e) {
			System.out.println(e.toString());
		}
		//edit credential
		Thread.sleep(2000);
		WebElement credentialTable = driver.findElement(By.id("credentialTable"));
		List<WebElement> credentialList = credentialTable.findElements(By.tagName("td"));
		for (WebElement row : credentialList) {
			WebElement editButton;
			editButton = row.findElement(By.tagName("button"));
			editButton.click();
			Thread.sleep(2000);
			driver.findElement(By.id("credential-url")).clear();
			driver.findElement(By.id("credential-url")).sendKeys("www.google.com");
			driver.findElement(By.id("credential-username")).clear();
			driver.findElement(By.id("credential-username")).sendKeys("huge1111");
			driver.findElement(By.id("credential-password")).clear();
			driver.findElement(By.id("credential-password")).sendKeys("1231231111");
			credentialTable.findElement(By.id("submit-credential")).click();
			Thread.sleep(2000);
			Assertions.assertEquals("huge1111", driver.findElement(By.id("credential-username")).getText());
			Assertions.assertEquals("1231231111", driver.findElement(By.id("credential-password")).getText());
		}
	}

	@Test
	public void deleteCredential() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/signup");
		driver.findElement(By.id("inputFirstName")).sendKeys("hu");
		driver.findElement(By.id("inputLastName")).sendKeys("ge");
		driver.findElement(By.id("inputUsername")).sendKeys("huge");
		driver.findElement(By.id("inputPassword")).sendKeys("123123");
		driver.findElement(By.id("submit-signup")).click();
		driver.get("http://localhost:" + this.port + "/login");
		driver.findElement(By.id("inputUsername")).sendKeys("huge");
		driver.findElement(By.id("inputPassword")).sendKeys("123123");
		driver.findElement(By.id("submit-login")).click();

		driver.findElement(By.id("nav-credentials-tab")).click();
		Thread.sleep(2000);
		try {
			driver.findElement(By.id("add-new-credential")).click();
			Thread.sleep(2000);
			driver.findElement(By.id("credential-url")).sendKeys("www.baidu.com");
			driver.findElement(By.id("credential-username")).sendKeys("huge");
			driver.findElement(By.id("credential-password")).sendKeys("123123");
			driver.findElement(By.id("submit-credential")).click();
		} catch(Exception e) {
			System.out.println(e.toString());
		}
        //delete credential
		Thread.sleep(2000);
		WebElement credentialTable = driver.findElement(By.id("credentialTable"));
		List<WebElement> noteLink = credentialTable.findElements(By.tagName("a"));
		for (WebElement deleteNoteButton : noteLink) {
			deleteNoteButton.click();
		}
		Assertions.assertEquals(0,credentialTable.findElements(By.tagName("td")).size());
	}

}
