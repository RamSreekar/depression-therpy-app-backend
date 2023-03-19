package com.had.depressiontherapyappbackend.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "medical_history")
public class MedicalHistory {

    @Id
    @Column(name = "id")
    private int id;

    @OneToOne
    @MapsId
    @JsonBackReference
    private Patient patient;

    @Column(name = "height")
    private int height;

    @Column(name = "weight")
    private int weight;

    @Column(name = "is_smoker")
    private boolean isSmoker;

    @Column(name = "drinks_alcohol")
    private boolean drinksAlcohol;

    @Column(name = "diseases")
    private String diseases;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isSmoker() {
        return isSmoker;
    }

    public void setSmoker(boolean smoker) {
        isSmoker = smoker;
    }

    public boolean isDrinksAlcohol() {
        return drinksAlcohol;
    }

    public void setDrinksAlcohol(boolean drinksAlcohol) {
        this.drinksAlcohol = drinksAlcohol;
    }

    public String getDiseases() {
        return diseases;
    }

    public void setDiseases(String diseases) {
        this.diseases = diseases;
    }

    @Override
    public String toString() {
        return "MedicalHistory{" +
                "id=" + id +
                //", patient=" + patient +
                ", height=" + height +
                ", weight=" + weight +
                ", isSmoker=" + isSmoker +
                ", drinksAlcohol=" + drinksAlcohol +
                ", diseases='" + diseases + '\'' +
                '}';
    }
}
