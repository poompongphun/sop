package com.example.lab71.view;

import com.example.lab71.pojo.Wizard;
import com.example.lab71.pojo.Wizards;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;


@Route("mainPage.it")
public class MainWizardView extends VerticalLayout {
    private TextField fNameTxt, dollarTxt;
    private ComboBox position, school, house;
    private Button leftBtn, rightBtn, createBtn, updateBtn, deleteBtn;
    private RadioButtonGroup<String> gender;
    private Notification nf;

    private int index;
    private Wizards wizards;

    public MainWizardView() {
        this.wizards = WebClient.create()
                .get()
                .uri("http://localhost:8080/wizards")
                .retrieve()
                .bodyToMono(Wizards.class)
                .block();

        this.fNameTxt = new TextField();
        this.fNameTxt.setPlaceholder("Fullname");

        this.gender = new RadioButtonGroup<>();
        this.gender.setLabel("Gender");
        this.gender.setItems("Male", "Female");

        this.position = new ComboBox("Position");
        this.position.setPlaceholder("Position");
        this.position.setItems("Student", "Teacher");

        this.dollarTxt = new TextField("Dollars");
        this.dollarTxt.setPrefixComponent(new Span("$"));

        this.school = new ComboBox();
        this.school.setPlaceholder("School");
        this.school.setItems("Hogwarts", "Beauxbatons", "Durmstrang");
        this.house = new ComboBox();
        this.house.setPlaceholder("House");
        this.house.setItems("Gryffindor", "Ravenclaw", "Hufflepuff", "Slyther");

        this.leftBtn = new Button("<<");
        this.createBtn = new Button("Create");
        this.updateBtn = new Button("Update");
        this.deleteBtn = new Button("Delete");
        this.rightBtn = new Button(">>");

        this.nf = new Notification();
        this.nf.setDuration(3000);

        this.leftBtn.addClickListener(event -> {
           if (index > 0) {
               index--;
               setData(index);
           }
        });

        this.rightBtn.addClickListener(event -> {
            if (index < this.wizards.wizards.size() - 1) {
                index++;
                setData(index);
            }
        });

        this.createBtn.addClickListener(event -> {
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("sex", this.gender.getValue());
            formData.add("name", this.fNameTxt.getValue());
            formData.add("school", this.school.getValue().toString());
            formData.add("house", this.house.getValue().toString());
            formData.add("money", this.dollarTxt.getValue());
            formData.add("position", this.position.getValue().toString());

            Wizard out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/addWizard")
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .bodyToMono(Wizard.class)
                    .block();

            this.wizards.wizards.add(out);
            this.nf.setText("Wizard has been Created");
            this.nf.open();
        });

        this.updateBtn.addClickListener(event -> {
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("_id", this.wizards.wizards.get(this.index).get_id());
            formData.add("sex", this.gender.getValue());
            formData.add("name", this.fNameTxt.getValue());
            formData.add("school", this.school.getValue().toString());
            formData.add("house", this.house.getValue().toString());
            formData.add("money", this.dollarTxt.getValue());
            formData.add("position", this.position.getValue().toString());

            Wizard out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/updateWizard")
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .bodyToMono(Wizard.class)
                    .block();

            this.wizards.wizards.set(this.index, out);
            this.nf.setText("Wizard has been Updated");
            this.nf.open();
        });

        this.deleteBtn.addClickListener(event -> {
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("_id", this.wizards.wizards.get(this.index).get_id());

            String out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/deleteWizard")
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            if (out.equals("Delete Success")) {
                this.wizards.wizards.remove(this.index);
                this.nf.setText("Wizard has been removed");
                this.nf.open();
            }
        });

        setData(0);

        add(this.fNameTxt, this.gender, this.position, this.dollarTxt, this.school, this.house, new HorizontalLayout(this.leftBtn, this.createBtn, this.updateBtn, this.deleteBtn, this.rightBtn));
    }

    public void setData(int i) {
        Wizard wz = this.wizards.wizards.get(i);
        this.fNameTxt.setValue(wz.getName());
        this.gender.setValue(wz.getSex().equals("m") ? "Male" : "Female");
        this.position.setValue(wz.getPosition());
        this.dollarTxt.setValue(String.valueOf(wz.getMoney()));
        this.school.setValue(wz.getSchool());
        this.house.setValue(wz.getHouse());
    }
}
