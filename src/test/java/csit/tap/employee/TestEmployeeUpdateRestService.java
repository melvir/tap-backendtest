package csit.tap.employee;


import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import csit.tap.employee.entities.Employee;
import csit.tap.employee.repositories.EmployeeRepository;
import csit.tap.employee.services.EmployeeService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.time.LocalDateTime;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class TestEmployeeUpdateRestService {
	
	@Mock
    private EmployeeRepository employeeRepository;
	
	@InjectMocks
    private EmployeeService employeeService;
    // MockBean is the annotation provided by Spring that wraps mockito one
    // Annotation that can be used to add mocks to a Spring ApplicationContext.
    // If any existing single bean of the same type defined in the context will be replaced by the mock, if no existing bean is defined a new one will be added.
	
	@Test
    public void whenUpdateEmployeeNotFound_thenReturnFalse() {
		
		//Arrange
		Employee employee = new Employee("testName","testDepartment", LocalDateTime.now());
		System.out.println(employee);
		Employee updatedEmployee = employeeRepository.save(employee);
		
		updatedEmployee.setName("testNewName");
		updatedEmployee.setDepartment("testNewDepartment");
		System.out.println(updatedEmployee);
		//Act
		Employee updatedEmployee2 = employeeService.updateEmployee(employee.getId(),employee);
        System.out.println(updatedEmployee);
        //Assert
        
    }
}
