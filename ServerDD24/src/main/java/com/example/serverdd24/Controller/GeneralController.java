package com.example.serverdd24.Controller;

import com.example.serverdd24.Utils.MessageResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
public class GeneralController {
    @GetMapping("/testConnection")
    public ResponseEntity<Object> testConnection() {
        String message = "Connessione avvenuta con successo!";
        return ResponseEntity.ok().body(new MessageResponse(message));
    }
}
