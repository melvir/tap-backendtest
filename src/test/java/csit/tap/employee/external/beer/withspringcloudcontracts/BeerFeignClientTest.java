package csit.tap.employee.external.beer.withspringcloudcontracts;

import csit.tap.employee.external.beer.client.BeerFeignClient;
import csit.tap.employee.external.intoxication.IntoxicationStatusDto;
import csit.tap.employee.external.intoxication.PersonDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@RunWith(SpringRunner.class)
@AutoConfigureStubRunner(ids = {"com.example:beer-api-producer-java:+:stubs:8090"}, stubsMode = StubRunnerProperties.StubsMode.LOCAL)
public class BeerFeignClientTest {
    @Autowired
    private BeerFeignClient beerFeignClient;

    @Test
    public void GetIntoxicationStatus_givenPersonNameMarcin_shouldComplyToContract(){
        // arrange
        PersonDto personDto = new PersonDto("marcin");
        IntoxicationStatusDto expected = new IntoxicationStatusDto( "SOBER", "TIPSY");

        // act
        IntoxicationStatusDto result = beerFeignClient.GetIntoxicationStatus(personDto);

        // assert
        // assert according to contract
        assertThat(result).isEqualToComparingFieldByField(expected);
    }
}
