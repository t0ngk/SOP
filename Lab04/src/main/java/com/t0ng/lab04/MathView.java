package com.t0ng.lab04;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Route;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Route("/index1")
public class MathView extends VerticalLayout {
    private NumberField number1Field = new NumberField("Number 1");
    private NumberField number2Field = new NumberField("Number 2");
    private HorizontalLayout groupButton = new HorizontalLayout();
    private Button addButton = new Button("+");
    private Button minusButton = new Button("-");
    private Button divideButton = new Button("/");
    private Button multiButton = new Button("*");
    private Button modButton = new Button("Mod");
    private Button maxButton = new Button("Max");
    private NumberField resultField = new NumberField("Result");

    public MathView() {
        groupButton.add(addButton, minusButton, multiButton, divideButton, modButton, maxButton);
        resultField.setReadOnly(true);

        add(number1Field);
        add(number2Field);
        add("Operation");
        add(groupButton);
        add(resultField);

        addButton.addClickListener(e -> {
            Double number1 = number1Field.getValue();
            Double number2 = number2Field.getValue();

            Double result = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/add/" + number1 + "/" + number2)
                    .retrieve()
                    .bodyToMono(Double.class)
                    .block();

            resultField.setValue(result);
        });

        minusButton.addClickListener(e -> {
            Double number1 = number1Field.getValue();
            Double number2 = number2Field.getValue();

            Double result = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/minus/" + number1 + "/" + number2)
                    .retrieve()
                    .bodyToMono(Double.class)
                    .block();

            resultField.setValue(result);
        });

        divideButton.addClickListener(e -> {
            Double number1 = number1Field.getValue();
            Double number2 = number2Field.getValue();

            Double result = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/divide/" + number1 + "/" + number2)
                    .retrieve()
                    .bodyToMono(Double.class)
                    .block();

            resultField.setValue(result);
        });

        multiButton.addClickListener(e -> {
            Double number1 = number1Field.getValue();
            Double number2 = number2Field.getValue();

            Double result = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/multi/" + number1 + "/" + number2)
                    .retrieve()
                    .bodyToMono(Double.class)
                    .block();

            resultField.setValue(result);
        });

        modButton.addClickListener(e -> {
            Double number1 = number1Field.getValue();
            Double number2 = number2Field.getValue();

            Double result = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/mod/" + number1 + "/" + number2)
                    .retrieve()
                    .bodyToMono(Double.class)
                    .block();

            resultField.setValue(result);
        });

        maxButton.addClickListener(e -> {
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("n1", number1Field.getValue().toString());
            formData.add("n2", number2Field.getValue().toString());

            Double result = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/max")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .bodyToMono(Double.class)
                    .block();

            resultField.setValue(result);
        });
    }
}