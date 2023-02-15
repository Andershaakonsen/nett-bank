package oslomet.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Sikkerhet.Sikkerhet;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestSikkerhet {

  @InjectMocks
  private Sikkerhet sjekk;

  @Mock
  private BankRepository rep;

  @Mock
  private HttpSession session;

  @Test
  public void sjekkLoggInn() {
    // Arrange
    when(rep.sjekkLoggInn(anyString(), anyString())).thenReturn("OK");

    // Act
    String resultat = sjekk.sjekkLoggInn("12345678901", "123446");

    // Assert
    assertEquals("OK", resultat);
  }

  @Test
  public void sjekkLoggInn_FeilPnr() {
    // Act
    String resultat = sjekk.sjekkLoggInn("123456780", "123446");

    // Assert
    assertEquals("Feil i personnummer", resultat);
  }

  @Test
  public void sjekkLoggInn_FeilPassord() {
    // Act
    String resultat = sjekk.sjekkLoggInn("12345678901", "1234");

    // Assert
    assertEquals("Feil i passord", resultat);
  }

  @Test
  public void sjekkLoggInn_Feil() {
    // Arrange
    when(rep.sjekkLoggInn(anyString(), anyString())).thenReturn("Ugyldig");

    // Act
    String resultat = sjekk.sjekkLoggInn("12345678901", "1234446");

    // Assert
    assertEquals("Feil i personnummer eller passord", resultat);
  }

  @Test
  public void loggInnAdmin() {
    // Act
    String resultat = sjekk.loggInnAdmin("Admin", "Admin");

    // Assert
    assertEquals("Logget inn", resultat);
  }

  @Test
  public void loggInnAdmin_Feil() {
    // Act
    String resultat = sjekk.loggInnAdmin("Admin", "Admin1");

    // Assert
    assertEquals("Ikke logget inn", resultat);
  }

  @Test
  public void loggetInn() {
    // Arrange
    when(session.getAttribute("Innlogget")).thenReturn("12345678901");

    // Act
    String resultat = sjekk.loggetInn();

    // Assert
    assertEquals("12345678901", resultat);
  }

  @Test
  public void loggetInn_Feil() {
    // Arrange
    when(session.getAttribute("Innlogget")).thenReturn(null);

    // Act
    String resultat = sjekk.loggetInn();

    // Assert
    assertNull(resultat);
  }
}
