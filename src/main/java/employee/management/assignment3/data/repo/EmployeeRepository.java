package employee.management.assignment3.data.repo;


import employee.management.assignment3.domain.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee,Integer> {
}
