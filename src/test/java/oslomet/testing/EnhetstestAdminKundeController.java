package oslomet.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Sikkerhet.Sikkerhet;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKundeController {

  @InjectMocks
  // denne skal testes
  private AdminKundeController kundeController;

  @Mock
  // denne skal Mock'es
  private AdminRepository repository;

  @Mock
  private Sikkerhet sjekk;

  @Test
  public void hentAlle_loggetInn() {
    //arrange
    List<Kunde> kunder = new ArrayList<>();

    Kunde testKunde = new Kunde(
      "03039937332",
      "Jon",
      "Doe",
      "Osloveien 4",
      "0368",
      "Oslo",
      "91004203",
      "Passord123"
    );
    kunder.add(testKunde);

    when(sjekk.loggetInn()).thenReturn("03039937332");
    when(repository.hentAlleKunder()).thenReturn(kunder);

    //act
    List<Kunde> resultat = kundeController.hentAlle();

    //assert
    assertEquals(kunder, resultat);
  }

  @Test
  public void hentAlle_ikkeLoggetInn() {
    //arrange
    when(sjekk.loggetInn()).thenReturn(null);

    //Act
    List<Kunde> resultat = kundeController.hentAlle();

    //assert
    assertNull(resultat);
  }

  @Test
  public void lagreKunde_LoggetInn() {
    //arrange
    Kunde testKunde = new Kunde(
      "03039937332",
      "Jon",
      "Doe",
      "Osloveien 4",
      "0368",
      "Oslo",
      "91004203",
      "Passord123"
    );

    when(sjekk.loggetInn()).thenReturn("03039937332");
    when(repository.registrerKunde(testKunde)).thenReturn("OK");

    //act
    String resultat = kundeController.lagreKunde(testKunde);

    //assert
    assertEquals("OK", resultat);
  }

  @Test
  public void lagre_ikkeLoggetInn() {
    //arrange
    Kunde lagreKunde = new Kunde(
      "03039937332",
      "Jon",
      "Doe",
      "Osloveien 4",
      "0368",
      "Oslo",
      "91004203",
      "Passord123"
    );

    when(sjekk.loggetInn()).thenReturn(null);

    //act
    String resultat = kundeController.lagreKunde(lagreKunde);

    //assert
    assertEquals("Ikke logget inn", resultat);
  }

  @Test
  public void endre_LoggetInn() {
    //arrange
    Kunde testKunde = new Kunde(
      "03039937332",
      "Jon",
      "Doe",
      "Osloveien 4",
      "0368",
      "Oslo",
      "91004203",
      "Passord123"
    );

    when(sjekk.loggetInn()).thenReturn("03039937332");
    when(repository.endreKundeInfo(testKunde)).thenReturn("OK");

    //act
    String resultat = kundeController.endre(testKunde);

    //assert
    assertEquals("OK", resultat);
  }

  @Test
  public void endre_ikkeLoggetInn() {
    //arrange
    Kunde testKunde = new Kunde(
      "03039937332",
      "Jon",
      "Doe",
      "Osloveien 4",
      "0368",
      "Oslo",
      "91004203",
      "Passord123"
    );

    when(sjekk.loggetInn()).thenReturn(null);

    //act
    String resultat = kundeController.endre(testKunde);

    //assert
    assertEquals("Ikke logget inn", resultat);
  }

  @Test
  public void slett_LoggetInn() {
    //arrange
    String personnr = "03039937332";

    when(sjekk.loggetInn()).thenReturn(personnr);
    when(repository.slettKunde(anyString())).thenReturn("OK");

    //act
    String resultat = kundeController.slett(personnr);

    //assert
    assertEquals("OK", resultat);
  }

  @Test
  public void slett_ikkeLoggetInn() {
    //arrange
    String personnr = "03039937332";

    when(sjekk.loggetInn()).thenReturn(null);

    //act
    String resultat = kundeController.slett(personnr);

    //assert
    assertEquals("Ikke logget inn", resultat);
  }
}
