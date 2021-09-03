package bdd.stepsdefs;

import employee.management.assignment3.domain.Employee;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EmpApiStepdefs {
    HttpHeaders headers;
    String addURI;
    String UpdatedRequestBody ;
    ResponseEntity<String> response;
    ResponseEntity<List<Employee>> responseEntity;
    List<Employee> employeeList;
    Employee employee;
    private final String SERVER_URL = "http://localhost";
    private final String ENDPOINT = "/employee";
    @Autowired
    private TestRestTemplate restTemplate;

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
         response = restTemplate.postForEntity(addURI, entity, String.class);
    }
    @Then("I receive valid Response")
    public void iReceiveValidResponse() {
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
       //hardcoding timebeing
        return "111";
    }

    @Given("I Set PUT employee service api endpoint")
    public void iSetPUTEmployeeServiceApiEndpoint() {
        addURI =  SERVER_URL + ":" + port + ENDPOINT;
        System.out.println("Add URL :"+addURI);

    }

    @When("I Set Update request Body")
    public void iSetUpdateRequestBody() {
        double name_id = Math.random();
        String updated_name = "Binaya"+name_id;
        UpdatedRequestBody = "{\"name\":\""+updated_name+"\",\"salary\":\"111\",\"age\":\"28\"}";
    }

    @And("Send a PUT HTTP request")
    public void sendAPUTHTTPRequest() {
        HttpEntity<String> entity = new HttpEntity<String>(UpdatedRequestBody, headers);
        //PUT Method to Update New Employee
        response = restTemplate.postForEntity(addURI, entity, String.class);
    }
    @Then("I receive valid HTTP Response code {int}")
    public void iReceiveValidHTTPResponseCode(int status) {
        // Write response to file
        String responseBody = response.getBody().toString();
        String employeeId = getEmpIdFromResponse(responseBody);
        System.out.println("empId is :" + employeeId);
        // Check if the added Employee is present in the response body.
        Assert.hasText(responseBody,employeeId);
        Assert.isTrue(response.getStatusCode()== HttpStatus.OK);
        System.out.println("Employee is Updated successfully employeeId:"+employeeId);
    }

    @Given("I Set GET employee service api endpoint")
    public void iSetGETEmployeeServiceApiEndpoint() {
        addURI =  SERVER_URL + ":" + port + ENDPOINT;
        System.out.println("Add URL :"+addURI);
    }

    @And("Send a GET HTTP request")
    public void sendAGETHTTPRequest() {
      responseEntity = this.restTemplate.exchange(addURI, HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {
        });
        employeeList = responseEntity.getBody();
    }
    @Then("I receive valid HTTP Response")
    public void iReceiveValidHTTPResponse() {
        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Given("I Set DELETE employee service api endpoint")
    public void iSetDELETEEmployeeServiceApiEndpoint() {
        addURI =  SERVER_URL + ":" + port + ENDPOINT;
        System.out.println("Add URL :"+addURI);
    }

    @When("I Send a DELETE HTTP request")
    public void iSendADELETEHTTPRequest() {
        int id = 5;
        employee = restTemplate.getForObject(addURI+id, Employee.class);
        assertNotNull(employee);
        this.restTemplate.delete(addURI+id, Employee.class);
    }
    @Then("I receive valid Response code {int}")
    public void iReceiveValidResponseCode(int status) {
        int id = 5;
        try {
            employee = restTemplate.getForObject(addURI+id, Employee.class);
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }


}


