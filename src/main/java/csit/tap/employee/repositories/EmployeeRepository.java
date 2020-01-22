package csit.tap.employee.repositories;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import csit.tap.employee.entities.Employee;

@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {
    Employee findByName(String name);
    //Optional<Employee> findById (Long id);
    //Optional<Employee> findByName (String name);
    //Page<Employee> findByNameIgnoreCaseContaining (String name, Pageable pageable);
}