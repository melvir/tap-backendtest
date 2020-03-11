package csit.tap.employee.external.beer.client;

import csit.tap.employee.external.intoxication.IntoxicationStatusDto;
import csit.tap.employee.external.intoxication.PersonDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

/*
 * This is 3 of 3 possible implementation of the BeerClient using WebClient to make
 * a remote http call to BeerService to get the IntoxicationStatus of a Person
 * The Webclient interface is async and non-blocking as opposed to RestTemplate blocking
 * Client
 */
@Service
public class BeerWebClient implements BeerClient {

    private WebClient webClient;

    public BeerWebClient() {}

    public BeerWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public IntoxicationStatusDto GetIntoxicationStatus(PersonDto person) {
        if(webClient == null)
            webClient = WebClient.builder().build();
        return  webClient.post()
                .uri("/beer")
                .body(BodyInserters.fromValue(person)).retrieve()
                .bodyToFlux(IntoxicationStatusDto.class)
                .blockFirst();
    }
}
