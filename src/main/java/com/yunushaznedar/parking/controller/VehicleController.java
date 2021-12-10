package com.yunushaznedar.parking.controller;


import com.yunushaznedar.parking.entitiy.Vehicle;
import com.yunushaznedar.parking.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/")
    public Iterable<Vehicle> getAllParkingLots() {
        return vehicleService.getVehicles();
    }


    @PostMapping("/create")
    public ResponseEntity<Vehicle> createParkingLot(@RequestBody @Valid Vehicle vehicle) {
        return new ResponseEntity<>(vehicleService.createVehicle(vehicle), HttpStatus.CREATED);
    }
}
