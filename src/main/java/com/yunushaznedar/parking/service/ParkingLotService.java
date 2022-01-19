package com.yunushaznedar.parking.service;

import com.yunushaznedar.parking.entitiy.ParkingLot;
import com.yunushaznedar.parking.entitiy.Vehicle;
import com.yunushaznedar.parking.exceptionhandler.PriceNotFoundException;
import com.yunushaznedar.parking.repository.ParkingLotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

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

    public ParkingLot findPriceByName(String lotName)
    {
        Optional<ParkingLot> parkingLot=parkingLotRepository.findPriceByName(lotName);
        return parkingLot.orElseThrow(PriceNotFoundException::new);
    }

    public List<ParkingLot> findAllByHeightGreaterThanEqual(Vehicle vehicle)
    {
        return parkingLotRepository.findAllByHeightGreaterThanEqual(vehicle.getHeight());
    }

}
