package learnova.assignment.ui.pages.coupon_management;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AddCouponPage
{
  private static final AddCouponPage instance = new AddCouponPage();

  private AddCouponPage(){}

  public static AddCouponPage getInstance()
  {
    return instance;
  }

  public WebElement getAddCouponButton(WebDriver driver){
    return driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[3]/div/section/div[2]/section/div/div/div[2]/section/div/div/div/div[1]/div[2]/button/div/div"));
  }

  public WebElement getActiveDateCalendarIcon(WebDriver driver){
    return driver.findElement(By.xpath("(//*[name()='div' and @class='month-selection']//*[local-name()='svg' and @xmlns='http://www.w3.org/2000/svg'])[3]"));
  }

  public WebElement getActiveDateCalendarYearAndMounth(WebDriver driver){
    return driver.findElement(By.xpath("/html/body/div/div/div[3]/div/section/div[2]/section/div/div/div[2]/section/div/div/div/form/div[4]/div[1]/div[3]/div/div/div/div[2]/div[1]/div[1]"));
  }

  public WebElement getActiveDateCalendarIncreaseButton(WebDriver driver){
    return driver.findElement(By.xpath("/html/body/div/div/div[3]/div/section/div[2]/section/div/div/div[2]/section/div/div/div/form/div[4]/div[1]/div[3]/div/div/div/button[2]"));
  }

  public WebElement getActiveDateCalendarDecreaseButton(WebDriver driver){
    return driver.findElement(By.xpath("/html/body/div/div/div[3]/div/section/div[2]/section/div/div/div[2]/section/div/div/div/form/div[4]/div[1]/div[3]/div/div/div/button[1]"));
  }

  public WebElement getCalendarDate(WebDriver driver,String xpath){
    return driver.findElement(By.xpath(xpath));
  }
  public WebElement getExpireDateCalendarIcon(WebDriver driver){
    return driver.findElement(By.xpath("(//*[name()='div' and @class='month-selection']//*[local-name()='svg' and @xmlns='http://www.w3.org/2000/svg'])[4]"));
  }
  public WebElement getExpireDateCalendarYearAndMounth(WebDriver driver){
    return driver.findElement(By.xpath("/html/body/div/div/div[3]/div/section/div[2]/section/div/div/div[2]/section/div/div/div/form/div[4]/div[2]/div[3]/div/div/div/div[2]/div[1]/div[1]"));
  }
  public WebElement getExpireDateCalendarIncreaseButton(WebDriver driver){
    return driver.findElement(By.xpath("/html/body/div/div/div[3]/div/section/div[2]/section/div/div/div[2]/section/div/div/div/form/div[4]/div[2]/div[3]/div/div/div/button[2]"));
  }

  public WebElement getExpireDateCalendarDecreaseButton(WebDriver driver){
    return driver.findElement(By.xpath("/html/body/div/div/div[3]/div/section/div[2]/section/div/div/div[2]/section/div/div/div/form/div[4]/div[2]/div[3]/div/div/div/button[1]"));
  }

  public WebElement getCouponType(WebDriver driver){
    return driver.findElement(By.xpath("/html/body/div/div/div[3]/div/section/div[2]/section/div/div/div[2]/section/div/div/div/form/div[1]/div/div/div/div/div/div[1]/div[2]/input"));
  }

  public WebElement getCouponCode(WebDriver driver){
    return driver.findElement(By.xpath("/html/body/div/div/div[3]/div/section/div[2]/section/div/div/div[2]/section/div/div/div/form/div[2]/div/div/div/div/div/input"));
  }

  public WebElement getDiscountType(WebDriver driver){
    return driver.findElement(By.xpath("/html/body/div/div/div[3]/div/section/div[2]/section/div/div/div[2]/section/div/div/div/form/div[5]/div[1]/div/div/div/div/div[1]/div[2]/input"));
  }

  public WebElement getDiscountAmount(WebDriver driver){
    return driver.findElement(By.xpath("/html/body/div/div/div[3]/div/section/div[2]/section/div/div/div[2]/section/div/div/div/form/div[5]/div[2]/div/div/div/div/input"));
  }

  public WebElement getNumberofCouponCodes(WebDriver driver){
    return driver.findElement(By.xpath("/html/body/div/div/div[3]/div/section/div[2]/section/div/div/div[2]/section/div/div/div/form/div[6]/div/div/div/div/div/input"));
  }

  public  WebElement getCourse(WebDriver driver){
    return driver.findElement(By.xpath("/html/body/div/div/div[3]/div/section/div[2]/section/div/div/div[2]/section/div/div/div/form/div[3]/div/div/div/div/div/div[1]/div[2]/input"));
  }

  public WebElement getSaveButton(WebDriver driver){
    return driver.findElement(By.xpath("/html/body/div/div/div[3]/div/section/div[2]/section/div/div/div[2]/section/div/div/div/form/div[7]/div[1]/button"));
  }
}
