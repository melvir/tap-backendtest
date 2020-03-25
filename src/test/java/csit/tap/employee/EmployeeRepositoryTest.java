package csit.tap.employee;

import csit.tap.ManagerRepository;
import csit.tap.employee.entities.Employee;
import csit.tap.employee.repositories.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    public void getEmployee_givenName_shouldReturnEmployee() {

        //arrange
        String name = "John";
        String nameToSearch = name + "3";

        for (int i = 0; i < 10; i++) {
            Employee employee = new Employee(name + i, "Department " + i);
            employeeRepository.save(employee);
        }

        Employee expectedEmployee = new Employee(nameToSearch, "Department 3");
        expectedEmployee.setId(4L);

        //act
        Pageable paging = PageRequest.of(0, 5, Sort.by("id"));
        Page<Employee> resultEmployee = employeeRepository.findByName(nameToSearch, paging);

        //assert
        assertThat(resultEmployee.getContent()).usingElementComparatorIgnoringFields("createdDateTime", "modifiedDateTime").contains(expectedEmployee);
    }

    @Test
    public void getEmployee_givenNameAndDepartment_shouldReturnEmployee() {

        //arrange
        String name = "John";
        String nameToSearch = name + "3";
        String department = "Department ";
        String departmentToSearch = department + "3";

            //simulate data accuracy
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                Employee employee = new Employee(name + i, "Department " + j);
                employeeRepository.save(employee);
            }
        }

        Employee expectedEmployee = new Employee(nameToSearch, departmentToSearch);

        //act
        Pageable paging = PageRequest.of(0, 5, Sort.by("id"));
        Page<Employee> resultEmployee = employeeRepository.findByNameAndDepartment(nameToSearch, departmentToSearch, paging);

        //assert
        assertThat(resultEmployee.getContent()).usingElementComparatorIgnoringFields("id", "createdDateTime", "modifiedDateTime").contains(expectedEmployee);
    }


    @Test
    public void getEmployee_givenId_shouldReturnEmployee() {

        //arrange
        for (int i = 0; i < 10; i++) {
            Employee employee = new Employee("Mel" + i, "Department " + i);
            employeeRepository.save(employee);
        }

        //act
        Optional<Employee> resultEmployee = employeeRepository.findById(1L);

        //assert
        assertThat(resultEmployee.get().getName()).isEqualTo("Mel0");
    }

    @Test
    public void getEmployee_givenInvalidId_shouldNotReturnEmployee() {

        //arrange
        for (int i = 0; i < 10; i++) {
            Employee employee = new Employee("Mel" + i, "Department " + i);
            employeeRepository.save(employee);
        }

        //act
        Optional<Employee> resultEmployee = employeeRepository.findById(11L);

        //assert
        assertThat(resultEmployee).isEmpty();
    }

    @Test
    public void getEmployee_givenDepartment_shouldReturnEmployee() {

        //arrange
        Employee employee = new Employee("John", "ITA");
        employeeRepository.save(employee);

        //act
        Pageable paging = PageRequest.of(0, 5, Sort.by("id"));
        Page<Employee> resultEmployee = employeeRepository.findByDepartment("ITA", paging);

        //assert
        assertThat(resultEmployee.getContent()).contains(employee);
    }
}
