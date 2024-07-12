package com.kripto.controller;

import com.kripto.domain.Application;
import com.kripto.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<Application> createApplication(@Validated @RequestBody Application application) {
        Application savedApplication = applicationService.saveApplication(application);
        return new ResponseEntity<>(savedApplication, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Application>> getAllApplications() {
        List<Application> applications = applicationService.getAllApplications();
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }

    @GetMapping("/optimize")
    public ResponseEntity<List<String>> getOptimizationSuggestions() {
        List<String> suggestions = applicationService.getOptimizationSuggestions();
        return new ResponseEntity<>(suggestions, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Application> updateApplication(@PathVariable Long id, @Validated @RequestBody Application applicationDetails) {
        Optional<Application> applicationOptional = applicationService.getApplicationById(id);

        if (!applicationOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Application application = applicationOptional.get();
        application.setName(applicationDetails.getName());
        application.setClient(applicationDetails.getClient());
        application.setDescription(applicationDetails.getDescription());
        application.setObsolete(applicationDetails.isObsolete());
        application.setResourceUsage(applicationDetails.getResourceUsage());
        application.setLastUpdated(applicationDetails.getLastUpdated());

        Application updatedApplication = applicationService.saveApplication(application);
        return new ResponseEntity<>(updatedApplication, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        Optional<Application> applicationOptional = applicationService.getApplicationById(id);

        if (!applicationOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        applicationService.deleteApplication(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
