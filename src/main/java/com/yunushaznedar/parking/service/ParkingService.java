package com.yunushaznedar.parking.service;

import com.yunushaznedar.parking.entitiy.Parking;
import com.yunushaznedar.parking.entitiy.ParkingLot;
import com.yunushaznedar.parking.entitiy.Vehicle;
import com.yunushaznedar.parking.exceptionhandler.*;
import com.yunushaznedar.parking.repository.ParkingRepository;
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
public class ParkingService
{
    @Autowired
    private ParkingRepository parkingRepository;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private ParkingLotService parkingLotService;

    public Iterable<Parking> getParkingList() {
        return parkingRepository.findAll();
    }

    Logger logger = LoggerFactory.getLogger(ParkingService.class);


    @Transactional
    public Parking createParkingRecord(Vehicle comingVehicle)
    {
        Vehicle vehicle=checkIfVehicleExists(comingVehicle);
        checkIfVehicleAlreadyParked(vehicle);

        ParkingLot parkingLot = findSuitableLot(vehicle);

        if (parkingLot != null) {
            parkingLot.setEmpty(false);
            return parkingRepository.save(new Parking(parkingLot.getName(), vehicle.getLicencePlate(), java.time.LocalDateTime.now(), null, 0));

        } else {
            throw new ParkingLotNotFoundException();
        }

    }

    @Transactional
    public Parking updateParkingRecord(int parkingId)
    {
        LocalDateTime checkOutDate = java.time.LocalDateTime.now();
        double unitPrice=0;

        logger.debug("Checking existing parking records, if not found throw an exception");
        Parking existingParking = parkingRepository.getParkingByParkingId(parkingId)
                .orElseThrow(ParkingRecordNotFoundException::new);

        logger.debug("No record exists checking end date.");
        if (existingParking.getEndDate() != null)
            throw new VehicleAlreadyCheckedOutException();

        logger.debug("Find price for the lot");
        ParkingLot parkingLot = parkingLotService.findPriceByName(existingParking.getParkingLot());


        logger.debug("Calculating price for the lot");
        unitPrice = parkingLot.getPrice();

        logger.debug("Setting end date and price for record, setting lot as not empty");
        existingParking.setEndDate(checkOutDate);
        existingParking.setPrice(calculatePrice(existingParking.getStartDate(), checkOutDate, unitPrice));
        parkingLot.setEmpty(true);

        return parkingRepository.save(existingParking);

    }


    public ParkingLot findSuitableLot(Vehicle vehicle) {

        List<ParkingLot> parkingLotList = parkingLotService.findAllByHeightGreaterThanEqual(vehicle);
        logger.debug("Getting suitable lot lists with looking height first.");

        if (parkingLotList != null) {
            for (ParkingLot parkingLot : parkingLotList) {
                if (parkingLot.getWeight() - vehicle.getWeight() > 0 && parkingLot.isEmpty())
                {
                    logger.debug("Found a suitable lot " + parkingLot.getName());
                    return parkingLot;
                }
            }
        }
        logger.debug("Could not found a suitable lot.");
        return null;
    }

    public double calculatePrice(LocalDateTime checkInDate, LocalDateTime checkOutDate, double price) {
        int seconds = (int) ChronoUnit.SECONDS.between(checkInDate, checkOutDate);
        logger.debug("Price is calculated for " + seconds);

        return seconds * price;
    }

    public Vehicle checkIfVehicleExists(Vehicle vehicle)
    {
        Optional<Vehicle> comingVehicle = vehicleService.findVehicleByPlate(vehicle.getLicencePlate());

        if (comingVehicle.isPresent())
        {
            vehicle = comingVehicle.get();
            logger.debug("Vehicle " + vehicle.getId() + "exists.");
            return vehicle;
        }
        else{
            logger.debug("Vehicle is not exists. Creating a new vehicle.");
            return vehicleService.createVehicle(vehicle);
        }
    }

    public void checkIfVehicleAlreadyParked(Vehicle vehicle)
    {
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
    }


}
