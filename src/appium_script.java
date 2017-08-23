import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSTouchAction;
import io.appium.java_client.remote.HideKeyboardStrategy;
import io.appium.java_client.remote.MobileCapabilityType;

public class appium_script {
	
	static String apiToken = "tok_vey88z56n4td8168e7c25315c0"; // your API token from https://appetize.io/docs#request-api-token
    static String deviceType = "iphone5s"; // iphone5s, iphone6s, iphone6splus, iphone7, iphone7plus, ipadair2
    static String publicKey = "km7k25te7f4tmkxbc8ca0zr7kg"; // replace with your own publicKey after uploading through website or API
    static String osVersion = "10.3"; // also supports 10.2, 10.1, 10.0
    static String proxy = "intercept"; // false for no proxy, or specify your own with http://proxy-example.com:port
    static String outputDirectory = "./outputs/";
	
	public static AppiumDriver driver;
	public static WebDriverWait wait;
	
	public static void main(String args[]) throws MalformedURLException, Exception {
		new File(outputDirectory).mkdir();
		Map<String, String> hello = new HashMap<String, String>();
		hello.put("input", "output");
		hello.put("input1", "output1");
		System.out.println(hello.toString());
		
		
		DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("device", deviceType);
        capabilities.setCapability("publicKey", publicKey);
        capabilities.setCapability("osVersion", osVersion);
        capabilities.setCapability("proxy", proxy);
        capabilities.setCapability("debug", true);
        capabilities.setCapability("unicodeKeyboard", true);
        capabilities.setCapability("resetKeyboard", true);
		
        URL remote = new URL("https://" + apiToken + "@appium.appetize.io/wd/hub");
		driver = new IOSDriver(remote, capabilities);
		wait = new WebDriverWait(driver, 30);
		
		TouchAction tapCoordinates = new TouchAction(driver);
		tapCoordinates.tap(150,40).perform();
		driver.getKeyboard().sendKeys("activate");
		driver.getKeyboard().sendKeys(Keys.RETURN);
		System.out.println("activate");
		
		TouchAction tapCoordinates2 = new TouchAction(driver);
		tapCoordinates2.tap(150,170).perform();
		driver.getKeyboard().sendKeys("nithin");
		driver.getKeyboard().sendKeys(Keys.RETURN);
		System.out.println("nithin");
		
		TouchAction tapCoordinates3 = new TouchAction(driver);
		tapCoordinates3.tap(150,240).perform();
		driver.getKeyboard().sendKeys("test_activate__audience_off_with_attributes");
		driver.getKeyboard().sendKeys(Keys.RETURN);
		System.out.println("experiment");
		
		TouchAction tapCoordinates4 = new TouchAction(driver);
		tapCoordinates4.tap(150,430).perform();
		driver.getKeyboard().sendKeys("8581301295");
		driver.getKeyboard().sendKeys(Keys.RETURN);
		System.out.println("project");
		

		driver.findElementByXPath("//XCUIElementTypeButton[@name=\"enter\"]").click();
		//wait.until(ExpectedConditions.visibilityOf("variation"));
		
		
		String textVariation = driver.findElementByAccessibilityId("variation").getText();
		String textUPS = driver.findElementByAccessibilityId("labelUPSName").getText();
		System.out.println(textVariation);
		System.out.println(textUPS);
		JSONObject upsJSON = new JSONObject(textUPS);
		System.out.println(upsJSON);
		String sessionId = driver.getSessionId().toString();
		driver.quit();
		
		String timeStamp = "" + System.currentTimeMillis();
		downloadFile("https://api.appetize.io/v1/screenRecording/appiumId/" + sessionId,
                "appetize-" + deviceType + "-" + osVersion + "-" + publicKey +
                        "-screenRecording-" + timeStamp + ".mp4");
	
	}
	
	static void downloadFile(String url, String fileName) throws Exception {
        System.out.println("downloading " + url);
        URL remoteUrl = new URL(url);
        ReadableByteChannel rbc = Channels.newChannel(remoteUrl.openStream());
        FileOutputStream fos = new FileOutputStream(new File(outputDirectory, fileName));
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
    }
}
