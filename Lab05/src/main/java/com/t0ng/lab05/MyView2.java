package com.t0ng.lab05;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;

@Route("/index2")
public class MyView2 extends FormLayout {
    private TextField addWordField = new TextField("Add Word");
    private TextField addSentenceField = new TextField("Add Sentence");
    private Button addGoodWord = new Button("Add Good Word");
    private Button addSentence = new Button("Add Sentence");
    private Button addBadWord = new Button("Add Bad Word");
    private TextArea goodSentenceArea = new TextArea("Good Sentence");
    private ComboBox<String> goodWordCombo = new ComboBox<String>("Good Words");
    private TextArea badSentenceArea = new TextArea("Bad Sentence");
    private ComboBox<String> badWordCombo = new ComboBox<String>("Bad Words");
    private Button showSentence = new Button("Show Sentence");

    public MyView2() {
        this.add(addWordField);
        this.add(addSentenceField);
        this.add(addGoodWord);
        this.add(addSentence);
        this.add(addBadWord);
        this.add(goodSentenceArea);
        this.add(goodWordCombo);
        this.add(badSentenceArea);
        this.add(badWordCombo);
        this.add(showSentence);

        addGoodWord.addClickListener(event -> {
            String word = addWordField.getValue();
            ArrayList result = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/addGood/" + word)
                    .retrieve()
                    .bodyToMono(ArrayList.class)
                    .block();
            goodWordCombo.setItems(result);
            addWordField.setValue("");
            Notification.show("Good Word Added : " + word);
        });

        addBadWord.addClickListener(event -> {
           String word = addWordField.getValue();
           ArrayList result = WebClient.create()
                   .post()
                   .uri("http://localhost:8080/addBad/" + word)
                   .retrieve()
                   .bodyToMono(ArrayList.class)
                   .block();
           badWordCombo.setItems(result);
           addWordField.setValue("");
              Notification.show("Bad Word Added : " + word);
        });

        addSentence.addClickListener(event -> {
            String sentence = addSentenceField.getValue();
            String result = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/proof/" + sentence)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            Notification.show(result);
        });

        showSentence.addClickListener(event -> {
            Sentence result = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/getSentence")
                    .retrieve()
                    .bodyToMono(Sentence.class)
                    .block();
            goodSentenceArea.setValue(result.goodSentences.toString());
            badSentenceArea.setValue(result.badSentences.toString());
        });
    }
}
