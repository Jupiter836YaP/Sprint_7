import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import steps.OrderSteps;

import static org.hamcrest.Matchers.notNullValue;

public class GetOrderListTest extends BaseTest {

    @Test
    @DisplayName("Получение списка заказов")
    public void getOrderList() {
        ValidatableResponse response = OrderSteps.getOrderList();
        response.assertThat().body("orders", notNullValue()).statusCode(200);
    }
}
