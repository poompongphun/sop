package com.example.lab5new;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;

@Route(value = "index2")
public class MyView2 extends FormLayout {
    private TextField addWordTxt, addSentenceTxt;
    private Button addGood, addBad, addSentence, showSentence;
    private ComboBox goodWords, badWords;
    private TextArea goodSentences, badSentence;

    private Notification nf;

    public MyView2() {
        this.addWordTxt = new TextField("Add Word");
        this.addWordTxt.setWidthFull();
        this.addSentenceTxt = new TextField("Add Sentence");
        this.addSentenceTxt.setWidthFull();

        this.addGood = new Button("Add Good Word");
        this.addGood.setWidthFull();
        this.addBad = new Button("Add Bad Word");
        this.addBad.setWidthFull();
        this.addSentence = new Button("Add Sentence");
        this.addSentence.setWidthFull();
        this.showSentence = new Button("Show Sentence");
        this.showSentence.setWidthFull();

        this.goodSentences = new TextArea("Good Sentences");
        this.goodSentences.setWidthFull();
        this.badSentence = new TextArea("Bad Sentences");
        this.badSentence.setWidthFull();

        this.goodWords = new ComboBox("Good Words");
        this.goodWords.setWidthFull();
        this.badWords = new ComboBox("Bad Words");
        this.badWords.setWidthFull();

        this.nf = new Notification();
        this.nf.setDuration(5000);

        this.addGood.addClickListener(event -> {
            ArrayList<String> out = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/addGood/" + this.addWordTxt.getValue())
                    .retrieve()
                    .bodyToMono(ArrayList.class)
                    .block();
            this.goodWords.setItems(out);
            this.nf.setText("Insert " + this.addWordTxt.getValue() + " to Good Word List Complete");
            this.nf.open();
        });

        this.addBad.addClickListener(event -> {
            ArrayList<String> out = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/addBad/" + this.addWordTxt.getValue())
                    .retrieve()
                    .bodyToMono(ArrayList.class)
                    .block();
            this.badWords.setItems(out);
            this.nf.setText("Insert " + this.addWordTxt.getValue() + " to Bad Word List Complete");
            this.nf.open();
        });

        this.addSentence.addClickListener(event -> {
            String out = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/proof/" + this.addSentenceTxt.getValue())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            this.nf.setText(out);
            this.nf.open();
        });

        this.showSentence.addClickListener(event -> {
            Sentence out = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/getSentence")
                    .retrieve()
                    .bodyToMono(Sentence.class)
                    .block();
            System.out.println(out.goodSentences);
            this.goodSentences.setValue(String.valueOf(out.goodSentences));
            this.badSentence.setValue(String.valueOf(out.badSentences));
        });


        this.add(new VerticalLayout(addWordTxt, addGood, addBad, goodWords, badWords), new VerticalLayout(addSentenceTxt, addSentence, goodSentences, badSentence, showSentence));
    }

}
