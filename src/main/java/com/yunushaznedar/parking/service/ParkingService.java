package com.yunushaznedar.parking.service;

import com.yunushaznedar.parking.entitiy.Parking;
import com.yunushaznedar.parking.entitiy.ParkingLot;
import com.yunushaznedar.parking.entitiy.Vehicle;
import com.yunushaznedar.parking.exceptionhandler.*;
import com.yunushaznedar.parking.repository.ParkingLotRepository;
import com.yunushaznedar.parking.repository.ParkingRepository;
import com.yunushaznedar.parking.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingService {
    @Autowired
    private ParkingRepository parkingRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    public Iterable<Parking> getParkingList() {
        return parkingRepository.findAll();
    }

    Logger logger = LoggerFactory.getLogger(ParkingService.class);


    @Transactional
    public Parking createParkingRecord(Vehicle vehicle) {
        Optional<Vehicle> comingVehicle = vehicleRepository.getVehicleByLicencePlate(vehicle.getLicencePlate());
        Parking parking = null;
        LocalDateTime checkinDate = java.time.LocalDateTime.now();

        if (!comingVehicle.isPresent()) {
            vehicleRepository.save(vehicle);
            logger.debug("Vehicle did not exist. Vehicle " + vehicle.getId() + "is created.");
        } else {
            vehicle = comingVehicle.get();
            logger.debug("Vehicle " + vehicle.getId() + "exists.");
        }

        //Check if vehicle already in the park
        List<Parking> oldPark= parkingRepository.findByVehicleLicencePlate(vehicle.getLicencePlate());
        if (oldPark.size()>=1)
        {
            for(Parking parking1:oldPark)
            {
                LocalDateTime checkIn = parking1.getStartDate();
                LocalDateTime checkOut = parking1.getEndDate();

                if (checkIn != null && checkOut == null)
                {
                    logger.debug("Vehicle already checked-in");
                    throw new VehicleAlreadyCheckedInException();
                }
            }
        }

        //Vehicle is ready to check in
        ParkingLot parkingLot = findSuitableLot(vehicle);
        if (parkingLot != null) {
            parkingLot.setIsEmpty("F");
            parking = new Parking(parkingLot.getName(), vehicle.getLicencePlate(), checkinDate, null, 0);

        } else {
            logger.debug("Parking Lot not found.");
            throw new ParkingLotNotFoundException();
        }

        return parkingRepository.save(parking);

    }

    @Transactional
    public Parking updateParkingRecord(int parkingId)
    {
        LocalDateTime checkOutDate = java.time.LocalDateTime.now();
        double unitPrice;

        logger.debug("Checking existing parking records, if not found throw an exception");
        Parking existingParking = parkingRepository.getParkingByParkingId(parkingId)
                .orElseThrow(ParkingRecordNotFoundException::new);

        logger.debug("No record exists checking end date.");
        if (existingParking.getEndDate() != null)
            throw new VehicleAlreadyCheckedOutException();

        logger.debug("Find price for the lot");
        ParkingLot parkingLot = parkingLotRepository.findPriceByName(existingParking.getParkingLot())
                .orElseThrow(PriceNotFoundException::new);

        logger.debug("Calculating price for the lot");
        unitPrice = parkingLot.getPrice();

        logger.debug("Setting end date and price for record, setting lot as not empty");
        existingParking.setEndDate(checkOutDate);
        existingParking.setPrice(calculatePrice(existingParking.getStartDate(), checkOutDate, unitPrice));
        parkingLot.setIsEmpty("T");

        return parkingRepository.save(existingParking);

    }


    public ParkingLot findSuitableLot(Vehicle vehicle) {

        List<ParkingLot> parkingLotList = parkingLotRepository.findAllByHeightGreaterThanEqual(vehicle.getHeight());
        logger.debug("Getting suitable lot lists with looking height first.");

        if (parkingLotList != null) {
            for (ParkingLot parkingLot : parkingLotList) {
                if (parkingLot.getWeight() - vehicle.getWeight() > 0 && parkingLot.getIsEmpty().equals("T")) {
                    logger.debug("Found a suitable spot " + parkingLot.getName());
                    return parkingLot;
                }
            }
        }
        logger.debug("Not found a suitable spot");
        return null;
    }

    public double calculatePrice(LocalDateTime checkInDate, LocalDateTime checkOutDate, double price) {
        int seconds = (int) ChronoUnit.SECONDS.between(checkInDate, checkOutDate);
        logger.debug("Price is calculated for " + seconds);

        return seconds * price;
    }

}
