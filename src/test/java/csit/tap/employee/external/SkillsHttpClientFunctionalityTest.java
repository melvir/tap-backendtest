package csit.tap.employee.external;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.anything;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(MockitoJUnitRunner.class)
public class SkillsHttpClientFunctionalityTest {

    /* The functionality of SkillsHttpClient is to retrieve json response from external api and convert to List<Skill>
    * This Test only Test the functionality of the conversion given a stub json response*/
    @Mock
    private RestTemplate testRestTemplate = new RestTemplate();

    @InjectMocks
    private SkillsHttpClient skillsHttpClient = new SkillsHttpClient(new RestTemplateBuilder());

    private ObjectMapper om = new ObjectMapper();

    @Test
    public void getAllSkillsFromExternalApi_ShouldTransformAsListOfSkills() throws IOException {
       // arrange
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Skill[] stubResponse = om.readValue(new ClassPathResource("skill-stub.json").getFile(), Skill[].class);

        Mockito.when(testRestTemplate.getForEntity("http://api.dataatwork.org/v1/skills", Skill[].class))
                .thenReturn(new ResponseEntity<>(stubResponse, HttpStatus.OK));

        // act
        List<Skill> result = skillsHttpClient.getAllSkillsFromExternalApi();

        // assert
        assertThat(result).contains(
                new Skill("02eb7ff7569c9beedda699580d679557","0-1 drop indicators","tool","","41111621","01 drop indicators"),
                new Skill("a94d4806c457c4a596791eef636cdcc7","100-foot measuring tapes","tool","","41114201","100foot measuring tapes"),
                new Skill("6cc1298c92a50bceb8d65f4732f1df0e","100 plus hatch pattern library","tool","","43232101","100 plus hatch pattern library")
        );
    }
}
