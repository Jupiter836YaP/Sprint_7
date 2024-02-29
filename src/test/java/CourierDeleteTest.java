import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import pojo.Courier;
import steps.CourierSteps;

import static constant.Data.*;
import static constant.ErrorMessage.*;
import static org.hamcrest.Matchers.equalTo;

public class CourierDeleteTest extends BaseTest{

    @Test
    @DisplayName("Удаление существующего курьера")
    public void deleteExistCourier(){
        Courier courier = new Courier(LOGIN,PASSWORD,FIRST_NAME);
        CourierSteps.courierCreate(courier);
        courierId = CourierSteps.courierLogin(courier).extract().path("id");
        ValidatableResponse response = CourierSteps.courierDelete(courierId);
        response.assertThat().body("ok", equalTo(true)).statusCode(200);
}

    @Test
    @DisplayName("Удаление несуществующего курьера")
    public void deleteNonexistenceCourier() {
        ValidatableResponse response = CourierSteps.courierDelete(NONEXISTENT_ID);
        response.assertThat().body("message", equalTo(MESSAGE_FOR_NONEXISTENCE_ID_REQUEST_DELETE_COURIER)).statusCode(404);
    }

    @Test
    @DisplayName("Удаление курьера, без передачи id")
    public void deleteCourierWithoutId() {
        ValidatableResponse response = CourierSteps.courierDeleteWithoutId();
        response.assertThat().body("message", equalTo(MESSAGE_FOR_WITHOUT_ID_REQUEST_DELETE_COURIER)).statusCode(404);
    }

}
