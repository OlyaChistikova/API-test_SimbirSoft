package tests;

import helpers.BaseRequests;
import org.hamcrest.Matchers;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import pojo.Addition;
import pojo.Entity;
import java.io.IOException;
import static io.restassured.RestAssured.given;
import io.qameta.allure.Step;

/**
 * Тесты для проверки создания сущностей.
 */
public class CreateEntityTest extends BaseTest {

    /**
     * Тест, который проверяет создание сущности с сериализацией.
     */
    @Test
    @Step("Test creating entity with serialization")
    public void testCreateEntityWithSerialization() {
        createEntity = createTestEntity();

        // Добавляем новую сущность
        Response createResponse = addEntity(createEntity);

        // Получаем ID только что созданной сущности
        entityId = Integer.valueOf(createResponse.asString());

        // Проверяем, что созданная сущность присутствует в ответе
        verifyEntityCreated();
    }

    /**
     * Тест, который проверяет создание сущности с сериализацией и десериализацией.
     *
     * @throws IOException если возникла ошибка при десериализации ответа.
     */
    @Test
    @Step("Test creating entity with serialization and deserialization")
    public void testCreateEntityWithSerializationAndDeserialization() throws IOException {
        createEntity = createTestEntity();

        // Добавляем новую сущность
        Response createResponse = addEntity(createEntity);

        // Получаем ID созданной сущности для проверки ее успешного создания
        entityId = Integer.valueOf(createResponse.asString());

        // Проверяем созданную сущность по ID
        verifyEntityById(entityId);
    }

    /**
     * Метод, который выполняется после каждого теста для удаления созданного пользователя.
     */
    @AfterMethod
    @Step("Delete user after creation with ID: {entityId}")
    public void deleteUserAfterCreation() {
        BaseRequests.deleteUserById(entityId);
    }

    /**
     * Создает тестовую сущность.
     *
     * @return объект Entity с тестовыми данными.
     */
    @Step("Create test entity")
    private Entity createTestEntity() {
        return Entity.builder()
                .id(null)
                .addition(Addition.builder()
                        .additional_info("Дополнительные сведения")
                        .additional_number(123)
                        .build())
                .importantNumbers("456, 789")
                .title("Тестовая сущность")
                .verified(true)
                .build();
    }

    /**
     * Добавляет новую сущность.
     *
     * @param entity сущность для добавления.
     * @return ответ от сервера.
     */
    @Step("Add new entity to the system")
    private Response addEntity(Entity entity) {
        return given()
                .spec(requestSpecification)
                .contentType("application/json")
                .body(entity)
                .when()
                .post(CREATE_USER_PATH)
                .then()
                .statusCode(200)
                .extract().response();
    }

    /**
     * Проверяет, что созданная сущность присутствует в ответе.
     */
    @Step("Verify created entity in the list of all entities")
    private void verifyEntityCreated() {
        Response allEntitiesResponse = given()
                .spec(requestSpecification)
                .when()
                .get(GETALL_USER_PATH)
                .then()
                .statusCode(200)
                .extract().response();

        // Проверка, что ожидаемая сущность присутствует
        allEntitiesResponse.then()
                .body("entity[-1].title", Matchers.equalTo("Тестовая сущность"));
    }
    /**
     * Проверяет сущность по ID.
     *
     * @param entityId ID сущности для проверки.
     * @throws IOException если возникла ошибка при десериализации ответа.
     */
    @Step("Verify entity by ID: {entityId}")
    private void verifyEntityById(Integer entityId) throws IOException {
        Response response = given()
                .spec(requestSpecification)
                .when()
                .get(GET_USER_PATH + entityId)
                .then()
                .statusCode(200)
                .extract().response();

        // Десериализация сущности из JSON-ответа
        objectMapper.readValue(response.asString(), Entity.class);
    }
}