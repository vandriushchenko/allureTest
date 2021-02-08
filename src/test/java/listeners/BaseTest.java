package listeners;

import io.github.bonigarcia.wdm.WebDriverManager;
import listeners.CustomTestListener;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

@Listeners({ CustomTestListener.class})
public class BaseTest {
	protected WebDriver driver;
	@Parameters("team")
	@BeforeClass
	public void before(@Optional("LIV") String team){
	}

	private WebDriver initChrome() {
		WebDriverManager.chromedriver().setup();
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setPageLoadStrategy(PageLoadStrategy.NONE);
		return new ChromeDriver(chromeOptions);
	}
}
