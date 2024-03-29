package steps;

import io.restassured.RestAssured;
import pojo.Courier;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static constant.Endpoints.*;


public class CourierSteps {

    @Step("Создание курьера")
    public static ValidatableResponse courierCreate(Courier courier){
        return RestAssured.given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(CREATE_COURIER)
                .then();
    }

    @Step("Логин курьера и получение ID для удаления")
    public static ValidatableResponse courierLogin(Courier courier){
        return RestAssured.given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(LOGIN_COURIER)
                .then();
    }

    @Step("Удаление курьера")
    public static ValidatableResponse courierDelete(int id){
        return RestAssured.given()
                .delete(DELETE_COURIER + id)
                .then();
    }

    @Step("Удаление курьера, без передачи id")
    public static ValidatableResponse courierDeleteWithoutId(){
        return RestAssured.given()
                .delete(DELETE_COURIER)
                .then();
    }

}
