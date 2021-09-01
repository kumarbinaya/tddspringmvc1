package employee.management.assignment3.controller;

import employee.management.assignment3.data.repo.EmployeeRepository;
import employee.management.assignment3.domain.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeeManagementControllerIntegrationTest {


    final private static String baseUrl ="http://localhost:";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void create_Employee_Return_Ok() {
        Employee employee = new Employee();
        employee.setFirstName("binay");
        employee.setLastName("kumar");
        employee.setEmail("bk@gmail.com");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Employee> request = new HttpEntity<>(employee, headers);
        ResponseEntity<Employee> postResponse =  restTemplate.postForEntity(baseUrl+port+"/employee",request, Employee.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
        assertEquals(HttpStatus.OK,postResponse.getStatusCode());
    }

    @Test
    void get_AllEmployee_ReturnsAllEmployee_Ok() {

        List<String> employeeFirstNamesList = Stream.of("jenny","binay").collect(Collectors.toList());
        ResponseEntity<List<Employee>> responseEntity = this.restTemplate.exchange(baseUrl + port+"/employee", HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {
        });
        List<Employee> employeeList = responseEntity.getBody();
        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(employeeList.stream().anyMatch((employee) ->{
            return employeeFirstNamesList.contains(employee.getFirstName());
        }));

    }

    @Test
    void get_EmployeeById_Returns_Employee_OK() {
        int id =1;
        String expectedEmployeeFirstName="jenny";
        ResponseEntity<Employee> responseEntity = this.restTemplate.getForEntity(baseUrl+port+"/employee/"+id, Employee.class);
        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(expectedEmployeeFirstName , responseEntity.getBody().getFirstName());

    }

    @Test
    void update_Employee_Returns_Ok() {
        int id =1;
        Employee employee = this.restTemplate.getForObject(baseUrl+port+"/employee/"+id, Employee.class);
        employee.setFirstName("jenny1");
        employee.setLastName("johnson1");
        this.restTemplate.put(baseUrl+port+"/employee/"+id, employee);
        ResponseEntity<Employee> updatedEmployee = this.restTemplate.getForEntity(baseUrl+port+"/employee/"+id, Employee.class);
        assertNotNull(updatedEmployee.getBody());
        assertEquals(HttpStatus.OK,updatedEmployee.getStatusCode());
    }

    @Test
    void delete_Employee_Returns_NoContent_Ok() {
        int id = 5;
        Employee employee = restTemplate.getForObject(baseUrl+port+"/employee/"+id, Employee.class);
        assertNotNull(employee);
        this.restTemplate.delete(baseUrl+port+"/employee/"+id, Employee.class);
        try {
            employee = restTemplate.getForObject(baseUrl+port+"/employee/"+id, Employee.class);
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }

    }
}