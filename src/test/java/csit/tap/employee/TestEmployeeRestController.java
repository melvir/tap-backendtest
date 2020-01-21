package csit.tap.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import csit.tap.employee.controllers.v1.EmployeeV1;
import csit.tap.employee.entities.Employee;
import csit.tap.employee.services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeV1.class)
public class TestEmployeeRestController {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    public void it_should_return_all_employees() throws Exception
    {

        mvc.perform( MockMvcRequestBuilders
                .get("/api/v1/employee/all")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Origin","*"))
                .andExpect(status().isOk());
                //.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    public void it_should_return_created_employee() throws Exception
    {

        mvc.perform( MockMvcRequestBuilders
                .post("/api/v1/employee/new")
                .content(asJsonString(new Employee("Mary", "CST")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Origin","*"))
                .andExpect(status().isCreated());
                //.andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Mary"));
    }
    
    @Test
    public void getEmployee() throws Exception{     
        mvc.perform( MockMvcRequestBuilders
                .get("/api/v1/employee/retrieve_employee/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Origin","*"))
                .andExpect(status().isNoContent());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
