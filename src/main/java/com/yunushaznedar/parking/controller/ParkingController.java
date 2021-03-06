package com.yunushaznedar.parking.controller;


import com.yunushaznedar.parking.entitiy.Parking;
import com.yunushaznedar.parking.entitiy.Vehicle;
import com.yunushaznedar.parking.exceptionhandler.VehicleNotFoundException;
import com.yunushaznedar.parking.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/parking")
public class ParkingController
{

    @Autowired
    private ParkingService parkingService;

    @GetMapping("/")
    public Iterable<Parking> getAllParkingRecords()
    {
        return parkingService.getParkingList();
    }

    @GetMapping("/current")
    public List<Parking> getCurrentParkingRecords()
    {
        List<Parking> result = new ArrayList<>();
        parkingService.getParkingList().forEach(result::add);
        result=result.stream().filter(parking -> parking.getEndDate()==null).collect(Collectors.toList());
        return result;
    }

    @GetMapping("/get/{licence_plate}")
    public List<Parking> getCurrentParkingWithLicencePlate(@PathVariable(value = "licence_plate") String licencePlate)
    {
        List<Parking> result = new ArrayList<>();
        parkingService.getParkingList().forEach(result::add);
        result=result.stream().filter(parking -> parking.getVehicleLicencePlate().equals(licencePlate)).collect(Collectors.toList());

        if(result.size()==0)
            throw new VehicleNotFoundException();
        else
            return result;
    }


    @PostMapping("/checkin")
    public ResponseEntity<Parking> checkInToParkingLot(@RequestBody @Valid Vehicle vehicle)
    {
        return new ResponseEntity<>(parkingService.createParkingRecord(vehicle), HttpStatus.CREATED);
    }

    @PutMapping("/checkout/{id}")
    public ResponseEntity<Parking> checkOutFromParkingLot(@PathVariable int id)
    {
        return new ResponseEntity<>(parkingService.updateParkingRecord(id), HttpStatus.OK);
    }
}
