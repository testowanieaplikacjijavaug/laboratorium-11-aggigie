import io.github.bonigarcia.seljup.SeleniumExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.MessageFormat;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SeleniumExtension.class)
public class HeadlessTest {

	WebDriver driver;
	WebDriverWait wait;

	@BeforeEach
	public void setUp()
	{
		driver = new HtmlUnitDriver();
		wait = new WebDriverWait(driver, 10);
		driver.get("https://duckduckgo.com");
	}


	@BeforeEach
	public void setDefaultPage() {
		driver.get("https://duckduckgo.com/");
	}


	@Test
	public void testTitlePage() {
		assertEquals("DuckDuckGo — Privacy, simplified.", driver.getTitle());
	}


	@Test
	public void testFindElement() {
		WebElement element = driver.findElement(By.className("content-wrap--home"));
		assertNotNull(element);
	}

	@Test
	public void testClicking() {
		String key = "testujmy";
		driver.findElement(By.id("search_form_input_homepage")).sendKeys(key);
		driver.findElement(By.id("search_button_homepage")).click();
		assertEquals(MessageFormat.format("{0} at DuckDuckGo", key), driver.getTitle());
	}


	@Test
	public void testNonExisting() {
		assertThrows(NoSuchElementException.class, () -> {
			driver.findElement(By.linkText("¯\\_(ツ)_/¯ "));
		});
	}

	@AfterEach
	public void tearDown() {
		driver.quit();
	}

}
