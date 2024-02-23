package learnova.assignment.api.tests.coupon_management;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import learnova.assignment.util.Constant;
import learnova.assignment.util.CouponObjectMapper;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class ChangeTheStatusTest {

    protected Header header;

    @BeforeClass
    private void setuptest() {
        baseURI = Constant.getBaseUrlApi();
        header = CouponObjectMapper.getInstance().getRequestHeader();
    }

    @Test
    public void ChangeTheActiveFalseCoupenToTrue() {
        String couponId = CouponObjectMapper.getInstance().getCoupenIdForChangeStatus("Active",false);
        given().
                header(header).
                contentType(ContentType.JSON).accept(ContentType.JSON).
                when().
                patch("/api/admin/coupons/state-change/" + couponId + "?isActive=true").then().
                statusCode(200);
    }

    @Test
    public void ChangeTheActiveTrueCoupenToFalse() {
        String couponId = CouponObjectMapper.getInstance().getCoupenIdForChangeStatus("Active",true);
        given().
                header(header).
                contentType(ContentType.JSON).accept(ContentType.JSON).
                when().
                patch("/api/admin/coupons/state-change/" + couponId + "?isActive=false").then().
                statusCode(200);
    }

    @Test
    public void ChangeThePendingTrueCoupenToFalse() {
        String couponId = CouponObjectMapper.getInstance().getCoupenIdForChangeStatus("Pending",true);
        given().
                header(header).
                contentType(ContentType.JSON).accept(ContentType.JSON).
                when().
                patch("/api/admin/coupons/state-change/" + couponId + "?isActive=false").then().
                statusCode(403);
    }

    @Test
    public void ChangeThePendingFalseCoupenToTrue() {
        String couponId = CouponObjectMapper.getInstance().getCoupenIdForChangeStatus("Pending",false);
        given().
                header(header).
                contentType(ContentType.JSON).accept(ContentType.JSON).
                when().
                patch("/api/admin/coupons/state-change/" + couponId + "?isActive=true").then().
                statusCode(400);
    }

    @Test
    public void ChangeTheExpireTrueCoupenToFalse() {
        String couponId = CouponObjectMapper.getInstance().getCoupenIdForChangeStatus("Expire",true);
        given().
                header(header).
                contentType(ContentType.JSON).accept(ContentType.JSON).
                when().
                patch("/api/admin/coupons/state-change/" + couponId + "?isActive=false").then().
                statusCode(200);
    }

    @Test
    public void ChangeTheExpireFalseCoupenToTrue() {
        String couponId = CouponObjectMapper.getInstance().getCoupenIdForChangeStatus("Expire",false);
        given().
                header(header).
                contentType(ContentType.JSON).accept(ContentType.JSON).
                when().
                patch("/api/admin/coupons/state-change/" + couponId + "?isActive=true").then().
                statusCode(400);
    }
}
