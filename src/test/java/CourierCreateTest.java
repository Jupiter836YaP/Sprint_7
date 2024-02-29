import pojo.Courier;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import steps.CourierSteps;

import static constant.Data.*;
import static constant.ErrorMessage.*;
import static org.hamcrest.CoreMatchers.equalTo;


public class CourierCreateTest extends BaseTest {
    Courier courier = new Courier(LOGIN, PASSWORD, FIRST_NAME);
    Courier courierWithoutLogin = new Courier(EMPTY_LOGIN, PASSWORD, FIRST_NAME);
    Courier courierWithoutPassword = new Courier(LOGIN, EMPTY_PASSWORD, FIRST_NAME);
    Courier courierWithoutName = new Courier(LOGIN, PASSWORD, EMPTY_FIRST_NAME);

    @Test
    @DisplayName("Создания курьера со всеми обязательными полями")
    public void createCourierTest() {
        ValidatableResponse response = CourierSteps.courierCreate(courier);
        courierId = CourierSteps.courierLogin(courier).extract().path("id");
        response.assertThat().body("ok", equalTo(true)).and().statusCode(201);
    }

    @Test
    @DisplayName("Создания курьера с существующим логином")
    public void createDuplicateCourierTest() {
        CourierSteps.courierCreate(courier);
        ValidatableResponse responseDuplicate = CourierSteps.courierCreate(courier);
        courierId = CourierSteps.courierLogin(courier).extract().path("id");
        responseDuplicate.assertThat().body("message", equalTo(MESSAGE_FOR_INCORRECT_REQUEST_CREATE_COURIER)).statusCode(409);
    }

    @Test
    @DisplayName("Создания курьера, без обязательного поля - логина")
    public void createCourierWithoutLoginTest() {
        ValidatableResponse response = CourierSteps.courierCreate(courierWithoutLogin);
        response.assertThat().body("message", equalTo(MESSAGE_FOR_INCOMPLETE_REQUEST_CREATE_COURIER)).statusCode(400);
    }

    @Test
    @DisplayName("Создания курьера, без обязательного поля - пароля")
    public void createCourierWithoutPasswordTest() {
        ValidatableResponse response = CourierSteps.courierCreate(courierWithoutPassword);
        response.assertThat().body("message", equalTo(MESSAGE_FOR_INCOMPLETE_REQUEST_CREATE_COURIER)).statusCode(400);
    }

    @Test
    @DisplayName("Создания курьера ,без обязательного поля - имени")
    public void createCourierWithoutNameTest() {
        ValidatableResponse response = CourierSteps.courierCreate(courierWithoutName);
        courierId = CourierSteps.courierLogin(courierWithoutName).extract().path("id");
        response.assertThat().body("ok", equalTo(true)).and().statusCode(201);
    }

}
