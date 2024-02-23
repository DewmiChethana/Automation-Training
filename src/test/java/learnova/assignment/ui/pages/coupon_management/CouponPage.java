package learnova.assignment.ui.pages.coupon_management;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CouponPage
{
  private final static CouponPage instance = new CouponPage();

  private CouponPage(){

  }

  public static CouponPage getInstance() {
    return instance;
  }

  public WebElement getAddCoupponButton(WebDriver driver){
    return driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[3]/div/section/div[2]/section/div/div/div[2]/section/div/div/div/div[1]/div[2]/button/div/div"));
  }


  public WebElement getDataTable(WebDriver driver){
    return driver.findElement(By.xpath("/html/body/div/div/div[3]/div/section/div[2]/section/div/div/div[2]/section/div/div/div/div[2]/div/table"));
  }

  public WebElement getDataTableRow(WebDriver driver, String raw){
    return driver.findElement(By.xpath("/html/body/div/div/div[3]/div/section/div[2]/section/div/div/div[2]/section/div/div/div/div[2]/div/table/tbody/tr["+raw+"]"));
  }

  public WebElement getDataTableCell(WebDriver driver, String raw, String cell){
    return driver.findElement(By.xpath("/html/body/div/div/div[3]/div/section/div[2]/section/div/div/div[2]/section/div/div/div/div[2]/div/table/tbody/tr["+raw+"]/td["+cell+"]"));
  }
}
