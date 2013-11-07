package ru.yandex.qatools;


import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import static org.junit.Assert.assertTrue;

/**
 * Browsers test
 */
public final class BrowsersTest {

    @Test
    public void testIsFirefoxOnLinuxWorking() throws Exception {
        final DesiredCapabilities browser = DesiredCapabilities.firefox();
        browser.setPlatform(Platform.LINUX);
        browser.setVersion("last");
        browser.setBrowserName("firefox");
        isBrowserWorking(browser);
    }

    @Test
    public void testIsChromeOnLinuxWorking() throws Exception {
        final DesiredCapabilities browser = DesiredCapabilities.chrome();
        browser.setPlatform(Platform.LINUX);
        browser.setVersion("last");
        browser.setBrowserName("chrome");
        isBrowserWorking(browser);
    }

    private static void isBrowserWorking(final DesiredCapabilities browser) throws MalformedURLException, IOException {
        RemoteWebDriver driver = null;
        try {
            final String hubUrl = "http://localhost:4444/wd/hub";
            driver = new RemoteWebDriver(new URL(hubUrl), browser);
            final String testUrl = "http://internet.yandex.ru/";
            driver.get(testUrl);
            takeScreenshot(driver);
            assertTrue(driver.getPageSource().length() > 0); //Driver should fetch something from the given URL
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }

    private static void takeScreenshot(final RemoteWebDriver driver) throws IOException {
        final String browserName = driver.getCapabilities().getBrowserName();
        final long currentTimestamp = new Date().getTime();
        final String outputFileName = browserName + "_" + currentTimestamp + ".png";
        final WebDriver augmentedDriver = new Augmenter().augment(driver);
        final File scrFile = ((TakesScreenshot)augmentedDriver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("/vagrant/screenshots", outputFileName));
    }

}
