package com.kripto.service;

import com.kripto.domain.Application;

import java.util.List;
import java.util.Optional;

public interface ApplicationService {

    Application saveApplication(Application application);
    List<Application> getAllApplications();
    List<String> getOptimizationSuggestions();
    public Optional<Application> getApplicationById(Long id);
    public void deleteApplication(Long id);
}
