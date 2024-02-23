package learnova.assignment.ui.pages.coupon_management;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CommenPageElements
{
  private final static CommenPageElements instance = new CommenPageElements();

  private CommenPageElements(){}

  public static CommenPageElements getInstance() {
    return instance;
  }
    public WebElement getSettingButton(WebDriver driver){
      return driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[3]/div/nav/div/div[2]/div[2]"));
    }
}
