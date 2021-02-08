package listeners;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.filter.log.LogDetail;
import com.jayway.restassured.filter.log.RequestLoggingFilter;
import com.jayway.restassured.filter.log.ResponseLoggingFilter;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class CustomTestListener extends TestListenerAdapter {
	private ByteArrayOutputStream request = new ByteArrayOutputStream();
	private ByteArrayOutputStream response = new ByteArrayOutputStream();

	private PrintStream requestVar = new PrintStream(request, true);
	private PrintStream responseVar = new PrintStream(response, true);


	public void onStart(ITestContext iTestContext) {
		RestAssured.filters(new ResponseLoggingFilter(LogDetail.ALL, responseVar),
				new RequestLoggingFilter(LogDetail.ALL, requestVar));
	}

	public void onTestSuccess(ITestResult iTestResult) {
		logRequest(request);
		logResponse(response);
	}

	public void onTestFailure(ITestResult iTestResult) {
		makeScreenshot(iTestResult);
		if(iTestResult.getThrowable()!=null)
			iTestResult.getThrowable().printStackTrace();
	}

	@Attachment(value = "Screenshot", type = "image.png")
	private byte[] makeScreenshot(ITestResult result){
		Object currentClass = result.getInstance();
		WebDriver driver = ((BaseTest) currentClass).driver;
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	}
	@Attachment(value = "request")
	public byte[] logRequest(ByteArrayOutputStream stream) {
		return attach(stream);
	}

	@Attachment(value = "response")
	public byte[] logResponse(ByteArrayOutputStream stream) {
		return attach(stream);
	}

	public byte[] attach(ByteArrayOutputStream log) {
		byte[] array = log.toByteArray();
		log.reset();
		return array;
	}

	public void onTestStart(ITestResult iTestResult) {
	}

	public void onTestSkipped(ITestResult iTestResult) {

	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

	}

	public void onFinish(ITestContext iTestContext) {

	}
}
