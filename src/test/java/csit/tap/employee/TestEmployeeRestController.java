package csit.tap.employee;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import csit.tap.employee.entities.Employee;
import csit.tap.employee.repositories.EmployeeRepository;
import io.restassured.response.ValidatableResponse;
import lombok.extern.java.Log;

@Log
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestEmployeeRestController {

    @LocalServerPort
    private int port;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    ObjectMapper objectMapper;

//    @Test
//    public void retrieveAllEmployee_shouldReturnAllEmployees() throws JsonProcessingException {
//        //arrange
//
//        List<Employee> employeeList = new ArrayList<>();
//
////      setup();
//        for (int i = 0; i < 10; i++) {
//            Employee employee = new Employee("alex" + i, "department " + i);
//            employee = employeeRepository.save(employee);
//            employeeList.add(employee);
//        }
//
//        final String apiUrl = "http://localhost:" + port + "/api/v1/employees?pageNo=0&pageSize=10";
//
//        //act
//        List<Employee> response =
//                given()
//                        .get(apiUrl)
//                        .then()
//                        .statusCode(200)
//                        .extract()
//                        .body()
//                        .jsonPath()
//                        .getList("content", Employee.class);
//
//        assertEquals(response, employeeList);
//    }
//
//    @Test
//    public void whenGetEmployeeByName_GivenName_ShouldReturnEmployee() {
//
//        //arrange
//
//        List<Employee> employeeList = new ArrayList<>();
//
//        for (int i = 0; i < 10; i++) {
//            Employee employee = new Employee("alex" + i, "department " + i);
//            employee = employeeRepository.save(employee);
//            employeeList.add(employee);
//        }
//
//        //act
//        final String apiUrl = "http://localhost:" + port + "/api/v1/employees?name=alex 2";
//
//
//        given()
//                .get(apiUrl).then().statusCode(200);
//    }
    
    @Test
    public void whenGetEmployeeById_GivenId_ShouldReturnEmployeeId() {

        //arrange
        List<Employee> employeeList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Employee employee = new Employee();
            employee.setId((long)i);
            employee.setName("mango" + i);
            employee.setDepartment("basket" + i);
            employee = employeeRepository.save(employee);
            employeeList.add(employee);
        }

        //act
        final String apiUrl = "http://localhost:" + port + "/api/v1/employees?name=mango3";

        //assert
        given().get(apiUrl).then().assertThat().statusCode(200);
        
//        List<Employee> response =
//	        given()
//	                .get(apiUrl)
//	                .then()
//	                .statusCode(200)
//	                .extract()
//	                .body()
//	                .jsonPath()
//	                .getList("content", Employee.class);
        
//        assertEquals(response, employeeList.toString());
    }

    @After
    public void tearDown() {
        employeeRepository.deleteAll();
    }

}
