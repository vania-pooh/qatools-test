package ru.yandex.qatools;

import org.apache.commons.io.FileUtils;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Allows to take screenshots on test failures or exceptions
 */
public class TakeScreenshotOnFailureRule implements MethodRule {

    public Statement apply(final Statement statement, final FrameworkMethod frameworkMethod, final Object o) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    statement.evaluate();
                } catch (Throwable t) {
                    final String testName = frameworkMethod.getName();
                    final RemoteWebDriver driver = BrowserTest.getDriver();
                    takeScreenshot(testName, driver);
                    throw t;
                }
            }

        };
    }

    private static void takeScreenshot(final String testName, final RemoteWebDriver driver) throws IOException {
        final long currentTimestamp = new Date().getTime();
        final String outputFileName = testName + "_" + currentTimestamp + ".png";
        final WebDriver augmentedDriver = new Augmenter().augment(driver);
        if (augmentedDriver != null){
            final File scrFile = ((TakesScreenshot)augmentedDriver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File("/vagrant/screenshots", outputFileName));
        }
    }

}
