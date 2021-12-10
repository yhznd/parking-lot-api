package com.yunushaznedar.parking.service.impl;

import com.yunushaznedar.parking.entitiy.Vehicle;
import com.yunushaznedar.parking.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;


    public Iterable<Vehicle> getVehicles() {
        return vehicleRepository.findAll();
    }

    public Vehicle createVehicle(@RequestBody Vehicle vehicle)
    {
        return vehicleRepository.save(vehicle);
    }

}
