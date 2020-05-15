package csit.tap.employee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import csit.tap.employee.entities.Employee;
import csit.tap.employee.repositories.EmployeeRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lombok.extern.java.Log;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Log
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @After
    public void cleanIndex() {
        employeeRepository.deleteAll(); //Assuming you have a collection
    }

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
        String name = "alex";
        String nameToSearch = name + "2";
        for (int i = 0; i < 10; i++) {
            Employee employee = new Employee(name + i, "department " + i);
            employeeRepository.save(employee);
        }
        Employee e = employeeRepository.findByName(nameToSearch);

        //act
        final String apiUrl = "http://localhost:" + port + "/api/v1/employees";
        JsonPath jsonPath = RestAssured.given()
                .queryParam("name", nameToSearch)
                .when()
                .get(apiUrl)
                .then()
                .statusCode(200)
                .extract().body().jsonPath();

        List<Employee> employeeList = jsonPath.getList("content", Employee.class);

        //assert
        assertThat(employeeList).containsOnly(e);
    }

    /**
     * To test for updating a specific Object only (Employee.class)
     * @throws JsonProcessingException when cannot parse the json as a request parameter
     */
    //todo: for view demo
    @Test
    public void whenUpdateEmployeeName_GivenId_ShouldReturnEmployee() throws JsonProcessingException {
        //arrange
        String name = "John";
        String newName = "Mary";
        Employee employee = new Employee(name, "department");
        Employee updatedEmployee = employeeRepository.save(employee);
        updatedEmployee.setName(newName);

        String json = objectMapper.writeValueAsString(updatedEmployee);

        //act
        final String apiUrl = "http://localhost:" + port + "/api/v1/employees/{id}";
        Employee e = given()
                .contentType(ContentType.JSON)
                .body(json)
                .pathParam("id", updatedEmployee.getId())
                .when()
                .put(apiUrl)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(Employee.class);

        //assert

        assertThat(e).isEqualToIgnoringGivenFields(updatedEmployee, "createdDateTime", "modifiedDateTime"); //check return contains the object
    }


    /**
     * To test for data in the pagination response for a list of employees
     */
    //todo: for view demo
    @Test
    public void whenGetEmployeeByDepartment_GivenDepartment_ShouldReturnEmployee() {
        //arrange
        String department = "ITA";
        Employee employee = new Employee("John", department);
        employeeRepository.save(employee);

        //act
        final String apiUrl = "http://localhost:" + port + "/api/v1/employees";

        //Response response = given().get(apiUrl);
        //ResponseBody body = response.getBody();

        JsonPath jsonPath = RestAssured.given()
                .param("department", department)
                .when()
                .get(apiUrl)
                .then()
                .statusCode(200)
                .extract().body().jsonPath();

        List<Employee> employeeList = jsonPath.getList("content", Employee.class);

        //assert
        assertThat(employeeList).contains(employee); //check return contains the object
    }


}
