package com.example.lab6.repository;

import com.example.lab6.pojo.Wizard;
import com.example.lab6.pojo.Wizards;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class WizardService {

    @Autowired
    private WizardRepository repository;

    public Wizards getAllWizards() {
        Wizards wizards = new Wizards();
        wizards.wizards = (ArrayList<Wizard>) this.repository.findAll();
        return wizards;
    }

    public void addWizard(Wizard wz) {
        repository.insert(wz);
    }

    public void updateWizard(Wizard wz) {
        repository.save(wz);
    }

    public void deleteWizard(String _id) {
        repository.deleteById(_id);
    }

}
