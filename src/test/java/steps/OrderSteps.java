package steps;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import pojo.Order;

import static constant.Endpoints.*;


public class OrderSteps {
    @Step("Создание заказа")
    public static ValidatableResponse orderCreate(Order order) {
        return RestAssured.given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(ORDERS)
                .then();
    }

    @Step("Получение списка заказов")
    public static ValidatableResponse getOrderList() {
        return RestAssured.given()
                .get(ORDERS)
                .then();
    }

    @Step("Отмена заказа")
    public static ValidatableResponse cancelOrder(int track) {
        return RestAssured.given()
                .header("Content-type", "application/json")
                .body(track)
                .when()
                .put(CANCEL_ORDER)
                .then();
    }

    @Step("Получить конкретный заказ")
    public static ValidatableResponse getOrder(int track) {
        return RestAssured.given()
                .queryParam("t", track)
                .get(GET_ORDER)
                .then();
    }

    @Step("Получить конкретный заказ, но не передавая track")
    public static ValidatableResponse getOrderEmpty() {
        return RestAssured.given()
                .get(GET_ORDER_EMPTY)
                .then();
    }

    @Step("Принять заказ")
    public static ValidatableResponse acceptOrder(int courierId, int orderId) {
        return RestAssured.given()
                .queryParam("courierId", courierId)
                .put(String.format(ACCEPT_ORDER, orderId))
                .then();
    }

    @Step("Принять заказ, но не передавая courierId")
    public static ValidatableResponse acceptOrderWithoutCourierId(int orderId) {
        return RestAssured.given()
                .put(String.format(ACCEPT_ORDER, orderId))
                .then();
    }
}
