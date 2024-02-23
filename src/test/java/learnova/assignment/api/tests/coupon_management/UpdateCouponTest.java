package learnova.assignment.api.tests.coupon_management;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import learnova.assignment.util.Constant;
import learnova.assignment.util.CouponObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UpdateCouponTest {

    protected Header header;

    private JSONObject coupon;

    private CouponObjectMapper objectMapper = CouponObjectMapper.getInstance();

    @BeforeClass
    private void setuptest() throws ParseException {
        baseURI = Constant.getBaseUrlApi();
        header = CouponObjectMapper.getInstance().getRequestHeader();
    }

    @BeforeTest
    private void getCoupon() throws ParseException {
        coupon = CouponObjectMapper.getInstance().getCouponFromDB(true);
        System.out.println("working");
    }

    @Test
    public void UpdateCouponWithCouponObject() {
        JSONObject updatedCoupon= objectMapper.updateCoupon(coupon);
        String id = updatedCoupon.get("id").toString();
        given().
                header(header).
                contentType(ContentType.JSON).accept(ContentType.JSON).
                body(updatedCoupon.toJSONString()).
                when().
                put("/api/admin/coupons/"+id).then().
                statusCode(200).
                body("couponCode",equalTo(updatedCoupon.get("couponCode")));
    }

    @Test
    public void UpdateCouponWithCouponObjectContainUsedCoupnCode() {
        JSONObject updatedCoupon= objectMapper.getCouponWithUsedCoupenCode(coupon);
        String id = updatedCoupon.get("id").toString();
        given().
                header(header).
                contentType(ContentType.JSON).accept(ContentType.JSON).
                body(updatedCoupon.toJSONString()).
                when().
                put("/api/admin/coupons/"+id).then().
                statusCode(400).
                body("detail",equalTo("Coupon code already exists."));
    }

    @Test
    public void UpdateCouponWithCouponObjectContainInvalidUsagePercentage() {
        JSONObject updatedCoupon= objectMapper.getCouponWithOutOfRangeUsagePercentage(coupon);
        String id = updatedCoupon.get("id").toString();
        given().
                header(header).
                contentType(ContentType.JSON).accept(ContentType.JSON).
                body(updatedCoupon.toJSONString()).
                when().
                put("/api/admin/coupons/"+id).then().
                statusCode(400).
                body("detail",equalTo("Coupon discount amount must be not null, more than 0, and below 101."));
    }

    @Test
    public void UpdateCouponWithCouponObjectContainOutofRangeUsageCount() {
        JSONObject updatedCoupon= objectMapper.getOverUsageCoupon(coupon);
        String id = updatedCoupon.get("id").toString();
        given().
                header(header).
                contentType(ContentType.JSON).accept(ContentType.JSON).
                body(updatedCoupon.toJSONString()).
                when().
                put("/api/admin/coupons/"+id).then().
                statusCode(400).
                body("detail",equalTo("Coupon discount amount must be not null, more than 0, and below 101."));
    }
}
