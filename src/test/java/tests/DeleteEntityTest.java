package tests;

import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pojo.Addition;
import pojo.Entity;
import static io.restassured.RestAssured.given;
import io.qameta.allure.Step;

/**
 * Тесты для проверки удаления сущностей.
 */
public class DeleteEntityTest extends BaseTest {

    /**
     * Подготовка тестовой сущности для проверки её удаления.
     */
    @BeforeMethod
    @Step("Create an entity for deletion test")
    public void testToCreateEntityForDeleteEntityById() {
        // Создаем объект Entity для проверки его удаления
        createEntity = Entity.builder()
                .id(null)
                .addition(Addition.builder()
                        .additional_info("Будет удалена")
                        .additional_number(123)
                        .build())
                .importantNumbers("456, 789")
                .title("Удаляемая сущность")
                .verified(true)
                .build();

        // Добавляем новую сущность
        Response createResponse = given()
                .spec(requestSpecification)
                .contentType("application/json")
                .body(createEntity)
                .when()
                .post(CREATE_USER_PATH)
                .then()
                .statusCode(200)
                .extract().response();

        // Получаем ID созданной сущности для проверки ее успешного удаления
        entityId = Integer.valueOf(createResponse.asString());
    }

    /**
     * Тест, который проверяет удаление сущности по ID.
     */
    @Test
    @Step("Test delete entity by ID")
    public void testDeleteEntityById() {
        // Удаляем созданную сущность по ID
        deleteEntityById(entityId);

        // Проверка, что сущность была удалена
        verifyEntityDeleted(entityId);
    }

    /**
     * Удаляет сущность по указанному ID.
     *
     * @param entityId ID сущности для удаления.
     */
    @Step("Delete entity by ID: {entityId}")
    private void deleteEntityById(Integer entityId) {
        given()
                .when()
                .delete(DELETE_USER_PATH + entityId)
                .then()
                .statusCode(204);
    }

    /**
     * Проверяет, что сущность удалена, проверяя ответ по ее ID.
     *
     * @param entityId ID сущности для проверки.
     */
    @Step("Verify that entity with ID {entityId} is deleted")
    private void verifyEntityDeleted(Integer entityId) {
        given()
                .when()
                .get(GET_USER_PATH + entityId)
                .then()
                .statusCode(500);
    }
}