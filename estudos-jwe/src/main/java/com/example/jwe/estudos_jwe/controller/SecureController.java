package com.example.jwe.estudos_jwe.controller;

import com.example.jwe.estudos_jwe.security.JweService;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class SecureController {
    
    private final JweService jweService;
    
    public SecureController(JweService jweService) {
        this.jweService = jweService;
    }
    
    @GetMapping("/secure")
    public String secureEndpoint(HttpServletRequest request) {
        String decryptedData = (String) request.getAttribute("decryptedData");
        // Processa os dados decriptografados
        return "Processed: " + decryptedData;
    }
    // Endpoint auxiliar para gerar token criptografado
    @PostMapping("/encrypt")
    public String getEncryptedToken(@RequestBody String payload) throws JOSEException {
        return jweService.encrypt(payload);
    }

    @GetMapping("/decrypt-test")
    public ResponseEntity<String> testDecryption(HttpServletRequest request) {
    String decryptedData = (String) request.getAttribute("decryptedData");
    
    if (decryptedData == null) {
        return ResponseEntity.badRequest().body("No encrypted header found");
    }
    
    return ResponseEntity.ok("Decrypted data: " + decryptedData);
}
}