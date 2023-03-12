package oslomet.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestBankController {

  @InjectMocks
  // denne skal testes
  private BankController bankController;

  @Mock
  // denne skal Mock'es
  private BankRepository repository;

  @Mock
  // denne skal Mock'es
  private Sikkerhet sjekk;

  @Test
  public void initDB() {
   //arrange
    when(repository.initDB(any())).thenReturn("OK");

    //act
    String resultat = bankController.initDB();

    //assert
    assertEquals(resultat, "OK");
  }

  @Test
  public void hentTransaksjoner_LoggetInn() {
    // arrange
    Konto enKonto = new Konto(
      "105010123456",
      "01010110523",
      720,
      "Lønnskonto",
      "NOK",
      null
    );

    when(sjekk.loggetInn()).thenReturn("01010110523");

    when(repository.hentTransaksjoner(anyString(), anyString(), anyString()))
      .thenReturn(enKonto);

    // act
    Konto resultat = bankController.hentTransaksjoner(
      "105010123456",
      "2020-01-01",
      "2020-01-31"
    );

    // assert
    assertEquals(enKonto, resultat);
  }

  @Test
  public void hentTransaksjoner_IkkeloggetInn() {
    // arrange
    when(sjekk.loggetInn()).thenReturn(null);

    // act
    Konto resultat = bankController.hentTransaksjoner(
      "105010123456",
      "2020-01-01",
      "2020-01-31"
    );

    // assert
    assertNull(resultat);
  }

  @Test
  public void hentKonti_LoggetInn() {
    // arrange
    List<Konto> konti = new ArrayList<>();
    Konto konto1 = new Konto(
      "105010123456",
      "01010110523",
      720,
      "Lønnskonto",
      "NOK",
      null
    );
    Konto konto2 = new Konto(
      "105010123456",
      "12345678901",
      1000,
      "Lønnskonto",
      "NOK",
      null
    );
    konti.add(konto1);
    konti.add(konto2);

    when(sjekk.loggetInn()).thenReturn("01010110523");

    when(repository.hentKonti(anyString())).thenReturn(konti);

    // act
    List<Konto> resultat = bankController.hentKonti();

    // assert
    assertEquals(konti, resultat);
  }

  @Test
  public void hentKonti_IkkeLoggetInn() {
    // arrange

    when(sjekk.loggetInn()).thenReturn(null);

    // act
    List<Konto> resultat = bankController.hentKonti();

    // assert
    assertNull(resultat);
  }

  @Test
  public void hentSaldi_LoggetInn() {
    // arrange
    List<Konto> saldi = new ArrayList<>();
    Konto saldo = new Konto(
      "105010123456",
      "01010110523",
      720,
      "Lønnskonto",
      "NOK",
      null
    );
    Konto saldo1 = new Konto(
      "105010123456",
      "12345678901",
      1000,
      "Lønnskonto",
      "NOK",
      null
    );
    saldi.add(saldo);
    saldi.add(saldo1);

    when(sjekk.loggetInn()).thenReturn("01010110523");

    when(repository.hentSaldi(anyString())).thenReturn(saldi);

    // act
    List<Konto> resultat = bankController.hentSaldi();

    // assert

    assertEquals(saldi, resultat);
  }

  @Test
  public void hentSaldi_IkkeLoggetInn() {
    // arrange
    when(sjekk.loggetInn()).thenReturn(null);

    // act
    List<Konto> resultat = bankController.hentSaldi();

    // assert
    assertNull(resultat);
  }

  @Test
  public void registrerBetaling_LoggetInn() {
    // arrange
    Transaksjon transaksjon = new Transaksjon(
      105010,
      "1202.24.12345",
      200.00,
      "Hei hei",
      "test",
      "test",
      "1202.24.12345"
    );

    when(sjekk.loggetInn()).thenReturn("01010110523");

    when(repository.registrerBetaling(any())).thenReturn("OK");

    // act
    String result = bankController.registrerBetaling(transaksjon);

    // assert
    assertEquals("OK", result);
  }

  @Test
  public void registrerBetaling_IkkeLoggetInn() {
    // arrange
    when(sjekk.loggetInn()).thenReturn(null);

    // act
    String result = bankController.registrerBetaling(null);

    // assert
    assertNull(result);
  }

  @Test
  public void hentbetalinger_LoggetInn() {
    // arrange
    List<Transaksjon> transaksjoner = new ArrayList<>();
    transaksjoner.add(
      new Transaksjon(
        105010,
        "1202.24.12345",
        200.00,
        "Hei hei",
        "test",
        "test",
        "1202.24.12345"
      )
    );

    when(sjekk.loggetInn()).thenReturn("01010110523");

    // act
    when(repository.hentBetalinger(anyString())).thenReturn(transaksjoner);

    // assert

    assertEquals(transaksjoner, bankController.hentBetalinger());
  }

  @Test
  public void hentBetalinger_IkkeLoggetInn() {
    // arrange
    when(sjekk.loggetInn()).thenReturn(null);

    // act
    List<Transaksjon> resultat = bankController.hentBetalinger();

    // assert
    assertNull(resultat);
  }

  @Test
  public void utforBetaling_LoggetInn() {
    // arrange
    List<Transaksjon> transaksjoner = new ArrayList<>();
    transaksjoner.add(
      new Transaksjon(
        105010,
        "1202.24.12345",
        200.00,
        "Hei hei",
        "test",
        "test",
        "1202.24.12345"
      )
    );

    when(sjekk.loggetInn()).thenReturn("01010110523");

    when(repository.utforBetaling(anyInt())).thenReturn("OK");

    when(repository.hentBetalinger(anyString())).thenReturn(transaksjoner);

    // act
    List<Transaksjon> resultat = bankController.utforBetaling(105010);

    // assert
    assertEquals(transaksjoner, resultat);
  }

  @Test
  public void utforBetaling_IkkeLoggetInn() {
    // arrange
    when(sjekk.loggetInn()).thenReturn(null);

    // act
    List<Transaksjon> resultat = bankController.utforBetaling(105010);

    // assert
    assertNull(resultat);
  }

  @Test
  public void hentKundeInfo_loggetInn() {
    // arrange
    Kunde enKunde = new Kunde(
      "01010110523",
      "Lene",
      "Jensen",
      "Askerveien 22",
      "3270",
      "Asker",
      "22224444",
      "HeiHei"
    );

    when(sjekk.loggetInn()).thenReturn("01010110523");

    when(repository.hentKundeInfo(anyString())).thenReturn(enKunde);

    // act
    Kunde resultat = bankController.hentKundeInfo();

    // assert
    assertEquals(enKunde, resultat);
  }

  @Test
  public void hentKundeInfo_IkkeloggetInn() {
    // arrange
    when(sjekk.loggetInn()).thenReturn(null);

    //act
    Kunde resultat = bankController.hentKundeInfo();

    // assert
    assertNull(resultat);
  }

  @Test
  public void endre_LoggetInn() {
    // Arrange
    when(sjekk.loggetInn()).thenReturn("01010110523");

    Kunde innKunde = new Kunde();
    innKunde.setFornavn("Lene");
    innKunde.setEtternavn("Jensen");
    innKunde.setAdresse("Askerveien 22");

    when(repository.endreKundeInfo(any())).thenReturn("OK");

    // Act
    String resultat = bankController.endre(innKunde);

    // Assert
    assertEquals("OK", resultat);
  }

  @Test
  public void endre_IkkeLoggetInn() {
    // Arrange
    when(sjekk.loggetInn()).thenReturn(null);

    // Act
    String resultat = bankController.endre(null);

    // Assert
    assertNull(resultat);
  }
}
