package steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import pojo.Order;

import static constant.Endpoints.CANCEL_ORDER;
import static constant.Endpoints.ORDERS;
import static io.restassured.RestAssured.given;

public class OrderSteps {
    @Step("Создание заказа")
    public static ValidatableResponse orderCreate(Order order) {
        return given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(ORDERS)
                .then();
    }

    @Step("Получение списка заказов")
    public static ValidatableResponse getOrderList() {
        return given()
                .get(ORDERS)
                .then();
    }

    @Step("Отмена заказа")
    public static ValidatableResponse cancelOrder(int track) {
        return given()
                .header("Content-type", "application/json")
                .body(track)
                .when()
                .put(CANCEL_ORDER)
                .then();
    }
}
