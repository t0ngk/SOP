package com.t0ng.lab06_redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WizardController {

    @Autowired
    private WizardService WizardService;

    @RequestMapping(value = "/wizards", method = RequestMethod.GET)
    public ResponseEntity<?> getAllWizards() {
        List<Wizard> wizards = WizardService.getAllWizard();
        Wizards w = new Wizards();
        w.setModel(wizards);
        return ResponseEntity.ok(w);
    }

    @RequestMapping(value = "/addWizard", method = RequestMethod.POST)
    public ResponseEntity<?> createWizard(@RequestBody Wizard wizard) {
        Object newWizard = WizardService.createWizard(wizard);
        return ResponseEntity.ok(newWizard);
    }

    @RequestMapping(value = "/updateWizard/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateWizardById(@PathVariable String id, @RequestBody Wizard wizard) {
        System.out.println("id: " + id);
        System.out.println(wizard);
        Object updatedWizard = WizardService.updateWizardById(id, wizard);
        return ResponseEntity.ok(updatedWizard);
    }

    @RequestMapping(value = "/deleteWizard/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteWizardById(@PathVariable String id) {
        Object deletedWizard = WizardService.deleteWizardById(id);
        return ResponseEntity.ok(deletedWizard);
    }
}
