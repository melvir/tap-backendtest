package csit.tap.employee;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import csit.tap.employee.entities.Employee;
import csit.tap.employee.repositories.EmployeeRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;

//    @Test
//    public void getEmployee_givenName_shouldReturnEmployee() {
//
//        //arrange
//        for (int i = 0; i < 10; i++) {
//            Employee employee = new Employee("Mel" + i, "Department " + i);
//            employeeRepository.save(employee);
//        }
//
//        //act
//        Employee resultEmployee = employeeRepository.findByName("Mel3");
//
//        //assert
//        assertThat(resultEmployee.getName()).isEqualTo("Mel3");
//    }
    
    @Test
    public void getEmployee_givenId_shouldReturnEmployeeId() {

        //arrange
    	Employee employee = new Employee("orange1", "basket1");
        employeeRepository.save(employee);

        //act
        Employee resultEmployee = employeeRepository.findById(1);

        //assert
        assertThat(resultEmployee.getId()).isEqualTo(1);
    }

}
