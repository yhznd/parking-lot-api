package com.yunushaznedar.parking.service;

import com.yunushaznedar.parking.entitiy.ParkingLot;
import com.yunushaznedar.parking.repository.ParkingLotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class ParkingLotService {
    @Autowired
    private ParkingLotRepository parkingLotRepository;

    private static Logger logger = LoggerFactory.getLogger(ParkingService.class);

    public Iterable<ParkingLot> getParkingLots() {
        return parkingLotRepository.findAll();
    }

    public ParkingLot createParkingLot(@RequestBody ParkingLot parkingLot) {
        logger.debug("Parking lot created " + parkingLot.getName());
        return parkingLotRepository.save(parkingLot);
    }

}
