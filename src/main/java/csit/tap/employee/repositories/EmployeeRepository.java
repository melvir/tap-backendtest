package csit.tap.employee.repositories;

import csit.tap.employee.entities.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {
    Employee findByName(String name);
    Page<Employee> findByName(String name, Pageable pageable);

    Page<Employee> findByDepartment(String department, Pageable pageable);

    Page<Employee> findByNameAndDepartment(String name, String department, Pageable paging);

    //Optional<Employee> findById (Long id);
    //Optional<Employee> findByName (String name);
    //Page<Employee> findByNameIgnoreCaseContaining (String name, Pageable pageable);
}