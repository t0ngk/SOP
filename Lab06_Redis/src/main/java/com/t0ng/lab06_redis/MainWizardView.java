package com.t0ng.lab06_redis;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Route("/mainPage.it")
public class MainWizardView extends VerticalLayout {
    private TextField name = new TextField();
    private RadioButtonGroup<String> gender = new RadioButtonGroup<>("Gender :");
    private ComboBox<String> position = new ComboBox<>();
    private NumberField money = new NumberField("Dollars");
    private ComboBox<String> school = new ComboBox<>();
    private ComboBox<String> house = new ComboBox<>();
    private HorizontalLayout toolbar = new HorizontalLayout();
    private Button prevBtn = new Button("<<");
    private Button createBtn = new Button("Create");
    private Button updateBtn = new Button("Update");
    private Button deleteBtn = new Button("Delete");
    private Button nextBtn = new Button(">>");

    private List<Wizard> wizards = getWizards().getModel();
    private int index = 0;

    public MainWizardView() {
        name.setPlaceholder("Fullname");
        gender.setItems("Male", "Female");
        position.setPlaceholder("Position");
        position.setItems("Student", "Teacher");
        money.setPrefixComponent(new Span("$"));
        school.setPlaceholder("School");
        school.setItems("Hogwarts", "Beauxbatons", "Durmstrang");
        house.setPlaceholder("House");
        house.setItems("Gryffindor", "Hufflepuff", "Ravenclaw", "Slytherin");
        toolbar.add(prevBtn, createBtn, updateBtn, deleteBtn, nextBtn);

        this.add(name);
        this.add(gender);
        this.add(position);
        this.add(money);
        this.add(school);
        this.add(house);
        this.add(toolbar);

        setInfo(index);

        nextBtn.addClickListener(e -> {
            if (index < wizards.size() - 1) {
                index++;
                setInfo(index);
            }
        });

        prevBtn.addClickListener(e -> {
            if (index > 0) {
                index--;
                setInfo(index);
            }
        });

        createBtn.addClickListener(e -> {
            Wizard wizard = getInfo();
            WebClient.create()
                    .post()
                    .uri("http://localhost:8080/addWizard")
                    .bodyValue(wizard)
                    .retrieve()
                    .bodyToMono(Wizard.class)
                    .block();
            wizards = getWizards().getModel();
            setInfo(wizards.size() - 1);
            Notification.show("Wizard has been Created");
        });

        deleteBtn.addClickListener(e -> {
            Wizard wizard = getInfo();
            WebClient.create()
                    .delete()
                    .uri("http://localhost:8080/deleteWizard/" + wizards.get(index).get_id())
                    .retrieve()
                    .bodyToMono(Wizard.class)
                    .block();
            wizards = getWizards().getModel();
            setInfo(index > 0 ? index - 1 : 0);
            Notification.show("Wizard has been Deleted");
        });

        updateBtn.addClickListener(e -> {
            Wizard wizard = getInfo();
            WebClient.create()
                    .put()
                    .uri("http://localhost:8080/updateWizard/" + wizards.get(index).get_id())
                    .bodyValue(wizard)
                    .retrieve()
                    .bodyToMono(Wizard.class)
                    .block();
            wizards = getWizards().getModel();
            setInfo(index);
            Notification.show("Wizard has been Updated");
        });
    }

    public void setInfo(int index) {
        if (index <= wizards.size() - 1) {
            Wizard wizard = wizards.get(index);
            name.setValue(wizard.getName());
            if (wizard.getSex().equals("m")) {
                gender.setValue("Male");
            } else {
                gender.setValue("Female");
            }
            position.setValue(wizard.getPosition());
            money.setValue(wizard.getMoney());
            school.setValue(wizard.getSchool());
            house.setValue(wizard.getHouse());

            nextBtn.setEnabled(index != wizards.size() - 1);
            prevBtn.setEnabled(index != 0);
        }
    }

    public Wizard getInfo() {
        return new Wizard(null, gender.getValue().equals("Male") ? "m" : "f", name.getValue(), school.getValue(), house.getValue(), money.getValue(), position.getValue());
    }

    private Wizards getWizards() {
        return WebClient.create()
                .get()
                .uri("http://localhost:8080/wizards")
                .retrieve()
                .bodyToMono(Wizards.class)
                .block();
    }
}
