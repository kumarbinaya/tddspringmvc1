package employee.management.assignment3.controller;

import employee.management.assignment3.domain.Employee;
import employee.management.assignment3.exception.ResourceNotFoundException;
import employee.management.assignment3.service.EmployeeManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeManagementController {

    private final EmployeeManagementService employeeManagementService;

    @Autowired
    public EmployeeManagementController(EmployeeManagementService employeeManagementService){
        this.employeeManagementService = employeeManagementService;
    }

    @PostMapping
    public Employee processAddEmployeeSubmit(@RequestBody Employee employee){
    return employeeManagementService.addNewEmployee(employee);
    }

    @GetMapping
    public List<Employee> processGetAllEmployee(){
        return employeeManagementService.getAllEmployee();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> processGetEmployeeById(@PathVariable(value = "id") Integer Id) throws ResourceNotFoundException {
        return employeeManagementService.getEmployeeById(Id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Integer id, @RequestBody Employee employee) throws ResourceNotFoundException{
    return employeeManagementService.updateEmployee(id, employee);
    }
    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException{
        employeeManagementService.deleteEmployee(id);

    }
}
