package com.kripto.service.impl;

import com.kripto.domain.Application;
import com.kripto.repository.ApplicationRepository;
import com.kripto.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;
    @Override
    public Application saveApplication(Application application) {
        return applicationRepository.save(application);
    }

    @Override
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    @Override
    public List<String> getOptimizationSuggestions() {
        List<Application> applications = applicationRepository.findAll();

        // Sugerencias para aplicaciones obsoletas
        List<String> obsoleteSuggestions = applications.stream()
                .filter(Application::isObsolete)
                .map(app -> "Considerar remover aplicación obsoleta: " + app.getName())
                .collect(Collectors.toList());

        // Sugerencias para aplicaciones redundantes
        List<String> redundantSuggestions = applications.stream()
                .collect(Collectors.groupingBy(Application::getName, Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(entry -> "Considerar remover aplicación de nombre ya existente: " + entry.getKey())
                .collect(Collectors.toList());

        // Combinar ambas listas de sugerencias
        return Stream.concat(obsoleteSuggestions.stream(), redundantSuggestions.stream())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Application> getApplicationById(Long id) {
        return applicationRepository.findById(id);
    }

    @Override
    public void deleteApplication(Long id) {
        applicationRepository.deleteById(id);
    }
}
