package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pojo.Addition;
import pojo.Entity;
import static io.restassured.RestAssured.given;


public class GetEntityTest extends BaseTest {

    @BeforeMethod
    public void createEntity() {
        // Создаем объект Entity
        createEntity = Entity.builder()
                .id(4)
                .title("тест")
                .importantNumbers("42, 87, 15")
                .addition(Addition.builder()
                        .additional_info("Тестовая сущность")
                        .additional_number(123)
                        .build())
                .verified(true)
                .build();

        //Получение id сущности
        entityId = createEntity.getId();
    }

    @Test
    public void testGetEntityById() {
        // Получение сущности по id и преобразование в объект Entity
        Entity actualEntity = given()
                .when()
                .get(GET_USER_PATH + entityId)
                .then()
                .statusCode(200)
                .extract().as(Entity.class);

        // Сравнение полученного объекта с эталонным
        assertEntitiesAreEqual(createEntity, actualEntity);
    }

    private void assertEntitiesAreEqual(Entity expected, Entity actual) {
        // Проверка всех полей объекта
        Assert.assertEquals(actual.getId(), expected.getId(), "ID не совпадают");
        Assert.assertEquals(actual.getTitle(), expected.getTitle(), "Title не совпадают");
        Assert.assertEquals(actual.getImportantNumbers(), expected.getImportantNumbers(), "Important Numbers не совпадают");
        Assert.assertTrue(actual.getAddition().equals(expected.getAddition()), "Addition не совпадают");
        Assert.assertEquals(actual.getVerified(), expected.getVerified(), "Verified не совпадают");
    }
}
