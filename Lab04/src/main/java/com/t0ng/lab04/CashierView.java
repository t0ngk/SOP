package com.t0ng.lab04;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Route;
import org.springframework.web.reactive.function.client.WebClient;

@Route("/index2")
public class CashierView extends VerticalLayout{
    private NumberField moneyField = new NumberField("เงินทอน");
    private Button changeButton = new Button("คำนวณเงินทอน");
    private NumberField b1000Field = new NumberField();
    private NumberField b500Field = new NumberField();
    private NumberField b100Field = new NumberField();
    private NumberField b20Field = new NumberField();
    private NumberField b10Field = new NumberField();
    private NumberField b5Field = new NumberField();
    private NumberField b1Field = new NumberField();

    public CashierView() {
        moneyField.setPrefixComponent(new Span("$"));
        b1000Field.setPrefixComponent(new Span("$1000"));
        b1000Field.setReadOnly(true);
        b500Field.setPrefixComponent(new Span("$500"));
        b500Field.setReadOnly(true);
        b100Field.setPrefixComponent(new Span("$100"));
        b100Field.setReadOnly(true);
        b20Field.setPrefixComponent(new Span("$20"));
        b20Field.setReadOnly(true);
        b10Field.setPrefixComponent(new Span("$10"));
        b10Field.setReadOnly(true);
        b5Field.setPrefixComponent(new Span("$5"));
        b5Field.setReadOnly(true);
        b1Field.setPrefixComponent(new Span("$1"));
        b1Field.setReadOnly(true);

        add(moneyField);
        add(changeButton);
        add(b1000Field);
        add(b500Field);
        add(b100Field);
        add(b20Field);
        add(b10Field);
        add(b5Field);
        add(b1Field);

        changeButton.addClickListener(e -> {

            int money = moneyField.getValue().intValue();

            Change result = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/getChange/" + money)
                    .retrieve()
                    .bodyToMono(Change.class)
                    .block();

            b1000Field.setValue(Double.parseDouble(String.valueOf(result.getB1000())));
            b500Field.setValue(Double.parseDouble(String.valueOf(result.getB500())));
            b100Field.setValue(Double.parseDouble(String.valueOf(result.getB100())));
            b20Field.setValue(Double.parseDouble(String.valueOf(result.getB20())));
            b10Field.setValue(Double.parseDouble(String.valueOf(result.getB10())));
            b5Field.setValue(Double.parseDouble(String.valueOf(result.getB5())));
            b1Field.setValue(Double.parseDouble(String.valueOf(result.getB1())));
        });
    }

}
