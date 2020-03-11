package csit.tap.employee.external.beer;

import csit.tap.employee.external.beer.client.BeerClient;
import csit.tap.employee.external.intoxication.IntoxicationRepository;
import csit.tap.employee.external.intoxication.IntoxicationStatus;
import csit.tap.employee.external.intoxication.IntoxicationStatusDto;
import csit.tap.employee.external.intoxication.PersonDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/*
 * This unit test tests the business logic within BeerService
 * This is similar to EmployeeServiceTest where we mock the return of the outside layers*/
@RunWith(MockitoJUnitRunner.class)
public class BeerServiceTest {

    @Mock
    private BeerClient beerClient;

    @Mock
    private IntoxicationRepository intoxicationRepository;

    @InjectMocks
    private BeerService beerService = new BeerService(beerClient, intoxicationRepository);

    @Test
    public void getIntoxicationStatusFromExternalSource_givenIntoxicatedPerson_shouldSaveInIntoxicationStatusInRepository(){
        // arrange
        IntoxicationStatusDto intoxicationStatusDtoReturn = new IntoxicationStatusDto("not tipsy", "tipsy now");
        PersonDto personDto = new PersonDto("alvin");

        // mocking return from the remote service as intoxicationDto
        Mockito.when(beerClient.GetIntoxicationStatus(personDto)).thenReturn(intoxicationStatusDtoReturn);

        // act
        boolean result = beerService.getIntoxicationStatusFromExternalSource();

        // assert
        assertThat(result).isTrue();
        Mockito.verify(intoxicationRepository, Mockito.times(1)).save(new IntoxicationStatus(intoxicationStatusDtoReturn));
    }
}
