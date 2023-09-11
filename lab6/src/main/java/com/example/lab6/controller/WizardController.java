package com.example.lab6.controller;

import com.example.lab6.pojo.Wizard;
import com.example.lab6.pojo.Wizards;
import com.example.lab6.repository.WizardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class WizardController {

    @Autowired
    private WizardService wizardService;

    @RequestMapping(value ="/wizards", method = RequestMethod.GET)
    public Wizards getWizards() {
        return wizardService.getAllWizards();
    }

    @RequestMapping(value ="/addWizard", method = RequestMethod.POST)
    public Wizard addWizard(@RequestBody LinkedMultiValueMap<String, String> wz) {
        Map<String, String> d = wz.toSingleValueMap();
        Wizard wzNew = new Wizard(null, d.get("name"), d.get("sex"), d.get("school"), d.get("house"), d.get("position"), Double.parseDouble(d.get("money")));
        wizardService.addWizard(wzNew);
        return wzNew;
    }

    @RequestMapping(value ="/updateWizard", method = RequestMethod.POST)
    public Wizard updateWizard(@RequestBody LinkedMultiValueMap<String, String> wz) {
        Map<String, String> d = wz.toSingleValueMap();
        Wizard wzNew = new Wizard(d.get("_id"), d.get("name"), d.get("sex"), d.get("school"), d.get("house"), d.get("position"), Double.parseDouble(d.get("money")));
        wizardService.updateWizard(wzNew);
        return wzNew;
    }

    @RequestMapping(value ="/deleteWizard", method = RequestMethod.POST)
    public String deleteWizard(@RequestBody LinkedMultiValueMap<String, String> wz) {
        Map<String, String> d = wz.toSingleValueMap();
        wizardService.deleteWizard(d.get("_id"));
        return "Delete Success";
    }
}
