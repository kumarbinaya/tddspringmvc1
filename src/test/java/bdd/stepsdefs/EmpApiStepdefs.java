package bdd.stepsdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

public class EmpApiStepdefs {
    HttpHeaders headers;
    TestRestTemplate restTemplate;
    String addURI;
    ResponseEntity<String> response;
    private final String SERVER_URL = "http://localhost";
    private final String ENDPOINT = "/employee";
    @LocalServerPort
    private int port;
    @Given("^I Set POST employee service api endpoint$")
    public void setPostEndpoint(){
        addURI =  SERVER_URL + ":" + port + ENDPOINT;
        System.out.println("Add URL :"+addURI);
    }
    @When("I Set request HEADER")
    public void iSetRequestHEADER() {
        headers = new HttpHeaders();
        headers.add("Accept","application/json");
        headers.add("content-Type","application/json");
    }

    @When ("^Send a POST HTTP request$")
    public void sendPostRequest(){
        double name_id = Math.random();
        String emp_name = "Binay_" + name_id;//Just to avoid Duplicate Name entry
        String jsonBody = "{\"name\":\""+emp_name+"\",\"salary\":\"111\",\"age\":\"28\"}";
        System.out.println("\n\n" + jsonBody);
        HttpEntity<String> entity = new HttpEntity<String>(jsonBody, headers);
        //POST Method to Add New Employee
         restTemplate = new TestRestTemplate();
        response = restTemplate.postForEntity(addURI, entity, String.class);
    }
    @Then("I receive valid Response")
    public void iReceiveValidResponse() {
        String responseBodyPOST = response.getBody();
        // Write response to file
        String responseBody = response.getBody().toString();
        System.out.println("responseBody --->" + responseBody);
        // Get ID from the Response object
        String employeeId = getEmpIdFromResponse(responseBody);
        System.out.println("empId is :" + employeeId);
        // Check if the added Employee is present in the response body.
        Assert.hasText(responseBody,employeeId);
        // Check if the status code is 201
        Assert.isTrue(response.getStatusCode()== HttpStatus.OK);
        System.out.println("Employee is Added successfully employeeId:"+employeeId);
    }

    private String getEmpIdFromResponse(String responseBody) {
        return "111";
    }

    @Given("I Set PUT employee service api endpoint")
    public void iSetPUTEmployeeServiceApiEndpoint() {
        addURI =  SERVER_URL + ":" + port + ENDPOINT;
        System.out.println("Add URL :"+addURI);

    }

    @When("I Set Update request Body")
    public void iSetUpdateRequestBody() {
        String emp_name;
        String UpdatedJsonBody = "{\"name\":\""+emp_name+"\",\"salary\":\"111\",\"age\":\"28\"}";
        //POST Method to Add New Employee
        restTemplate = new TestRestTemplate();
      //  response = restTemplate.(addURI, UpdatedJsonBody);

    }

    @And("Send a PUT HTTP request")
    public void sendAPUTHTTPRequest() {

    }

    @Then("I receive valid HTTP Response code {int}")
    public void iReceiveValidHTTPResponseCode(int arg0) {

    }

    @Given("I Set GET employee service api endpoint")
    public void iSetGETEmployeeServiceApiEndpoint() {

    }

    @And("Send a GET HTTP request")
    public void sendAGETHTTPRequest() {

    }

    @Given("I Set DELETE employee service api endpoint")
    public void iSetDELETEEmployeeServiceApiEndpoint() {

    }

    @When("I Send a DELETE HTTP request")
    public void iSendADELETEHTTPRequest() {

    }

    @Then("I receive valid Response code {int}")
    public void iReceiveValidResponseCode(int arg0) {
    }
}


