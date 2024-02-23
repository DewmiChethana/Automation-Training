package learnova.assignment.ui.test.coupon_management;
import io.github.bonigarcia.wdm.WebDriverManager;
import learnova.assignment.ui.pages.coupon_management.AddCouponPage;
import learnova.assignment.ui.pages.coupon_management.CommenPageElements;
import learnova.assignment.ui.pages.coupon_management.CouponPage;
import learnova.assignment.util.Constant;
import learnova.assignment.util.CouponObjectMapper;
import learnova.assignment.util.CouponUtil;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

import static io.restassured.RestAssured.baseURI;


public class CreateCouponsTest {
    WebDriver driver;

    CouponObjectMapper objectMapper = CouponObjectMapper.getInstance();

    @BeforeClass
    public void setUp(){
        baseURI = Constant.getBaseUrlUi();
        driver = objectMapper.getDriver();
        CouponUtil.loginForUiTest(driver,baseURI);
    }

    @Test
    public void CreateCouponWithCouponObject()  {
        try {
            CommenPageElements.getInstance().getSettingButton(driver).click();
            AddCouponPage.getInstance().getAddCouponButton(driver).click();
            JSONObject coupen = CouponObjectMapper.getInstance().getNewCouponForUi();
            CouponUtil.saveCoupen(driver,coupen);
            Thread.sleep(2000);
            AddCouponPage.getInstance().getSaveButton(driver).click();
            String text = driver.findElement(By.xpath("/html/body/div/div/div[1]")).getText();
            String x="";
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
