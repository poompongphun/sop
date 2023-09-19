package com.example.lab71.repository;

import com.example.lab71.pojo.Wizard;
import com.example.lab71.pojo.Wizards;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class WizardService {

    @Autowired
    private WizardRepository repository;

    @Cacheable(value="mywizard")
    public Wizards getAllWizards() {
        Wizards wizards = new Wizards();
        wizards.wizards = (ArrayList<Wizard>) this.repository.findAll();
        return wizards;
    }

    @CacheEvict(value="mywizard", allEntries = true)
    public void addWizard(Wizard wz) {
        repository.insert(wz);
    }

    @CachePut(value = "mywizard")
    public void updateWizard(Wizard wz) {
        repository.save(wz);
    }

    @CacheEvict(value="mywizard", allEntries = true)
    public void deleteWizard(String _id) {
        repository.deleteById(_id);
    }

}
