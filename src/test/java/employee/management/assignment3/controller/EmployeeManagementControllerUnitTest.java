package employee.management.assignment3.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import employee.management.assignment3.data.repo.EmployeeRepository;
import employee.management.assignment3.domain.Employee;
import employee.management.assignment3.service.EmployeeManagementService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeManagementController.class)
class EmployeeManagementControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private EmployeeManagementService employeeManagementService;
    @MockBean
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeManagementController employeeManagementController;

    @Test
    void create_Employee_Return_Ok() throws Exception {
        Mockito.when(employeeManagementService.addNewEmployee(Mockito.any(Employee.class))).thenReturn(getEmployees().get(0));
        mockMvc.perform(post("/employee").contentType(MediaType.APPLICATION_JSON_VALUE).content(this.mapper.writeValueAsString(getEmployees().get(0)))).andExpect(status().isOk()).andExpect(jsonPath("$.firstName").value(getEmployees().get(0).getFirstName()));

    }

    @Test
    void get_AllEmployee_ReturnsAllEmployee_Ok() throws Exception {

        // Mocking out the Employee service
        Mockito.when(employeeManagementService.getAllEmployee()).thenReturn(getEmployees());

        mockMvc.perform(get("/employee").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].firstName").value(getEmployees().get(0).getFirstName())).
                andExpect(jsonPath("$[0].lastName").value(getEmployees().get(0).getLastName())).andExpect(jsonPath("$[0].email").value(getEmployees().get(0).getEmail())).andExpect(jsonPath("$[1].firstName").value(getEmployees().get(0).getFirstName())).
                andExpect(jsonPath("$[1].lastName").value(getEmployees().get(1).getLastName())).andExpect(jsonPath("$[1].email").value(getEmployees().get(1).getEmail()));

    }

    @Test
    void get_EmployeeById_Returns_Employee_OK() throws Exception {

        Mockito.when(employeeManagementService.getEmployeeById(1)).thenReturn(ResponseEntity.status(HttpStatus.OK).body(getEmployees().get(0)));
        mockMvc.perform(get("/employee/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.firstName").value(getEmployees().get(0).getFirstName())).
                andExpect(jsonPath("$.lastName").value(getEmployees().get(0).getLastName())).andExpect(jsonPath("$.email").value(getEmployees().get(0).getEmail()));
    }

    @Test
    void update_Employee_Returns_Ok() throws Exception {

        Mockito.when(employeeRepository.findById(1)).thenReturn(Optional.of(getEmployees().get(0)));
        Mockito.when(employeeManagementService.updateEmployee(1, getEmployees().get(0))).thenReturn(ResponseEntity.status(HttpStatus.OK).body(getEmployees().get(0)));

    }

    @Test
    void delete_Employee_Returns_NoContent_Ok() throws Exception {

       int id = 1;
        EmployeeManagementService spyService  = Mockito.spy(employeeManagementService);
        Mockito.doNothing().when(spyService).deleteEmployee(id);

        mockMvc.perform(delete("/contact/1")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        verify(employeeManagementService, times(1)).deleteEmployee(id);

    }

    public List<Employee> getEmployees() {
        List<Employee> employees = new ArrayList<>();

        Employee employee1 = new Employee();
        employee1.setId(1);
        employee1.setFirstName("binaya");
        employee1.setLastName("kumar");
        employee1.setEmail("bk@email.com");
        Employee employee2 = new Employee();
        employee1.setId(2);
        employee1.setFirstName("binaya2");
        employee1.setLastName("kumar2");
        employee1.setEmail("bk2@email.com");
        employees.add(employee1);
        employees.add(employee2);
        return  employees;
    }
}