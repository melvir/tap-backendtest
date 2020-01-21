package csit.tap.employee;


import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import csit.tap.employee.entities.Employee;
import csit.tap.employee.repositories.EmployeeRepository;
import csit.tap.employee.services.EmployeeService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class TestEmployeeUpdateRestService {
	
	@InjectMocks
	private EmployeeService employeeService; 
    // MockBean is the annotation provided by Spring that wraps mockito one
    // Annotation that can be used to add mocks to a Spring ApplicationContext.
    // If any existing single bean of the same type defined in the context will be replaced by the mock, if no existing bean is defined a new one will be added.

	@Mock
	private EmployeeRepository employeeRepository;
	
	@Test
    public void whenUpdateEmployeeNotFound_thenReturnFalse() {
		
		//Arrange
		Employee employee = new Employee("testName","testDepartment");
		System.out.println(employee);
		Employee newEmployee = employeeRepository.save(employee);
		
		newEmployee.setName("testNewName");
		newEmployee.setDepartment("testNewDepartment");
		//Act
		Employee updatedEmployee = employeeService.updateEmployee(newEmployee.getId(),employee);
        System.out.println(updatedEmployee);
        //Assert
        
    }
}
