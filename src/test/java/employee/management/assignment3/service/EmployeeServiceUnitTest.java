package employee.management.assignment3.service;

import employee.management.assignment3.data.repo.EmployeeRepository;

import employee.management.assignment3.domain.Employee;
import employee.management.assignment3.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class EmployeeServiceUnitTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeManagementService employeeManagementService;

    @Mock
    private Employee employee;


    @Test
    void create_Employee_Return_Ok() {
        Employee employee = new Employee();
        employee.setFirstName("binay");
        employee.setLastName("kumar");
        employee.setEmail("bk@gmail.com");
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        //save the Employee
        Employee newEmployee = employeeManagementService.addNewEmployee(new Employee());
        //verify the save
        assertNotNull(newEmployee);
        assertEquals("binay", newEmployee.getFirstName());
    }

    @Test
    void get_AllEmployee_ReturnsAllEmployee_Ok() {
        Employee employee = new Employee();
        employee.setFirstName("Test X");
        employee.setLastName("Test Y");
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee));
        assertEquals("Test X" , employeeManagementService.getAllEmployee().get(0).getFirstName());
    }

    @Test
    void get_EmployeeById_Returns_Employee_OK() throws ResourceNotFoundException {
        int id = 1;
        Employee employee = new Employee();
        employee.setFirstName("Test A");
        employee.setLastName("Test B");
        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));
        //invoke and verify getEmployeeById
        assertEquals(employeeManagementService.getEmployeeById(id).getBody().getFirstName(), "Test A");
    }

    @Test
    void update_Employee_Returns_Ok() throws ResourceNotFoundException {
        int id =1;
        ResponseEntity<Employee> employee = employeeManagementService.getEmployeeById(id);
        employee.getBody().setFirstName("testUpdate1");
        ResponseEntity<Employee> contactResponseEntity = employeeManagementService.updateEmployee(id,employee.getBody());

        //verify employeeRepository.save invoked once
        verify(employeeRepository).save(any(Employee.class));
        verify(contactResponseEntity.getBody()).setFirstName("testUpdate1");
    }

    @Test
    void delete_Employee_Returns_NoContent_Ok() throws ResourceNotFoundException {
        employeeManagementService.deleteEmployee(1);
        //verify employeeRepository.delete invoked
        verify(employeeRepository).delete(any(Employee.class));
    }
}
