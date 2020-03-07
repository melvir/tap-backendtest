package csit.tap.employee.external;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@DirtiesContext
@AutoConfigureStubRunner(ids = {"com.example:beer-api-producer-java:+:stubs:8090"}, stubsMode = StubRunnerProperties.StubsMode.LOCAL)
public class BeerControllerTest  {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test_beer_contract()
            throws Exception {

        System.out.println(mockMvc.getDispatcherServlet().getEnvironment());
        mockMvc.perform(MockMvcRequestBuilders.get("/beer/beerpong")
                .header("Origin", "*")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
