import pojo.Courier;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import steps.CourierSteps;

import static constant.ErrorMessage.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

import static constant.Data.*;

public class CourierLoginTest extends BaseTest {

    @Test
    @DisplayName("Авторизация под несуществующим курьером")
    public void loginCourierNonexistentLoginTest() {
        Courier courierNonexistentLogin = new Courier(NONEXISTENT_LOGIN, PASSWORD);
        ValidatableResponse response = CourierSteps.courierLogin(courierNonexistentLogin);
        response.assertThat().body("message", equalTo(MESSAGE_FOR_INCORRECT_REQUEST_LOGIN_COURIER)).and().statusCode(404);
    }

    @Test
    @DisplayName("Авторизация под существующим курьером с некорректным паролем")
    public void loginCourierNonexistentPasswordTest() {
        Courier courierNonexistentPassword = new Courier(LOGIN, NONEXISTENT_PASSWORD);
        ValidatableResponse response = CourierSteps.courierLogin(courierNonexistentPassword);
        response.assertThat().body("message", equalTo(MESSAGE_FOR_INCORRECT_REQUEST_LOGIN_COURIER)).and().statusCode(404);
    }

    @Test
    @DisplayName("Авторизация без обязательного поля - логина")
    public void loginCourierWithoutLogin() {
        Courier courierWithoutLogin = new Courier(EMPTY_LOGIN, PASSWORD);
        ValidatableResponse response = CourierSteps.courierLogin(courierWithoutLogin);
        response.assertThat().body("message", equalTo(MESSAGE_FOR_INCOMPLETE_REQUEST_LOGIN_COURIER)).and().statusCode(400);
    }

    @Test
    @DisplayName("Авторизация без обязательного поля - пароля")
    public void loginCourierWithoutPassword() {
        Courier courierWithoutPassword = new Courier(LOGIN, EMPTY_PASSWORD);
        ValidatableResponse response = CourierSteps.courierLogin(courierWithoutPassword);
        response.assertThat().body("message", equalTo(MESSAGE_FOR_INCOMPLETE_REQUEST_LOGIN_COURIER)).and().statusCode(400);
    }

    @Test
    @DisplayName("Регистрация курьера с последующей авторизацией")
    public void loginCourierAfterRegister() {
        Courier courierAfterCreate = new Courier(LOGIN, PASSWORD, FIRST_NAME);
        CourierSteps.courierCreate(courierAfterCreate);
        ValidatableResponse responseLogin = CourierSteps.courierLogin(courierAfterCreate);
        courierId = CourierSteps.courierLogin(courierAfterCreate).extract().path("id");
        responseLogin.assertThat().body("id", greaterThan(0)).and().statusCode(200);
    }
}
