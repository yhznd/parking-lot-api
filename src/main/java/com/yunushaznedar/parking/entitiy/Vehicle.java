package com.yunushaznedar.parking.entitiy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "VEHICLES")
public class Vehicle
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "HEIGHT")
    private double height;

    @Column(name = "WEIGHT")
    private double weight;

    @NotBlank(message = "Licence plate cannot be null")
    @Column(name = "LICENSE_PLATE")
    private String licencePlate;

    public Vehicle()
    {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    public Vehicle(int id, double height, double weight, @NotBlank(message = "Licence plate cannot be null") String licencePlate) {
        this.id = id;
        this.height = height;
        this.weight = weight;
        this.licencePlate = licencePlate;
    }
}
