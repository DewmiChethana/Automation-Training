package learnova.assignment.ui.test.coupon_management;

import static io.restassured.RestAssured.baseURI;

import learnova.assignment.ui.pages.coupon_management.AddCouponPage;
import learnova.assignment.ui.pages.coupon_management.CommenPageElements;
import learnova.assignment.ui.pages.coupon_management.CouponPage;
import learnova.assignment.util.Constant;
import learnova.assignment.util.CouponObjectMapper;
import learnova.assignment.util.CouponUtil;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ViewCouponsTest
{
  WebDriver driver;

  CouponObjectMapper objectMapper = CouponObjectMapper.getInstance();

  @BeforeClass
  public void setUp()
  {
    baseURI = Constant.getBaseUrlUi();
    driver = objectMapper.getDriver();
    CouponUtil.loginForUiTest(driver, baseURI);
  }

  @Test
  public void CreateCouponWithCouponObject()
  {
    CommenPageElements.getInstance().getSettingButton(driver).click();
    String rowNumber = CouponUtil.getRandomNumber(1, 10);
    CouponPage.getInstance().getDataTableRow(driver, rowNumber).click();

  }
}
