package com.yunushaznedar.parking.controller;


import com.yunushaznedar.parking.entitiy.Parking;
import com.yunushaznedar.parking.entitiy.ParkingLot;
import com.yunushaznedar.parking.entitiy.Vehicle;
import com.yunushaznedar.parking.service.impl.ParkingLotService;
import com.yunushaznedar.parking.service.impl.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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


    @PostMapping("/create")
    public Parking createParkingLot(@RequestBody Vehicle vehicle,
                                    @RequestParam(value = "start_date") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date checkInDate)
    {
        return parkingService.createParkingRecord(vehicle,checkInDate);
    }
}
