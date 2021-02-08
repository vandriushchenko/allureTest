package listeners;

import org.apache.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {
	private Logger log = Logger.getLogger(getClass());
	private int retryCount = 0;
	public boolean retry(ITestResult result) {
		int maxRetryCount = 3;
		if (retryCount<maxRetryCount){
			retryCount++;
			log.info("Retry #" + retryCount + " for test: " + result.getMethod().getMethodName() + ", on thread" + Thread.currentThread().getName());
			return true;
		}
		return false;
	}
}
