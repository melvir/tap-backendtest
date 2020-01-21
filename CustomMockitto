package csit.tap.employee;

import csit.tap.employee.entities.Employee;
import csit.tap.employee.repositories.EmployeeRepository;
import csit.tap.employee.services.EmployeeService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class EmployeeServiceUnitTest {

    @Mock
    private EmployeeRepository employeeRepository = new EmployeeRepository() {
        @Override
        public Iterable<Employee> findAll(Sort sort) {
            return null;
        }

        @Override
        public Page<Employee> findAll(Pageable pageable) {
            return null;
        }

        @Override
        public <S extends Employee> S save(S s) {
            return null;
        }

        @Override
        public <S extends Employee> Iterable<S> saveAll(Iterable<S> iterable) {
            return null;
        }

        @Override
        public Optional<Employee> findById(Long aLong) {
            return Optional.empty();
        }

        @Override
        public boolean existsById(Long aLong) {
            return false;
        }

        @Override
        public Iterable<Employee> findAll() {
            return null;
        }

        @Override
        public Iterable<Employee> findAllById(Iterable<Long> iterable) {
            return null;
        }

        @Override
        public long count() {
            return 0;
        }

        @Override
        public void deleteById(Long aLong) {

        }

        @Override
        public void delete(Employee employee) {

        }

        @Override
        public void deleteAll(Iterable<? extends Employee> iterable) {

        }

        @Override
        public void deleteAll() {

        }
    };

    //@InjectMocks
    private EmployeeService employeeService = new EmployeeService(employeeRepository);

    @Test
    public void when_save_employee_it_should_return_employee() throws Exception
    {
        //arrange
        Employee employeeToSave = new Employee("Mary", "CST", LocalDateTime.now());

        //act
        Employee newEmployee = employeeService.createEmployee(employeeToSave);
        System.out.println("newEmployee from test is " + newEmployee);

        //assert
        assertThat(newEmployee).isNotNull().isEqualTo(employeeToSave);
    }

    @Test
    public void when_save_user_it_should_return_all_employees() throws Exception
    {
/*
        List<Employee> employeeList = employeeService.findAll(0, 5, "id");
        System.out.println(employeeList);

        assertThat(employeeList).isNotNull()
                .isNotEmpty()
                .allMatch(employee -> employee.getName()
                        .toLowerCase()
                        .contains("test"));

 */
    }

}
