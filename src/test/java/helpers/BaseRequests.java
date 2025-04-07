package helpers;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class BaseRequests {
    /**
     * Запрос спецификации для настройки REST запросов.
     */
    public static RequestSpecification requestSpecification;
    /**
     * Инициализация спецификации запроса, в которой настраивается
     * основной URI и тип содержимого.
     *
     * @return спецификация запроса с установленными параметрами
     * @throws IOException если происходит ошибка при получении URL API
     */
    public static RequestSpecification initRequestSpecification() throws IOException{
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder
                .setContentType(ContentType.JSON)
                .setBaseUri(ParametersProvider.getProperty("apiUrl"));
        requestSpecification = requestSpecBuilder.build();
        return requestSpecification;
    }

    /**
     * Удаление пользователя с заданным id
     *
     * @param userId id сущности, которую необходимо удалить
     */
    public static void deleteUserById(String userId) {
        given()
                .when()
                .delete(" /api/delete/" + userId)
                .then()
                .statusCode(204);
    }
}
