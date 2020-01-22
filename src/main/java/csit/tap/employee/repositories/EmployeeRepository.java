package csit.tap.employee.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import csit.tap.employee.entities.Employee;

@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {
    Employee findByName(String name);

    Employee findByDepartment(String department);

    //Optional<Employee> findById (Long id);
    //Optional<Employee> findByName (String name);
    //Page<Employee> findByNameIgnoreCaseContaining (String name, Pageable pageable);
}