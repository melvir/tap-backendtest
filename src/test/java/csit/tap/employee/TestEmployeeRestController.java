package csit.tap.employee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import csit.tap.employee.entities.Employee;
import csit.tap.employee.repositories.EmployeeRepository;
import lombok.extern.java.Log;
import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Log
@RunWith(JUnit4.class)
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

    @After
    public void clearDatabase(){

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
                        .get(apiUrl)
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .jsonPath()
                        .getList("content", Employee.class);

        assertEquals(response, employeeList);
    }

    @After
    public void tearDown() {
        employeeRepository.deleteAll();
    }

//    private HttpEntity buildRequest() {
//        HttpHeaders headers = new HttpHeaders();
//        return new HttpEntity(headers);
//    }


//    @Test
//    public void it_should_return_all_employees() throws Exception
//    {
//
//        mvc.perform( MockMvcRequestBuilders
//                .get("/api/v1/employee/all")
//                .contentType(MediaType.APPLICATION_JSON)
//                .header("Origin","*"))
//                .andExpect(status().isOk());
//                //.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
//    }
//
//    @Test
//    public void it_should_return_created_employee() throws Exception
//    {
//
//        mvc.perform( MockMvcRequestBuilders
//                .post("/api/v1/employee/new")
//                .content(asJsonString(new Employee("Mary", "CST")))
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .header("Origin","*"))
//                .andExpect(status().isCreated());
//                //.andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Mary"));
//    }
//
//    @Test
//    public void getEmployee() throws Exception{
//        mvc.perform( MockMvcRequestBuilders
//                .get("/api/v1/employee/retrieve_employee/1")
//                .contentType(MediaType.APPLICATION_JSON)
//                .header("Origin","*"))
//                .andExpect(status().isNoContent());
//    }
//
//    public static String asJsonString(final Object obj) {
//        try {
//            return new ObjectMapper().writeValueAsString(obj);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

}
