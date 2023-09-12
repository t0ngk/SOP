package com.t0ng.lab06;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WizardService {
    @Autowired
    private final WizardRepository wizardRepository;

    public WizardService(WizardRepository wizardRepository) {
        this.wizardRepository = wizardRepository;
    }

    public List<Wizard> getAllWizard() {
        return wizardRepository.findAll();
    }

    public Wizard createWizard(Wizard wizard) {
        return wizardRepository.save(wizard);
    }

    public Wizard updateWizardById(String id, Wizard wizard) {
        Wizard wizardToUpdate = wizardRepository.findById(id).orElse(null);
        if (wizardToUpdate != null) {
            wizardToUpdate.setSex(wizard.getSex());
            wizardToUpdate.setName(wizard.getName());
            wizardToUpdate.setSchool(wizard.getSchool());
            wizardToUpdate.setHouse(wizard.getHouse());
            wizardToUpdate.setMoney(wizard.getMoney());
            wizardToUpdate.setPosition(wizard.getPosition());
            return wizardRepository.save(wizardToUpdate);
        }
        return null;
    }

    public Wizard deleteWizardById(String id) {
        Wizard wizardToDelete = wizardRepository.findById(id).orElse(null);
        if (wizardToDelete != null) {
            wizardRepository.deleteById(id);
            return wizardToDelete;
        }
        return null;
    }
}
