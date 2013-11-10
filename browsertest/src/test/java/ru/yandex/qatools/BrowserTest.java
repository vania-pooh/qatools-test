package ru.yandex.qatools;


import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class BrowserTest {

    @Parameters
    public static List<DesiredCapabilities[]> loadBrowsers() {
        return BrowsersLoader.getInstance().load();
    }

    private static final String SELENIUM_SERVER_URL = "http://localhost:4444/wd/hub";

    private static final String TEST_URL = "http://internet.yandex.ru/";

    @Rule
    public final TakeScreenshotOnFailureRule takeScreenshotOnFailureRule = new TakeScreenshotOnFailureRule();

    private static RemoteWebDriver driver;

    private final DesiredCapabilities browser;

    public BrowserTest(final DesiredCapabilities browser){
        this.browser = browser;
    }

    @Before
    public void startBrowserIfNeeded() throws MalformedURLException {
        final DesiredCapabilities browser = getBrowser();
        if (driver == null){
            driver = new RemoteWebDriver(new URL(SELENIUM_SERVER_URL), browser);
        }
    }

    @AfterClass
    public static void quitBrowser(){
        if (driver != null){
            driver.quit();
        }
    }

    @Test
    public void testIsBrowserWorking() throws IOException {
        getDriver().get(TEST_URL);
        assertTrue(getDriver().getPageSource().length() > 0);
    }

    @Test
    public void testTakeScreenshotOnFailure(){
        getDriver().get(TEST_URL);
        assertEquals("Chuck Norris shaves with a belt sander.", getDriver().getTitle());
    }

    public static RemoteWebDriver getDriver() {
        return driver;
    }

    private DesiredCapabilities getBrowser() {
        return browser;
    }

}
