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
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceUnitTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

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
    public void when_find_all_it_should_return_all_employees() throws Exception
    {

        List<Employee> employeeList = employeeService.findAll(0, 5, "id");

        assertThat(employeeList).isNotNull()
                .isNotEmpty()
                .allMatch(employee -> employee.getName()
                        .toLowerCase()
                        .contains("test"));

    }

}
