package learnova.assignment.api.tests.coupon_management;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import learnova.assignment.util.Constant;
import learnova.assignment.util.CouponObjectMapper;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class CheckCouponsAvailability {
    protected Header header;

    @BeforeClass
    private void setuptest() {
        baseURI = Constant.getBaseUrlApi();
        header = CouponObjectMapper.getInstance().getRequestHeader();
    }

    @Test
    public void ChangeTheActiveFalseCouponToTrue() {
        String couponId = CouponObjectMapper.getInstance().getCoupenIdForChangeStatus("Active",false);
        given().
                header(header).
                contentType(ContentType.JSON).accept(ContentType.JSON).
                when().
                patch("/api/admin/coupons/state-change/" + couponId + "?isActive=true").then().
                statusCode(200);
    }
}
