package com.yunushaznedar.parking.entitiy;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "PARKING_LOTS")
public class ParkingLot
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank(message = "Lot name cannot be null")
    @Column(name = "LOT_NAME")
    private String name;

    @NotBlank(message = "Lot floor cannot be null")
    @Column(name = "FLOOR")
    private String floor;

    @Column(name = "LOT_HEIGHT")
    private double height;

    @Column(name = "LOT_WEIGHT")
    private double weight;

    @Column(name = "LOT_PRICE")
    private double price;

    @NotNull(message = "Lot empty status cannot be null")
    @Column(name = "IS_EMPTY")
    private boolean isEmpty;


    public ParkingLot() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    public ParkingLot(int id, @NotBlank(message = "Lot name cannot be null") String name, @NotBlank(message = "Lot floor cannot be null") String floor, double height, double weight, double price, @NotNull(message = "Lot empty status cannot be null") boolean isEmpty) {
        this.id = id;
        this.name = name;
        this.floor = floor;
        this.height = height;
        this.weight = weight;
        this.price = price;
        this.isEmpty = isEmpty;
    }
}

