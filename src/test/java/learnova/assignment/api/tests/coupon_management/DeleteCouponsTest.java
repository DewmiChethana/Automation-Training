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

public class DeleteCouponsTest {
    protected Header header;

    private JSONObject coupon;
    @BeforeClass
    private void setuptest() {
        baseURI = Constant.getBaseUrlApi();
        header = CouponObjectMapper.getInstance().getRequestHeader();
    }
    @BeforeTest
    private void getCoupon() throws ParseException {
        coupon = CouponObjectMapper.getInstance().getCouponFromDB(true);
    }

    @Test
    public void DeleteNotUsedCoupon()  {
        String id= coupon.get("id").toString();
        given().
                header(header).
                contentType(ContentType.JSON).accept(ContentType.JSON).
                body(coupon.toJSONString()).
                when().
                delete("/api/admin/coupons/"+id).then().
                statusCode(204);
    }

    @Test
    public void DeleteUsedCoupon()  {
        coupon = CouponObjectMapper.getInstance().getCouponFromDB(false);
        String id= coupon.get("id").toString();
        given().
                header(header).
                contentType(ContentType.JSON).accept(ContentType.JSON).
                body(coupon.toJSONString()).
                when().
                delete("/api/admin/coupons/"+id).then().
                statusCode(400).
                body("detail",equalTo( "Coupon cannot be deleted as it has been used."));
    }
}
