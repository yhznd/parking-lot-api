package com.yunushaznedar.parking.entitiy;


import javax.persistence.*;

@Entity
@Table(name = "PARKING_LOTS")
public class ParkingLot
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "LOT_NAME")
    private String name;

    @Column(name = "FLOOR")
    private String floor;

    @Column(name = "LOT_HEIGHT")
    private double height;

    @Column(name = "LOT_WEIGHT")
    private double weight;

    @Column(name = "LOT_PRICE")
    private double price;

    @Column(name = "IS_EMPTY")
    private String isEmpty;


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

    public String getIsEmpty() {
        return isEmpty;
    }

    public void setIsEmpty(String isEmpty) {
        this.isEmpty = isEmpty;
    }

    public ParkingLot(String name, String floor, double height, double weight, double price, String isEmpty) {
        this.name = name;
        this.floor = floor;
        this.height = height;
        this.weight = weight;
        this.price = price;
        this.isEmpty = isEmpty;
    }
}

