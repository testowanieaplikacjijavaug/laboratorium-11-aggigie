import io.github.bonigarcia.seljup.Arguments;
import io.github.bonigarcia.seljup.SeleniumExtension;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class DuckWebDriverManagerTest {

	private static WebDriver webDriver;

	@BeforeAll
	public static void setUp() {
		WebDriverManager.chromedriver().setup();
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setHeadless(true);
		webDriver = new ChromeDriver(chromeOptions);
		webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@BeforeEach
	public void setDefaultPage() {
		webDriver.get("https://duckduckgo.com/");
	}

	@Test
	public void testTitlePage() {
		assertEquals("DuckDuckGo — Prywatność — jeszcze prostsza.", webDriver.getTitle());
	}


	@Test
	public void testFindElement() {
		WebElement element = webDriver.findElement(By.className("content-wrap--home"));
		assertNotNull(element);
	}

	@Test
	public void testClicking() {
		String key = "testujmy";
		webDriver.findElement(By.id("search_form_input_homepage")).sendKeys(key);
		webDriver.findElement(By.id("search_button_homepage")).click();
		assertEquals(MessageFormat.format("{0} at DuckDuckGo", key), webDriver.getTitle());
	}


	@Test
	public void testNonExisting() {
		assertThrows(NoSuchElementException.class, () -> {
			webDriver.findElement(By.linkText("¯\\_(ツ)_/¯ "));
		});
	}

	@AfterEach
	public void tearDown() {
		webDriver.quit();
	}
}
