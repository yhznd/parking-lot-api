package com.yunushaznedar.parking.controller;


import com.yunushaznedar.parking.entitiy.ParkingLot;
import com.yunushaznedar.parking.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/parking-lot")
public class ParkingLotController
{

    @Autowired
    private ParkingLotService parkingLotService;

    @GetMapping("/")
    public Iterable<ParkingLot> getAllParkingLots()
    {
        return parkingLotService.getParkingLots();
    }


    @PostMapping("/create")
    public ParkingLot createParkingLot(@RequestBody ParkingLot parkingLot)
    {
        return parkingLotService.createParkingLot(parkingLot);
    }
}
