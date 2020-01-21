package csit.tap.employee;

import csit.tap.employee.entities.Employee;
import csit.tap.employee.mocks.EmployeeRepositoryMock;
import csit.tap.employee.repositories.EmployeeRepository;
import csit.tap.employee.services.EmployeeService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(JUnit4.class)
public class EmployeeServiceUnitTest {

//    @Mock
    private EmployeeRepositoryMock employeeRepository = new EmployeeRepositoryMock();

//    @InjectMocks
    private EmployeeService employeeService = new EmployeeService(employeeRepository);

    @Before
    public void setup() {
        Employee newEmployee = new Employee("Alex", "ITA");

        //Mockito.doReturn(employeeRepository.findAll()).;
    }

    @Test
    public void whenSaveEmployee_givenEmployee_shouldReturnEmployee(){
        //arrange
        Employee employeeToSave = new Employee("Mary", "CST", LocalDateTime.now());

        //act
        Employee newEmployee = employeeService.createEmployee(employeeToSave);

        //assert
        assertThat(newEmployee).isNotNull().isEqualTo(employeeToSave);
    }

    @Test
    public void whenSaveEmployee_givenEmployee_shouldSaveEmployee(){
        //arrange
        Employee employeeToSave = new Employee("Mary", "CST", LocalDateTime.now());

        //act
        Employee newEmployee = employeeService.createEmployee(employeeToSave);

        //assert
//        verify(employeeRepository, times(1)).save(any());
        assertThat(employeeRepository.verify("save", 1)).isTrue();
        assertThat(newEmployee).isNotNull().isEqualTo(employeeToSave);
    }

    @Test
    public void whenFindAllEmployees_ShouldReturnAllEmployees() throws Exception
    {

        //arrange
        List<Employee> employeeList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Employee employee = new Employee("Mary " + i, "Department" + i);
            employeeList.add(employee);
        }

        employeeRepository.setEmployeeList(employeeList);

        //act
        Page<Employee> employeePage = employeeService.findAll(0, 5, "id");
        assertThat(employeePage.getContent()).usingRecursiveFieldByFieldElementComparator().isEqualTo(employeeList.subList(0, 5));

        //assert
        assertThat(employeePage.getTotalElements()).isEqualTo(5);


    }

}
