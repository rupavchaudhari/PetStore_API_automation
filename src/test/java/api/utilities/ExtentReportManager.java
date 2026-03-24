package api.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ExtentReportManager integrates ExtentReports with TestNG listeners
 * for PetStore API automation reporting.
 */
public class ExtentReportManager implements ITestListener {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();

    /**
     * Creates and configures the ExtentReports instance.
     */
    private static ExtentReports createInstance(String reportName) {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportName);
        sparkReporter.config().setDocumentTitle("PetStore API Test Report");
        sparkReporter.config().setReportName("PetStore API Automation");
        sparkReporter.config().setTheme(Theme.STANDARD);

        ExtentReports extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);
        extentReports.setSystemInfo("Project", "PetStore API");
        extentReports.setSystemInfo("Tester", "Automation QA");
        extentReports.setSystemInfo("Environment", "QA");

        return extentReports;
    }

    @Override
    public void onStart(ITestContext context) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String reportFile = System.getProperty("user.dir") + "/reports/PetStoreReport_" + timeStamp + ".html";
        extent = createInstance(reportFile);
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getMethod().getMethodName())
                .assignCategory(result.getMethod().getRealClass().getSimpleName());
        testThread.set(test);
        test.log(Status.INFO, "Starting test: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        testThread.get().log(Status.PASS, "Test passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        testThread.get().log(Status.FAIL, "Test failed: " + result.getMethod().getMethodName());
        testThread.get().log(Status.FAIL, "Reason: " + result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        testThread.get().log(Status.SKIP, "Test skipped: " + result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush();
        }
    }
}
