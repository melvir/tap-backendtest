package csit.tap.employee.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SkillsHttpClient {
//    @Autowired
    private RestTemplate restTemplate;

    public SkillsHttpClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public List<Skill> getAllSkillsFromExternalApi() {
        Skill[] skillArray = restTemplate.getForEntity("http://api.dataatwork.org/v1/skills", Skill[].class).getBody();
        List<Skill> convertToSkillList = Arrays.stream(skillArray).collect(Collectors.toList());
        return convertToSkillList;
    }
}
