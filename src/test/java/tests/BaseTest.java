package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import helpers.BaseRequests;
import io.restassured.specification.RequestSpecification;
import io.qameta.allure.Step;
import org.testng.annotations.BeforeClass;
import pojo.Entity;
import java.io.IOException;
import static org.testng.Assert.assertNotNull;

/**
 * Базовый класс для тестов, содержащий общие настройки и методы.
 */
public class BaseTest {

    /**
     * Объект для построения запросов.
     */
    public RequestSpecification requestSpecification;

    /**
     * Созданная сущность.
     */
    public Entity createEntity;

    /**
     * ID сущности.
     */
    public Integer entityId;

    /**
     * Объект для работы с JSON.
     */
    protected final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Путь для удаления пользователя по ID.
     */
    public static final String DELETE_USER_PATH = "/api/delete/";

    /**
     * Путь для создания нового пользователя.
     */
    public static final String CREATE_USER_PATH = "/api/create/";

    /**
     * Путь для получения всех пользователей.
     */
    public static final String GETALL_USER_PATH = "/api/getAll";

    /**
     * Путь для получения пользователя по ID.
     */
    public static final String GET_USER_PATH = "/api/get/";

    /**
     * Путь для обновления пользователя по ID.
     */
    public static final String PATCH_USER_PATH = "/api/patch/";

    /**
     * Метод инициализации, который выполняется перед запуском тестов.
     * Устанавливает спецификацию запроса и проверяет её корректность.
     *
     * @throws IOException если возникла ошибка при инициализации спецификации запроса.
     */
    @BeforeClass
    @Step("Initialize request specification")
    public void setup() throws IOException {
        requestSpecification = BaseRequests.initRequestSpecification();
        assertNotNull(requestSpecification, "RequestSpecification should not be null");
    }
}