package com.yunushaznedar.parking.controller;


import com.yunushaznedar.parking.entitiy.ParkingLot;
import com.yunushaznedar.parking.entitiy.Vehicle;
import com.yunushaznedar.parking.service.impl.ParkingLotService;
import com.yunushaznedar.parking.service.impl.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleController
{

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/")
    public Iterable<Vehicle> getAllParkingLots()
    {
        return vehicleService.getVehicles();
    }


    @PostMapping("/create")
    public Vehicle createParkingLot(@RequestBody Vehicle vehicle)
    {
        return vehicleService.createVehicle(vehicle);
    }
}
