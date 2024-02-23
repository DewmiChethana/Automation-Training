package learnova.assignment.api.tests.coupon_management;

import static io.restassured.RestAssured.*;

import io.restassured.http.*;

import learnova.assignment.util.Constant;
import learnova.assignment.util.CouponObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class CreateCouponsTest {

    protected Header header;

    private JSONObject coupon;

    @BeforeClass
    private void setuptest() throws ParseException {
        baseURI = Constant.getBaseUrlApi();
        header = CouponObjectMapper.getInstance().getRequestHeader();
    }

    private JSONObject getCoupon()  {
        return CouponObjectMapper.getInstance().getNewCoupon();
    }

    @Test
    public void CreateCouponWithActiveCouponObject()  {
        coupon = getCoupon();
        given().
                header(header).
                contentType(ContentType.JSON).accept(ContentType.JSON).
                body(coupon.toJSONString()).
                when().
                post("/api/admin/coupons").then().
                statusCode(201).
                body("id", greaterThan(0)).
                body("couponCode",equalTo(coupon.get("couponCode")));
    }

    @Test
    public void CreateCouponWithExpireCouponObject()  {
        coupon = getCoupon();
        coupon = CouponObjectMapper.getInstance().getExpireCoupon(coupon);
        given().
          header(header).
          contentType(ContentType.JSON).accept(ContentType.JSON).
          body(coupon.toJSONString()).
          when().
          post("/api/admin/coupons").then().
          statusCode(400).
          body("detail",equalTo("Coupon active date and expire date should be valid."));
    }

    @Test
    public void CreateCouponWithCouponObject()  {
        coupon = getCoupon();
        given().
          header(header).
          contentType(ContentType.JSON).accept(ContentType.JSON).
          body(coupon.toJSONString()).
          when().
          post("/api/admin/coupons").then().
          statusCode(201).
          body("id", greaterThan(0)).
          body("couponCode",equalTo(coupon.get("couponCode")));
    }

    @Test
    public void CreateCouponWithOverUsage() {
        coupon = getCoupon();
        JSONObject updatedCoupon = CouponObjectMapper.getInstance().getOverUsageCoupon(coupon);
        given().
                header(header).
                contentType(ContentType.JSON).accept(ContentType.JSON).
                body(updatedCoupon.toJSONString()).
                when().
                post("/api/admin/coupons").then().
                statusCode(400).
                body("detail",equalTo("Coupon usage limit must be not null, more than 0 and less than 10000."));
    }

    @Test
    public void CreateCouponWithOverper()  {
        coupon = getCoupon();
        JSONObject updatedCoupon = CouponObjectMapper.getInstance().getCouponWithOutOfRangeUsagePercentage(coupon);
        given().
                header(header).
                contentType(ContentType.JSON).accept(ContentType.JSON).
                body(updatedCoupon.toJSONString()).
                when().
                post("/api/admin/coupons").then().
                statusCode(400).
                body("detail",equalTo("Coupon discount amount must be not null, more than 0, and below 101."));
    }
}
