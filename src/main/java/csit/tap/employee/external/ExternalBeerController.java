package csit.tap.employee.external;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/beer")
public class ExternalBeerController {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    private RestTemplate restTemplate;

    @GetMapping("/beerpong")
    public String getBeerStats(){
        restTemplate = restTemplateBuilder.build();

        Person person = new Person();
        person.setName("hello");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity req = new HttpEntity(person, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:8090/stats", HttpMethod.POST, req, String.class);
        return responseEntity.getBody();
    }

    @Getter @Setter @NoArgsConstructor
    class Person{
        private String name;

    }
}
