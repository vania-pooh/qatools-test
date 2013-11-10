package ru.yandex.qatools;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.yandex.qatools.util.Tuple3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static ru.yandex.qatools.util.Tuple.tuple;

/**
 * Loads a list of desired capabilities to be used in tests using simple test notation in files
 */
public final class BrowsersLoader {

    private static final String FILE_NAME = "tested.browsers";
    private static final String DELIMITER = ":";

    private static BrowsersLoader instance;

    public static BrowsersLoader getInstance(){
        if (instance == null){
            instance = new BrowsersLoader();
        }
        return instance;
    }

    public List<DesiredCapabilities[]> load(){
        final List<DesiredCapabilities[]> browsers = new ArrayList<DesiredCapabilities[]>();
        final List<Tuple3<String, String, String>> configurationList = loadConfigurationLines(FILE_NAME);
        for (Tuple3<String, String, String> browserConfiguration: configurationList){
            browsers.add(new DesiredCapabilities[]{initBrowser(
                    browserConfiguration.getFirst(),
                    browserConfiguration.getSecond(),
                    browserConfiguration.getThird()
            )});
        }
        return browsers;
    }

    private static DesiredCapabilities initBrowser(final String browserName, final String version, final String platformName){
        final DesiredCapabilities browser = new DesiredCapabilities();
        final Platform platform = Platform.extractFromSysProperty(platformName);
        browser.setBrowserName(browserName);
        browser.setVersion(version);
        browser.setPlatform(platform);
        return browser;
    }

    private List<Tuple3<String, String, String>> loadConfigurationLines(String fileName){

        final List<Tuple3<String, String, String>> configurationLines = new ArrayList<Tuple3<String, String, String>>();

        final URL fileUrl = getFileUrl(fileName);
        if (fileUrl == null){
            throw new IllegalArgumentException("Input file does not exist.");
        }
        final File inputFile = new File(fileUrl.getFile());
        try {
            final FileReader fileReader = new FileReader(inputFile);
            final BufferedReader bReader = new BufferedReader(fileReader);

            while (true){
                final String currentLine = bReader.readLine();
                if (currentLine == null || currentLine.length() == 0){
                    break;
                }
                final String[] pieces = currentLine.split(DELIMITER);
                if (
                        (pieces.length == 3)  //Not sure whether this seems like a magic number :P
                        && !StringUtils.isBlank(pieces[0])
                        && !StringUtils.isBlank(pieces[1])
                        && !StringUtils.isBlank(pieces[2])
                ){
                    configurationLines.add(tuple(pieces[0], pieces[1], pieces[2]));
                } else {
                    throw new IllegalStateException(
                        "Invalid configuration line: " + currentLine + ".  Should be in format: \"browserName:version:platformName\""
                    );
                }
            }
        } catch (Exception e){
            throw new RuntimeException("Caught an exception while reading configuration file contents. Exception message was: " + e.getMessage());
        }

        return configurationLines;
    }

    private URL getFileUrl(final String fileName){
        final ClassLoader classLoader = getClass().getClassLoader();
        if (classLoader != null){
            return classLoader.getResource(fileName);
        }
        return null;
    }
}
