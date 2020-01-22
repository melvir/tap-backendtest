package csit.tap.employee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import csit.tap.employee.entities.Employee;
import csit.tap.employee.repositories.EmployeeRepository;
import io.restassured.response.Response;
import lombok.extern.java.Log;
import org.json.JSONObject;
import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    public void retrieveAllEmployee_shouldReturnAllEmployees() throws JsonProcessingException {
        //arrange

        List<Employee> employeeList = new ArrayList<>();

//      setup();
        for (int i = 0; i < 10; i++) {
            Employee employee = new Employee("alex" + i, "department " + i);
            employee = employeeRepository.save(employee);
            employeeList.add(employee);
        }

        final String apiUrl = "http://localhost:" + port + "/api/v1/employees?pageNo=0&pageSize=10";

        //act
        List<Employee> response =
                given()
                        .header("Content-Type","application/json")
                        .get(apiUrl)
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .jsonPath()
                        .getList("content", Employee.class);

        assertEquals(response, employeeList);
    }

    @Test
    public void whenGetEmployeeByName_GivenName_ShouldReturnEmployee() {

        //arrange
        List<Employee> employeeList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Employee employee = new Employee("alex" + i, "department " + i);
            employee = employeeRepository.save(employee);
            employeeList.add(employee);
        }

        //act
        final String apiUrl = "http://localhost:" + port + "/api/v1/employees?name=alex 2";
        given()
                .get(apiUrl)
                .then().
                statusCode(200);
    }

    @Test
    public void whenUpdateEmployee_GivenId_ShouldReturnEmployee() {

        //arrange
        Employee employee = new Employee("Mel", "department");
        employeeRepository.save(employee);
        employee.setName("Mel 1");

        //act
        final String apiUrl = "http://localhost:" + port + "/api/v1/employees/update/1";

        Response response = given().when().body(employee)
                .put(apiUrl);

        //assert
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @After
    public void tearDown() {
        employeeRepository.deleteAll();
    }

}
