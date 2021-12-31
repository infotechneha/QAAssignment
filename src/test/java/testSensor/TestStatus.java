package testSensor;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class TestStatus {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

//*******Before Class which will call the Base URI
    @BeforeClass
    public void beforeclass() {
        RequestSpecBuilder rb = new RequestSpecBuilder().
                setBaseUri("http://localhost:8080").
                setBasePath("/v2").
                setContentType(ContentType.JSON). //Setting content type to JSON
                log(LogDetail.ALL);

        requestSpecification = rb.build();
        ResponseSpecBuilder rs = new ResponseSpecBuilder().
                        log(LogDetail.ALL);
        responseSpecification = rs.build();
    }

/*TEST01: Checking the post request */
    @Test(priority = 0)
    public void addsensor() {
        //request Payload
        String payload = "{\n" +
                "  \"name\": \"userTest\",\n" +
                "  \"in\": \"body\",\n" +
                "  \"description\": \"sensor object that needs to be added to the store\",\n" +
                "  \"required\": true,\n" +
                "  \"schema\": {\n" +
                "    \"$ref\": \"#/definitions/Sensor\"\n" +
                "  }";
        given(requestSpecification).
                body(payload).
                when().
                //POST Call
                post("/sensor").
                then().spec(responseSpecification).
                //Verifying the Response Status code
                assertThat().statusCode(405).
                //Verifying the Response body
                assertThat().body(equalToIgnoringCase("Invalid input"));
    }
    /*TEST02: Checking the get request*/
    @Test(priority = 1)
    public void checkgetrequest() {
        //request Payload
        String payload = "{\n" +
                "  \"name\": \"1234\",\n" +
                "  \"in\": \"path\",\n" +
                "  \"description\": \"ID of the sensor to return\",\n" +
                "  \"required\": true,\n" +
                "  \"type\": “integer”,\n" +
                "“format”:”int64”\n" +
                "}\n";
        given(requestSpecification).
                body(payload).
                when().
                //GET Call
                        get("/sensor").
                then().spec(responseSpecification).
                //Verifying the Response Status code
                        assertThat().statusCode(200).
                //Verifying the Response body
                        assertThat().body(equalToIgnoringCase("successful operation"));
    }
    /*TEST03: Checking the get request*/
    @Test(priority = 1)
    public void checkgetrequestinvalid() {
        //request Payload
        String payload = "{\n" +
                "  \"name\": \"abc\",\n" +
                "  \"in\": \"path\",\n" +
                "  \"description\": \"ID of the sensor to return\",\n" +
                "  \"required\": true,\n" +
                "  \"type\": “integer”,\n" +
                "“format”:”int64”\n" +
                "}\n";
        given(requestSpecification).
                body(payload).
                when().
                //GET Call
                        get("/sensor").
                then().spec(responseSpecification).
                //Verifying the Response Status code
                        assertThat().statusCode(400).
                //Verifying the Response body
                        assertThat().body(equalToIgnoringCase("Invalid ID supplied"));
    }
    /*TEST04: Checking the get request*/
    @Test(priority = 1)
    public void checkgetrequestnotfound() {
        //request Payload
        String payload = "{\n" +
                "  \"name\": \"0000\",\n" +
                "  \"in\": \"path\",\n" +
                "  \"description\": \"ID of the sensor to return\",\n" +
                "  \"required\": true,\n" +
                "  \"type\": “integer”,\n" +
                "“format”:”int64”\n" +
                "}\n";
        given(requestSpecification).
                body(payload).
                when().
                //GET Call
                        get("/sensor").
                then().spec(responseSpecification).
                //Verifying the Response Status code
                        assertThat().statusCode(404).
                //Verifying the Response body
                        assertThat().body(equalToIgnoringCase("sensor not found"));
    }

}
