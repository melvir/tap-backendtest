package csit.tap.employee.external.beer.withoutspringcloudcontracts;

import com.fasterxml.jackson.databind.ObjectMapper;
import csit.tap.employee.external.beer.client.BeerRestTemplateClient;
import csit.tap.employee.external.intoxication.IntoxicationStatusDto;
import csit.tap.employee.external.intoxication.PersonDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/*
 * We treat this as a unit test to only test the transformation of json to POJO
 * We mock the json return of restTemplate using the stub contract provided,
 *  in /resources/intoxication-status-stub.json
 */
@RunWith(MockitoJUnitRunner.class)
public class BeerRestTemplateClientTest {
    private ObjectMapper om = new ObjectMapper();

    @Mock
    private RestTemplate restTemplate= new RestTemplateBuilder().build();

    @InjectMocks
    private BeerRestTemplateClient beerRestTemplateClient= new BeerRestTemplateClient(new RestTemplateBuilder());

    @Test
    public void getIntoxicationStatusFromExternalApi_ShouldTransformToIntoxicationStatus() throws IOException {
        // arrange
        IntoxicationStatusDto stubResponse = om.readValue(new ClassPathResource("intoxication-status-stub.json").getFile(), IntoxicationStatusDto.class);

        HttpEntity<PersonDto> request = new HttpEntity<>(new PersonDto("alvin"));

        Mockito.when(restTemplate.postForEntity("/beer", request, IntoxicationStatusDto.class))
                .thenReturn(new ResponseEntity<>(stubResponse, HttpStatus.OK));

        // act
        IntoxicationStatusDto result = beerRestTemplateClient.GetIntoxicationStatus(new PersonDto("alvin"));

        // assert
        assertThat(stubResponse).isEqualToComparingFieldByField(result);
    }
}
