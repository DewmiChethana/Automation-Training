package learnova.assignment.util;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import learnova.assignment.ui.pages.coupon_management.AddCouponPage;
import learnova.assignment.ui.pages.coupon_management.CouponPage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class CouponUtil {
    private static Random random = new Random();
    public static String getNewCouponCode(int targetStringLength) {
        return getRandomString(targetStringLength)+getRandomNumber(99,1);
    }

    public static String getDate(int dateDiff){
        String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        DateFormat df = new SimpleDateFormat(pattern);
        Calendar c =  Calendar.getInstance();
        c.add(Calendar.DATE,dateDiff);
        Date date = c.getTime();
        String reportDate = df.format(date);
        return reportDate;
    }

    public static String getRandomString(int targetStringLength){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }

    public static String getRandomNumber(int max , int low){
        Integer number = random.nextInt(max-low)+low;
        return number.toString();
    }

    public static String getCourseName() {
        baseURI= Constant.getBaseUrlApi();
        Response response = null;
        try {
            response = given().
                    when().
                    header(CouponObjectMapper.getInstance().getRequestHeader()).
                    contentType(ContentType.JSON).accept(ContentType.JSON).
                    get("/api/public/courses");
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(response.asString());
            JSONObject jsonObject = (JSONObject) jsonArray.get(0);
            for (int x = 1; x < jsonArray.size(); ) {
                jsonObject = (JSONObject) jsonArray.get(x);
                if((boolean)jsonObject.get("paid")&&(boolean)jsonObject.get("couponAllowed")){
                    return jsonObject.get("name").toString();
                }
            }
            return "";
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Long getCourseId() {
        baseURI= Constant.getBaseUrlApi();
        Response response = null;
        try {
            response = given().
              when().
              header(CouponObjectMapper.getInstance().getRequestHeader()).
              contentType(ContentType.JSON).accept(ContentType.JSON).
              get("/api/public/courses");
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(response.asString());
            JSONObject jsonObject = (JSONObject) jsonArray.get(0);
            for (int x = 1; x < jsonArray.size(); ) {
                jsonObject = (JSONObject) jsonArray.get(x);
                if((boolean)jsonObject.get("paid")&&(boolean)jsonObject.get("couponAllowed")){
                    return (long)jsonObject.get("id");
                }
            }
            return null;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getUsedCoupenCode() {
        baseURI=Constant.getBaseUrlApi();
        Response response = null;
        try {
            response = given().
                    when().
                    header(CouponObjectMapper.getInstance().getRequestHeader()).
                    contentType(ContentType.JSON).accept(ContentType.JSON).
                    get("/api/admin/coupons");
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(response.asString());
            int x = jsonArray.size();
            JSONObject json = (JSONObject) jsonArray.get(0);
            return (String) json.get("couponCode");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loginForUiTest(WebDriver driver,String baseUrl){
        driver.get(baseUrl);
        WebElement loginButton = driver.findElement(By.xpath("/html/body/div/div/div[3]/div/div/div[1]/div/nav/div/div[2]/div[4]/button"));
        loginButton.click();
        WebElement emailInput = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[3]/div/div/section/div/div/div[2]/form/div[2]/div/div/div/div/div/input"));
        emailInput.sendKeys("skfadmin@gmail.com");
        WebElement passwordInput = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[3]/div/div/section/div/div/div[2]/form/div[3]/div/div/div/div/div/input"));
        passwordInput.sendKeys("SKF@Admin_2023");
        WebElement loginButton2 = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[3]/div/div/section/div/div/div[2]/form/div[5]/div/button"));
        loginButton2.click();
    }

    public static void saveCoupen(WebDriver driver,JSONObject coupon){
        AddCouponPage.getInstance().getCouponType(driver).sendKeys("Common",Keys.ENTER);
        AddCouponPage.getInstance().getCouponCode(driver).sendKeys(coupon.get("couponCode").toString());
        AddCouponPage.getInstance().getDiscountType(driver).sendKeys(coupon.get("discountType").toString(),Keys.ENTER);
        AddCouponPage.getInstance().getDiscountAmount(driver).sendKeys(coupon.get("discountAmount").toString());
        AddCouponPage.getInstance().getNumberofCouponCodes(driver).sendKeys(coupon.get("usageLimit").toString());
        AddCouponPage.getInstance().getCourse(driver).sendKeys(coupon.get("boundedCourseId").toString(),Keys.ENTER);
        AddCouponPage.getInstance().getActiveDateCalendarIcon(driver).click();
        setDateAndTime(AddCouponPage.getInstance().getActiveDateCalendarYearAndMounth(driver),
                       AddCouponPage.getInstance().getActiveDateCalendarIncreaseButton(driver),
                       AddCouponPage.getInstance().getActiveDateCalendarDecreaseButton(driver),driver,coupon.get("activeDate").toString());
        AddCouponPage.getInstance().getExpireDateCalendarIcon(driver).click();
        setDateAndTime(AddCouponPage.getInstance().getExpireDateCalendarYearAndMounth(driver),
                       AddCouponPage.getInstance().getExpireDateCalendarIncreaseButton(driver),
                       AddCouponPage.getInstance().getExpireDateCalendarDecreaseButton(driver),driver,coupon.get("expireDate").toString());
    }

    private static void setDateAndTime(WebElement yearAndMonth, WebElement monthsIncreaseButton, WebElement monthsDecreaseButton,WebDriver driver, String dateString){
        Date date;
        String year;
        String months = null;
        try
        {
            date =new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(dateString);
            year = String.valueOf(date.getYear()+1900);
            for (Map.Entry<String, Integer> entry : Constant.getMounthsMap().entrySet()) {
                if (entry.getValue()==(date.getMonth()+1)) {
                    months=entry.getKey();
                    break;
                }
            }
            dateString = String.valueOf(date.getDate());
        }
        catch (java.text.ParseException e)
        {
            throw new RuntimeException(e);
        }
        String requiredYearAndMonths = months+" "+year;
        while (true){
            String htmlYearAndMonths = yearAndMonth.getText();
            if (htmlYearAndMonths.equals(requiredYearAndMonths)){
                break;
            }
            else if(Integer.parseInt(htmlYearAndMonths.split(" ")[1]) > Integer.parseInt(year) || Constant.getMounthsMap().get(htmlYearAndMonths.split(" ")[0]) > Constant.getMounthsMap()
              .get(months)){
                monthsDecreaseButton.click();
            }else {
                monthsIncreaseButton.click();
            }
        }
        String dateName =null;
        for (Map.Entry<String, Integer> entry : Constant.getDaysMap().entrySet()) {
            if (entry.getValue()==(date.getDay())) {
                dateName=entry.getKey();
                break;
            }
        }
        String dateSuffixx= Constant.getDateSuffixMap().get(date.getDate());
        String dateKey="//*[name()='div' and @aria-label='Choose "+dateName+", "+months+" "+dateString+dateSuffixx+", 2024']";
        AddCouponPage.getInstance().getCalendarDate(driver, dateKey).click();
    }
}
