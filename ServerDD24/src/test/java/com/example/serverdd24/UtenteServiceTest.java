package com.example.serverdd24;

import com.example.serverdd24.Service.UtenteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;
import java.sql.Timestamp;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UtenteServiceTest {

    @Autowired
    private UtenteService utenteService;

    @Test
    public void validateLoginTestCase1() {
        String email = "email_esistente@test.com";
        String password = "Password1234corretta";

        boolean result = utenteService.validateLogin(email, password);
        Assertions.assertTrue(result);
    }

    @Test
    public void validateLoginTestCase2() {
        testInvalidCredentials("email_esistente@test.com", "password_non_corretta", "Credenziali non valide");
    }

    @Test
    public void validateLoginTestCase3() {
        testInvalidCredentials("email_non_esistente@test.com", "password_non_esistente", "Utente non trovato per l'email fornita.");
    }

    private void testInvalidCredentials(String email, String password, String expectedMessage) {
        Exception exception = assertThrows(ResponseStatusException.class, () -> utenteService.validateLogin(email, password));

        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void validatePinTestCase1() {
        String email = "email_esistente@test.com";

        Integer pin = utenteService.inviaCodiceRecuperoTest(email);

        boolean result = utenteService.validatePin(email, pin);
        Assertions.assertTrue(result);
    }

    @Test
    public void validatePinTestCase2() {
        String email = "email_esistente@test.com";

        Integer pin = 0;

        Exception exception = assertThrows(ResponseStatusException.class, () -> utenteService.validatePin(email, pin));

        String expectedMessage = "Pin non valido.";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void validatePinTestCase3() {
        String email = "email_esistente@test.com";

        Integer pin = utenteService.inviaCodiceRecuperoTest(email);

        Timestamp now = new Timestamp(System.currentTimeMillis() + (60 * 1000));
        Timestamp expiredTime = new Timestamp(now.getTime() + 61000); // 61000 millisecondi = 61 secondi

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> utenteService.validatePinTest(email, pin, expiredTime), "Expected validatePin to throw, but it didn't");
    }

    @Test
    public void validatePinTestCase4() {
        String email = "email_non_esistente@test.com";
        Integer pin = 123456;  // Un pin qualsiasi

        Exception exception = assertThrows(ResponseStatusException.class, () -> utenteService.validatePin(email, pin));

        String expectedMessage = "Utente non trovato per l'email fornita.";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }


}
