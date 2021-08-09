package employee.management.assignment3.service;


import employee.management.assignment3.domain.Employee;
import employee.management.assignment3.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class EmployeeServiceIntegrationTest {

    @Autowired
    EmployeeManagementService employeeManagementService;


    @Test
    void create_Employee_Return_Ok() {
      List<Employee> employeeList =   employeeManagementService.getAllEmployee();
        Employee employee = new Employee();
        employee.setFirstName("binay");
        employee.setLastName("kumar");
        employee.setEmail("bk@gmail.com");
        employeeManagementService.addNewEmployee(employee);
        List<Employee> employeeList1 = employeeManagementService.getAllEmployee();
        assertEquals(employeeList1.size(), employeeList.size()+1);

    }

    @Test
    void get_AllEmployee_ReturnsAllEmployee_Ok() {
        List<Employee> employeeList = employeeManagementService.getAllEmployee();
        assertNotNull(employeeList);
    }

    @Test
    void get_EmployeeById_Returns_Employee_OK() throws ResourceNotFoundException {
        int id = 1;
        ResponseEntity<Employee> employee = employeeManagementService.getEmployeeById(id);
        assertEquals("binay",employee.getBody().getFirstName());
    }

    @Test
    void update_Employee_Returns_Ok() throws ResourceNotFoundException {
        int id =1;
        ResponseEntity<Employee> employee = employeeManagementService.getEmployeeById(id);
        employee.getBody().setFirstName("testUpdate");
        ResponseEntity<Employee> contactResponseEntity = employeeManagementService.updateEmployee(id,employee.getBody());
        assertEquals("testUpdate",contactResponseEntity.getBody().getFirstName());
    }

    @Test
    void delete_Employee_Returns_NoContent_Ok() throws ResourceNotFoundException {
        List<Employee> employeeList = employeeManagementService.getAllEmployee();
        employeeManagementService.deleteEmployee(employeeList.get(0).getId());
        assertEquals(employeeManagementService.getAllEmployee().size(), employeeList.size()-1);
    }
}
