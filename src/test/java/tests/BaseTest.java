package tests;

import helpers.BaseRequests;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

import java.io.IOException;

import static org.testng.Assert.assertNotNull;

public class BaseTest {

    public RequestSpecification requestSpecification;
    public String entityId;

    @BeforeClass
    public void setup() throws IOException {
        requestSpecification = BaseRequests.initRequestSpecification();
        assertNotNull(requestSpecification, "RequestSpecification should not be null");
    }
}
