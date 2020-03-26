package csit.tap.employee.external.beer.withoutspringcloudcontracts;

import com.fasterxml.jackson.databind.ObjectMapper;
import csit.tap.employee.external.beer.client.BeerWebClient;
import csit.tap.employee.external.intoxication.IntoxicationStatusDto;
import csit.tap.employee.external.intoxication.PersonDto;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/*
 * We treat this as a unit test to only test the transformation of json to POJO by WebClient
 * We mock the json return of the remote server using a MockWebServer
 * and the stub contract provided in /resources/intoxication-status-stub.json
 */
@RunWith(MockitoJUnitRunner.class)
public class BeerWebClientTest {
    private ObjectMapper om = new ObjectMapper();
    private final MockWebServer mockWebServer = new MockWebServer();

    @InjectMocks
    private BeerWebClient beerWebClient = new BeerWebClient(WebClient.create(mockWebServer.url("/").toString()));

    @After
    public void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void getIntoxicationStatusFromExternalApi_ShouldTransformToIntoxicationStatus() throws IOException {
        // arrange
        IntoxicationStatusDto stubResponse = om.readValue(new ClassPathResource("intoxication-status-stub.json").getFile(), IntoxicationStatusDto.class);
        PersonDto personDto = new PersonDto("marcin");

        String stubContent = FileUtils.readFileToString(
                new ClassPathResource("intoxication-status-stub.json").getFile()
                , String.valueOf(StandardCharsets.UTF_8));

        // mocking the server return
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(stubContent)
        );

        // act
        IntoxicationStatusDto statusDto = beerWebClient.GetIntoxicationStatus(personDto);

        // assert
        assertThat(statusDto).isEqualToComparingFieldByField(stubResponse);
    }
}
