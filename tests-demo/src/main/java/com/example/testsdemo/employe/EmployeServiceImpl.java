package com.example.testsdemo.employe;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeServiceImpl implements EmployeService {

    private final EmployeRepository repository;

    @Override
    public List<Employe> listerTous() {
        return repository.findAll();
    }

    @Override
    public Employe trouverParId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EmployeNotFoundException("Employé introuvable : " + id));
    }

    @Override
    public Employe creer(EmployeDTO dto) {
        Employe employe = new Employe(
                null,
                dto.getNom(),
                dto.getDepartement(),
                dto.getSalaire()
        );

        return repository.save(employe);
    }

    @Override
    public void supprimer(Long id) {
        Employe employe = trouverParId(id);
        repository.delete(employe);
    }
}