package com.yunushaznedar.parking.service;

import com.yunushaznedar.parking.entitiy.ParkingLot;
import com.yunushaznedar.parking.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class ParkingLotService
{
    @Autowired
    private ParkingLotRepository parkingLotRepository;

    public Iterable<ParkingLot> getParkingLots() {
        return parkingLotRepository.findAll();
    }

    public ParkingLot createParkingLot(@RequestBody ParkingLot parkingLot)
    {
        return parkingLotRepository.save(parkingLot);
    }


}
