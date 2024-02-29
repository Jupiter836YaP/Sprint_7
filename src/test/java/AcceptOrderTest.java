import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import pojo.Courier;
import pojo.Order;
import steps.CourierSteps;
import steps.OrderSteps;

import static constant.Data.*;
import static constant.ErrorMessage.*;
import static org.hamcrest.Matchers.equalTo;

public class AcceptOrderTest extends BaseTest{
    @Test
    @DisplayName("Принять заказ")
    public void acceptOrder() {
        Courier courier = new Courier(LOGIN, PASSWORD);
        Order order = new Order(FIRST_NAME, LAST_NAME, ADDRESS, METRO_STATION, PHONE, RENT_TIME, DELIVERY_DATE, COMMENT, COLOUR_BLACK);
        CourierSteps.courierCreate(courier);
        courierId = CourierSteps.courierLogin(courier).extract().path("id");
        track = OrderSteps.orderCreate(order).extract().path("track");
        orderId = OrderSteps.getOrder(track).extract().path("order.id");
        ValidatableResponse response = OrderSteps.acceptOrder(courierId, orderId);
        response.assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Принять заказ, не передавая courierId")
    public void acceptOrderWithoutCourierId() {
        Courier courier = new Courier(LOGIN, PASSWORD);
        Order order = new Order(FIRST_NAME, LAST_NAME, ADDRESS, METRO_STATION, PHONE, RENT_TIME, DELIVERY_DATE, COMMENT, COLOUR_BLACK);
        CourierSteps.courierCreate(courier);
        courierId = CourierSteps.courierLogin(courier).extract().path("id");
        track = OrderSteps.orderCreate(order).extract().path("track");
        orderId = OrderSteps.getOrder(track).extract().path("order.id");
        ValidatableResponse response = OrderSteps.acceptOrderWithoutCourierId(orderId);
        response.assertThat().body("message", equalTo(MESSAGE_FOR_EMPTY_ID_REQUEST_ACCEPT_ORDER)).statusCode(400);
    }

    @Test
    @DisplayName("Принять заказ, передав некорректный courierId")
    public void acceptOrderIncorrectCourierId() {
        Order order = new Order(FIRST_NAME, LAST_NAME, ADDRESS, METRO_STATION, PHONE, RENT_TIME, DELIVERY_DATE, COMMENT, COLOUR_BLACK);
        track = OrderSteps.orderCreate(order).extract().path("track");
        orderId = OrderSteps.getOrder(track).extract().path("order.id");
        ValidatableResponse response = OrderSteps.acceptOrder(INCORRECT_COURIER_ID, orderId);
        response.assertThat().body("message", equalTo(MESSAGE_FOR_NONEXISTENCE_TRACK_REQUEST_ACCEPT_ORDER)).statusCode(404);
    }

    @Test
    @DisplayName("Принять заказ, передав некорректный orderId")
    public void acceptOrderIncorrectOrderId() {
        Courier courier = new Courier(LOGIN, PASSWORD);
        CourierSteps.courierCreate(courier);
        courierId = CourierSteps.courierLogin(courier).extract().path("id");
        ValidatableResponse response = OrderSteps.acceptOrder(courierId,INCORRECT_ORDER_ID);
        response.assertThat().body("message", equalTo(MESSAGE_FOR_INCORRECT_ORDER_ID_REQUEST_ACCEPT_ORDER)).statusCode(404);
    }
}
