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
        Employee employee = new Employee();
        employee.setId(1);
        employee.setFirstName("binaya");
        employee.setLastName("kumar");
        employee.setEmail("bk@gmail.com");
        Mockito.when(employeeManagementService.addNewEmployee(Mockito.any(Employee.class))).thenReturn(employee);
        /*MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/contact/").contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(contact));*/
        mockMvc.perform(post("/employee").contentType(MediaType.APPLICATION_JSON_VALUE).content(this.mapper.writeValueAsString(employee))).andExpect(status().isOk()).andExpect(jsonPath("$.firstName").value(employee.getFirstName()));

    }

    @Test
    void get_AllEmployee_ReturnsAllEmployee_Ok() throws Exception {

        List<Employee> employeeList = new ArrayList<>();
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

        employeeList.add(employee1);
        employeeList.add(employee2);
        // Mocking out the Employee service
        Mockito.when(employeeManagementService.getAllEmployee()).thenReturn(employeeList);

       /* mockMvc.perform(MockMvcRequestBuilders.get("/demo/vehicles").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].vin", is("AD23E5R98EFT3SL00"))).andExpect(jsonPath("$[0].make", is("Ford")))
                .andExpect(jsonPath("$[1].vin", is("O90DEPADE564W4W83")))
                .andExpect(jsonPath("$[1].make", is("Volkswagen")));*/
        mockMvc.perform(get("/employee").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].firstName").value(employee1.getFirstName())).
                andExpect(jsonPath("$[0].lastName").value(employee1.getLastName())).andExpect(jsonPath("$[0].email").value(employee1.getEmail())).andExpect(jsonPath("$[1].firstName").value(employee2.getFirstName())).
                andExpect(jsonPath("$[1].lastName").value(employee2.getLastName())).andExpect(jsonPath("$[1].email").value(employee2.getEmail()));



    }

    @Test
    void get_EmployeeById_Returns_Employee_OK() throws Exception {
        Employee employee = new Employee();
        employee.setId(1);
        employee.setFirstName("binaya");
        employee.setLastName("kumar");
        employee.setEmail("bk@email.com");

        Mockito.when(employeeManagementService.getEmployeeById(1)).thenReturn(ResponseEntity.status(HttpStatus.OK).body(employee));
        mockMvc.perform(get("/employee/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.firstName").value(employee.getFirstName())).
                andExpect(jsonPath("$.lastName").value(employee.getLastName())).andExpect(jsonPath("$.email").value(employee.getEmail()));
    }

    @Test
    void update_Employee_Returns_Ok() throws Exception {

        Employee employee = new Employee();
        employee.setId(1);
        employee.setFirstName("binaya");
        employee.setLastName("kumar");
        employee.setEmail("bk@email.com");
        Employee updateEmployee = new Employee();
        updateEmployee.setFirstName("binaya1");
        updateEmployee.setLastName("kumar1");
        updateEmployee.setEmail("bk1@email.com");
        Mockito.when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        Mockito.when(employeeManagementService.updateEmployee(1, employee)).thenReturn(ResponseEntity.status(HttpStatus.OK).body(employee));

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
}