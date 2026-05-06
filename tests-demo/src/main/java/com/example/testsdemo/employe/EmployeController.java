package com.example.testsdemo.employe;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employes")
@RequiredArgsConstructor
public class EmployeController {

    private final EmployeService service;

    @GetMapping
    public ResponseEntity<?> listerTous() {
        return ResponseEntity.ok(service.listerTous());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> trouverParId(@PathVariable Long id) {
        return ResponseEntity.ok(service.trouverParId(id));
    }

    @PostMapping
    public ResponseEntity<?> creer(@Valid @RequestBody EmployeDTO dto) {
        Employe employe = service.creer(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(employe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        service.supprimer(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(EmployeNotFoundException.class)
    public ResponseEntity<?> handleNotFound() {
        return ResponseEntity.notFound().build();
    }
}