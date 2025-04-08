package tests;

import helpers.BaseRequests;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pojo.Addition;
import io.qameta.allure.Step;
import pojo.Entity;

import java.util.ArrayList;
import java.util.List;
import static io.restassured.RestAssured.given;

/**
 * Тесты для проверки получения всех сущностей.
 */
public class GetAllEntitiesTest extends BaseTest {

    private List<Integer> createdEntityIds = new ArrayList<>();

    @DataProvider(name = "entityDataProvider")
    public Object[][] entityDataProvider() {
        return new Object[][]{
                {"Дополнительные сведения 1", 124, "Добавленная сущность 1"},
                {"Дополнительные сведения 2", 125, "Добавленная сущность 2"},
                {"Дополнительные сведения 3", 126, "Добавленная сущность 3"}
        };
    }

    /**
     * Тест, который создает сущность и проверяет получение всех сущностей.
     */
    @Test(dataProvider = "entityDataProvider")
    @Step("Create entity and get all entities")
    public void testCreateEntityAndGetAll(String additionalInfo, int additionalNumber, String title) {
        Entity createEntity = Entity.builder()
                .id(null)
                .addition(Addition.builder()
                        .additional_info(additionalInfo)
                        .additional_number(additionalNumber)
                        .build())
                .importantNumbers("456, 789")
                .title(title)
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

        // Извлекаем ID созданной сущности и добавляем в список
        Integer entityId = Integer.valueOf(createResponse.asString());
        createdEntityIds.add(entityId);
    }

    /**
     * Тест для получения всех сущностей и проверки соответствия созданных.
     */
    @Test(dependsOnMethods = {"testCreateEntityAndGetAll"})
    @Step("Get all entities and verify created entities")
    public void testGetAllEntities() {
        // Получение всех сущностей через API
        List<Entity> allEntities = given()
                .spec(requestSpecification)
                .when()
                .get(GETALL_USER_PATH)
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("entity", Entity.class);

        // Получаем последние три сущности
        List<Entity> lastThreeEntities = allEntities.subList(Math.max(allEntities.size() - 3, 0), allEntities.size());

        // Сравниваем ID созданных сущностей с ID последних трех сущностей
        Assert.assertEquals(
                lastThreeEntities.stream().map(Entity::getId).toList(),
                createdEntityIds,
                "ID последних трех сущностей на сервере не совпадают с созданными ID"
        );
    }

    /**
     * Удаляет созданные сущности после выполнения теста.
     */
    @AfterMethod
    @Step("Delete created entities after test")
    public void deleteUserAfterCreation() {
        for (Integer entityId : createdEntityIds) {
            BaseRequests.deleteUserById(entityId); // Удаляем каждую созданную сущность
        }
        createdEntityIds.clear(); // Очищаем список после удаления
    }
}