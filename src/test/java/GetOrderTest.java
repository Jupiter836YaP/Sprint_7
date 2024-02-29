import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import pojo.Order;
import steps.OrderSteps;

import static constant.Data.*;
import static constant.ErrorMessage.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class GetOrderTest extends BaseTest{
    @Test
    @DisplayName("Получение существующего заказа")
    public void getOrder() {
        Order order = new Order(FIRST_NAME, LAST_NAME, ADDRESS, METRO_STATION, PHONE, RENT_TIME, DELIVERY_DATE, COMMENT, COLOUR_BLACK);
        track = OrderSteps.orderCreate(order).extract().path("track");
        ValidatableResponse response = OrderSteps.getOrder(track);
        response.assertThat().body("order", notNullValue());
    }

    @Test
    @DisplayName("Получение несуществующего заказа")
    public void getNonexistenceOrder() {
        ValidatableResponse response = OrderSteps.getOrder(NONEXISTENT_TRACK);
        response.assertThat().body("message", equalTo(MESSAGE_FOR_NONEXISTENCE_TRACK_REQUEST_GET_ORDER)).statusCode(404);
    }

    @Test
    @DisplayName("Получение заказа, если передан пустой track")
    public void getOrderWithEmptyTrack() {
        ValidatableResponse response = OrderSteps.getOrderEmpty();
        response.assertThat().body("message", equalTo(MESSAGE_FOR_EMPTY_TRACK_REQUEST_GET_ORDER)).statusCode(400);

    }
}
