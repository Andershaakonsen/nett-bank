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
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Sikkerhet.Sikkerhet;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKontoController {

  @InjectMocks
  // denne skal testes
  private AdminKontoController kontoController;

  @Mock
  // denne skal Mock'es
  private AdminRepository repository;

  @Mock
  private Sikkerhet sjekk;

  @Test
  public void hentAlleKonti() {
    //arrange
    List<Konto> konti = new ArrayList<>();
    konti.add(new Konto());

    when(sjekk.loggetInn()).thenReturn("03039937332");
    when(repository.hentAlleKonti()).thenReturn(konti);

    //act
    List<Konto> resultat = kontoController.hentAlleKonti();

    //assert
    assertEquals(konti, resultat);
  }

  @Test
  public void hentAlleKonti_ikkeLoggetInn() {
    //arrange
    when(sjekk.loggetInn()).thenReturn(null);

    //act
    List<Konto> resultat = kontoController.hentAlleKonti();

    //assert
    assertNull(resultat);
  }

  @Test
  public void registrerKonto_loggetInn() {
    //arrange
    Konto konto = new Konto();

    when(sjekk.loggetInn()).thenReturn("03039937332");
    when(repository.registrerKonto(konto)).thenReturn("OK");

    //act
    String resultat = kontoController.registrerKonto(konto);

    //assert
    assertEquals("OK", resultat);
  }

  @Test
  public void registrerKonto_ikkeLoggetInn() {
    //arrange
    Konto konto = new Konto();

    when(sjekk.loggetInn()).thenReturn(null);

    //act
    String resultat = kontoController.registrerKonto(konto);

    //assert
    assertEquals("Ikke innlogget", resultat);
  }

  @Test
  public void endreKonto_loggetInn() {
    //arrange
    Konto konto = new Konto();

    when(sjekk.loggetInn()).thenReturn("03039937332");
    when(repository.endreKonto(konto)).thenReturn("OK");

    //act
    String resultat = kontoController.endreKonto(konto);

    //assert
    assertEquals("OK", resultat);
  }

  @Test
  public void endreKonto_ikkeLoggetInn() {
    //arrange
    Konto konto = new Konto();

    when(sjekk.loggetInn()).thenReturn(null);

    //act
    String resultat = kontoController.endreKonto(konto);

    //assert
    assertEquals("Ikke innlogget", resultat);
  }

  @Test
  public void slettKonto_loggetInn() {
    //arrange
    String personnr = "03039937332";
    when(sjekk.loggetInn()).thenReturn(personnr);
    when(repository.slettKonto(anyString())).thenReturn("OK");

    //act
    String resultat = kontoController.slettKonto(personnr);

    //assert
    assertEquals("OK", resultat);
  }

  @Test
  public void slettKonto_ikkeLoggetInn() {
    //arrange
    String personnr = "03039937332";
    when(sjekk.loggetInn()).thenReturn(null);

    //act
    String resultat = kontoController.slettKonto(personnr);

    //assert
    assertEquals("Ikke innlogget", resultat);
  }
}
