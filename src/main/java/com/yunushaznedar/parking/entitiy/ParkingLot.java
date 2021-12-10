package com.yunushaznedar.parking.entitiy;


import com.yunushaznedar.parking.service.IParkingLotService;

import javax.persistence.*;

@Entity
@Table(name = "PARKING_LOTS")
public class ParkingLot implements IParkingLotService
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int lotId;

    @Column(name = "LOT_NAME")
    private String name;

    @Column(name = "LOT_HEIGHT")
    private double height;

    @Column(name = "LOT_WEIGHT")
    private double weight;

    @Column(name = "LOT_PRICE")
    private double price;


    public ParkingLot() {

    }

    public ParkingLot(String name, double height, double weight, double price) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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


    @Override
    public void addVehicleToLot(double vehicleWeight)
    {
        setWeight(getWeight()-vehicleWeight);
    }

    @Override
    public void removeVehicleToLot(double vehicleWeight)
    {
        setWeight(getWeight()+vehicleWeight);
    }
}

