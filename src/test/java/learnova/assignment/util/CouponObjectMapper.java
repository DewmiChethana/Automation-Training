package learnova.assignment.util;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import static io.restassured.RestAssured.*;

public class CouponObjectMapper {

    private final static CouponObjectMapper instance = new CouponObjectMapper();

    private CouponObjectMapper() {
    }

    public Header getRequestHeader() {
        String authorization = getAuthorization();
        Header header = new Header("Authorization", "Bearer " + authorization);
        return header;
    }

    public JSONObject getNewCoupon() {
        JSONObject coupon = new JSONObject();
        coupon.put("couponType", 0);
        coupon.put("couponCode", CouponUtil.getNewCouponCode(7));
        coupon.put("couponCourseType", CouponUtil.getRandomNumber(1, 0));
        coupon.put("boundedCourseId", coupon.get("couponCourseType").equals("0") ? "" : CouponUtil.getCourseId().toString());
        coupon.put("discountType", coupon.get("couponCourseType").equals("0") ? 0 : CouponUtil.getRandomNumber(1, 0));
        coupon.put("discountAmount", CouponUtil.getRandomNumber(99, 1));
        coupon.put("usageLimit", CouponUtil.getRandomNumber(9999, 0));
        coupon.put("activeDate", CouponUtil.getDate(-1));
        coupon.put("expireDate", CouponUtil.getDate(30));
        return coupon;
    }
    public JSONObject getNewCouponForUi() {
        JSONObject coupon = new JSONObject();
        coupon.put("couponType", 0);
        coupon.put("couponCode", CouponUtil.getNewCouponCode(7));
        coupon.put("couponCourseType", CouponUtil.getRandomNumber(1, 0));
        coupon.put("boundedCourseId", coupon.get("couponCourseType").equals("0") ? "All" : CouponUtil.getCourseName());
        coupon.put("discountType", coupon.get("couponCourseType").equals("0") ? 0 : CouponUtil.getRandomNumber(1, 0));
        coupon.put("discountAmount", CouponUtil.getRandomNumber(99, 1));
        coupon.put("usageLimit", CouponUtil.getRandomNumber(9999, 0));
        coupon.put("activeDate", CouponUtil.getDate(-1));
        coupon.put("expireDate", CouponUtil.getDate(30));
        return coupon;
    }

    public JSONObject getOverUsageCoupon(JSONObject coupon) {
        coupon.put("usageLimit", 99999);
        return coupon;
    }

    public JSONObject getCouponWithOutOfRangeUsagePercentage(JSONObject coupon) {
        coupon.put("discountType", 0);
        coupon.put("discountAmount", 105);
        return coupon;
    }

    public JSONObject getPendingCoupon(JSONObject coupon) {
        coupon.put("activeDate", CouponUtil.getDate(10));
        coupon.put("expireDate", CouponUtil.getDate(40));
        return coupon;
    }
    public JSONObject getExpireCoupon(JSONObject coupon) {
        coupon.put("activeDate", CouponUtil.getDate(-30));
        coupon.put("expireDate", CouponUtil.getDate(-1));
        return coupon;
    }

    public JSONObject getCouponFromDB(boolean editable) {
        JSONArray jsonArray = getCopunsFromDB();
        for (int x = 0; x < jsonArray.size(); ) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(x);
            //temporary remove id 702 because it was corrupted
            if (editable) {
                if ((Long) jsonObject.get("usageCount") == 0 && jsonObject.get("active").equals(true) &&
                    (Long) jsonObject.get("id") != 702 && (Long) jsonObject.get("id") != 352) {
                    return jsonObject;
                }
            } else {
                if ((Long) jsonObject.get("usageCount") != 0 && !jsonObject.get("active").equals(true) &&
                    (Long) jsonObject.get("id") != 702 && (Long) jsonObject.get("id") != 352) {
                    return jsonObject;
                }
            }
            x++;
        }
        return null;
    }

    public JSONObject updateCoupon(JSONObject coupon) {
        coupon.put("couponType", 0);
        coupon.put("couponCode", CouponUtil.getNewCouponCode(4));
        coupon.put("couponCourseType", CouponUtil.getRandomNumber(1, 0));
        coupon.put("boundedCourseId", coupon.get("couponCourseType").equals("0") ? "" : CouponUtil.getCourseId().toString());
        coupon.put("discountType", coupon.get("couponCourseType").equals("0") ? 0 : CouponUtil.getRandomNumber(1, 0));
        coupon.put("discountAmount", CouponUtil.getRandomNumber(99, 1));
        coupon.put("usageLimit", CouponUtil.getRandomNumber(9999, 0));
        coupon.put("activeDate", CouponUtil.getDate(0));
        coupon.put("expireDate", CouponUtil.getDate(20));
        return coupon;
    }

    public JSONObject getCouponWithUsedCoupenCode(JSONObject coupon) {
        String coupenCode = CouponUtil.getUsedCoupenCode();
        coupon.put("couponCode", CouponUtil.getUsedCoupenCode());
        return coupon;
    }

    public String getCoupenIdForChangeStatus(String status, boolean active) {
        try {
            JSONArray jsonArray = getCopunsFromDB();
            Date today = new Date();
            String coupenId="";
            String statusVale="";
            for (int x = 0; x < jsonArray.size(); ) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(x);
                Date activeDate = new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.get("activeDate").toString().substring(0,9));
                Date expireDate = new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.get("expireDate").toString().substring(0,9));
                //temporary remove id 702 because it was corrupted
                if(active){
                    statusVale = "true";
                }else{
                    statusVale = "false";
                }
                if (status.equals("Active")) {
                    if (today.compareTo(activeDate)>0 && expireDate.compareTo(today)>0 && jsonObject.get("active").toString().equals(statusVale)) {
                        coupenId = jsonObject.get("id").toString();
                        break;
                    }
                } else if(status.equals("Pending")){
                    if (activeDate.compareTo(today)>0 && jsonObject.get("active").toString().equals(statusVale)) {
                        coupenId = jsonObject.get("id").toString();
                        break;
                    }
                } else if(status.equals("Expire")){
                    if (today.compareTo(expireDate)>0 && jsonObject.get("active").toString().equals(statusVale)) {
                        coupenId = jsonObject.get("id").toString();
                        break;
                    }
                }
                x++;
            }
            return coupenId;
        } catch (java.text.ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private String getAuthorization() {
        try {
            baseURI = Constant.getBaseUrlApi();
            JSONObject request = new JSONObject();
            request.put("email", "skfadmin@gmail.com");
            request.put("password", "SKF@Admin_2023");
            Response response = given().
                    contentType(ContentType.JSON).accept(ContentType.JSON).
                    body(request.toJSONString()).
                    when().
                    post("/api/authenticate");
            JSONParser parser = new JSONParser();
            JSONObject json = null;
            json = (JSONObject) parser.parse(response.asString());
            return json.get("id_token").toString();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private JSONArray getCopunsFromDB() {
        try {
            baseURI = Constant.getBaseUrlApi();
            Response response = given().
                    when().
                    header(getRequestHeader()).
                    contentType(ContentType.JSON).accept(ContentType.JSON).
                    get("/api/admin/coupons?page=0&size=10000");
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = null;
            jsonArray = (JSONArray) parser.parse(response.asString());
            return jsonArray;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public WebDriver getDriver()
    {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().timeouts().scriptTimeout(Duration.ofMinutes(5));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofMinutes(1));
        driver.manage().window().maximize();
        return driver;
    }

    public static CouponObjectMapper getInstance() {
        return instance;
    }

}
