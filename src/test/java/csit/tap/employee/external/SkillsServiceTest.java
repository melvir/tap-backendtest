package csit.tap.employee.external;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class SkillsServiceTest {
    /*
    Since SkillsServiceTest is a unit test,
    the call to its dependencies should be mocked
    (without invocation of the http call in SkillsHttpClient) */

    @Mock
    private SkillsHttpClient skillsHttpClient = new SkillsHttpClient(new RestTemplateBuilder());

    @Spy
    private SkillRepository skillRepository = new SkillRepository();

    @InjectMocks
    private SkillsService skillsService = new SkillsService(skillsHttpClient, skillRepository);

    @Test
    public void getSkillsFromExternalSource_shouldSaveInSkillsRepository(){
        // arrange
        List<Skill> skillsFromApi = Arrays.asList(
                new Skill("1","Spring boot","Tech Stack","Backend","121","S_B"),
                new Skill("1","Vue JS","Tech Stack","Frontend","122","Vue"),
                new Skill("1","React","Tech Stack","Frontend","123","React")
        );

        // mocking return from the httpClient as a list of Skill POJO
        Mockito.when(skillsHttpClient.getAllSkillsFromExternalApi()).thenReturn(skillsFromApi);

        // act
        boolean result = skillsService.getSkillsFromExternalSource();

        // assert
        assertThat(result).isTrue();
        Mockito.verify(skillRepository, Mockito.times(1)).saveAll(skillsFromApi);
    }
}
