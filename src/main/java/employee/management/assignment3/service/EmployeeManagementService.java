package employee.management.assignment3.service;

import employee.management.assignment3.data.repo.EmployeeRepository;
import employee.management.assignment3.domain.Employee;
import employee.management.assignment3.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeManagementService {

    private final EmployeeRepository employeeRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeManagementService.class);

    @Autowired
    public EmployeeManagementService(EmployeeRepository employeeRepository){
        super();
        this.employeeRepository = employeeRepository;
    }


    public Employee addNewEmployee(Employee employee) {
        LOGGER.info("Create a new Employee "+ employee.getFirstName());
       return  employeeRepository.save(employee);
    }
    public List<Employee> getAllEmployee(){
        LOGGER.info("Get all  Employees");
        return (List<Employee>) employeeRepository.findAll();
    }

    public ResponseEntity<Employee> getEmployeeById(Integer id) throws ResourceNotFoundException {
        LOGGER.info("Get Employees for Id"+id);
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not Found for this id "+ id));
       // return  ResponseEntity.ok().body(contact);
        return ResponseEntity.status(HttpStatus.OK).body(employee);

    }

    public ResponseEntity<Employee> updateEmployee(Integer id, Employee newEmployee) throws ResourceNotFoundException{
        LOGGER.info("Update Employee for id  "+ id);
        Employee employee = employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee not found for this Id"+id));
        employee.setFirstName(newEmployee.getFirstName());
        employee.setLastName(newEmployee.getLastName());
        employee.setEmail(newEmployee.getEmail());
        final Employee updatedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    public void deleteEmployee(Integer id) throws ResourceNotFoundException{
        LOGGER.info("Delete Employee for id  "+ id);
        Employee employee = employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee not found for this Id"+id));
        employeeRepository.delete(employee);
       /* Map<String,Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return response;*/
    }
}
