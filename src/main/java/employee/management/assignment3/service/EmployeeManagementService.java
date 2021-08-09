package employee.management.assignment3.service;

import employee.management.assignment3.data.repo.EmployeeRepository;
import employee.management.assignment3.domain.Employee;
import employee.management.assignment3.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeManagementService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeManagementService(EmployeeRepository employeeRepository){
        super();
        this.employeeRepository = employeeRepository;
    }


    public Employee addNewEmployee(Employee contact) {
       return  employeeRepository.save(contact);
    }
    public List<Employee> getAllEmployee(){

        return (List<Employee>) employeeRepository.findAll();
    }

    public ResponseEntity<Employee> getEmployeeById(Integer id) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not Founf for this id "+ id));
       // return  ResponseEntity.ok().body(contact);
        return ResponseEntity.status(HttpStatus.OK).body(employee);

    }

    public ResponseEntity<Employee> updateEmployee(Integer id, Employee newEmployee) throws ResourceNotFoundException{
        Employee employee = employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Customer Contact not found for this Id"+id));
        employee.setFirstName(newEmployee.getFirstName());
        employee.setLastName(newEmployee.getLastName());
        employee.setEmail(newEmployee.getEmail());
        final Employee updatedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    public void deleteEmployee(Integer id) throws ResourceNotFoundException{
        Employee employee = employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Customer Contact not found for this Id"+id));
        employeeRepository.delete(employee);
       /* Map<String,Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return response;*/
    }
}
