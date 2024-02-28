import pojo.Order;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import steps.OrderSteps;


import static constant.Data.*;
import static org.hamcrest.Matchers.greaterThan;

@RunWith(Parameterized.class)
public class CreateOrderTest extends BaseTest {
    private final Order order;

    public CreateOrderTest(Order order) {
        this.order = order;
    }

    @Parameterized.Parameters
    public static Object[][] testData() {
        return new Object[][]{
                {new Order(FIRST_NAME, LAST_NAME, ADDRESS, METRO_STATION, PHONE, RENT_TIME, DELIVERY_DATE, COMMENT, COLOUR_BLACK)},
                {new Order(FIRST_NAME, LAST_NAME, ADDRESS, METRO_STATION, PHONE, RENT_TIME, DELIVERY_DATE, COMMENT, COLOUR_GREY)},
                {new Order(FIRST_NAME, LAST_NAME, ADDRESS, METRO_STATION, PHONE, RENT_TIME, DELIVERY_DATE, COMMENT, BOTH_COLOURS)},
                {new Order(FIRST_NAME, LAST_NAME, ADDRESS, METRO_STATION, PHONE, RENT_TIME, DELIVERY_DATE, COMMENT, WITHOUT_COLOURS)},
        };
    }

    @Test
    @DisplayName("Создание заказа")
    public void createOrder() {
        ValidatableResponse response = OrderSteps.orderCreate(order);
        track = response.extract().path("track");
        response.assertThat().body("track", greaterThan(0)).statusCode(201);
    }
}
