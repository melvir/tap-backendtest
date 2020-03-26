package csit.tap.employee.external.beer.client;

import csit.tap.employee.external.intoxication.IntoxicationStatusDto;
import csit.tap.employee.external.intoxication.PersonDto;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/*
 * This is 2 of 3 possible implementation of the BeerClient using traditional RestTemplate to make
 * a remote http call to another MS to get the IntoxicationStatus of a Person
*/
@Service
public class BeerRestTemplateClient implements BeerClient {
    private RestTemplate restTemplate;

    public BeerRestTemplateClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public IntoxicationStatusDto GetIntoxicationStatus(PersonDto person) {
        HttpEntity<PersonDto> request = new HttpEntity<>(person);
        ResponseEntity<IntoxicationStatusDto>  responseEntity = restTemplate.postForEntity("/beer", request, IntoxicationStatusDto.class);
        return responseEntity.getBody();
    }
}