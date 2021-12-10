package com.yunushaznedar.parking.entitiy;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "PARKING_RECORDS")
public class Parking
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int parkingId;

    @Column(name = "PARKING_LOT")
    private String parkingLot;

    @Column(name = "VEHICLE_LICENCE_PLATE")
    private String vehicleLicencePlate;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Column(name = "END_DATE")
    private LocalDateTime endDate;

    @Column(name = "PRICE")
    private double price;

    public Parking() {

    }

    public int getParkingId() {
        return parkingId;
    }

    public void setParkingId(int parkingId) {
        this.parkingId = parkingId;
    }

    public String getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(String parkingLot) {
        this.parkingLot = parkingLot;
    }

    public String getVehicleLicencePlate() {
        return vehicleLicencePlate;
    }

    public void setVehicleLicencePlate(String vehicleLicencePlate) {
        this.vehicleLicencePlate = vehicleLicencePlate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Parking(String parkingLot, String vehicleLicencePlate, LocalDateTime startDate, LocalDateTime endDate, double price) {
        this.parkingLot = parkingLot;
        this.vehicleLicencePlate = vehicleLicencePlate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }
}
