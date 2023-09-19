package com.example.lab71.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("Wizard")
public class Wizard {

    @Id
    private String _id;
    private String name;
    private String sex;
    private String school;
    private String house;
    private String position;
    private double money;

    public Wizard() {}

    public Wizard(String _id, String name, String sex, String school, String house, String position, double money) {
        this._id = _id;
        this.name = name;
        this.sex = sex;
        this.school = school;
        this.house = house;
        this.position = position;
        this.money = money;
    }
}
