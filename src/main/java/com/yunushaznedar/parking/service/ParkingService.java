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

    @Transactional
    public Parking createParkingRecord(Vehicle vehicle)
    {
        Optional<Vehicle> comingVehicle = vehicleRepository.getVehicleByLicencePlate(vehicle.getLicencePlate());
        Parking parking = null;
        LocalDateTime checkinDate = java.time.LocalDateTime.now();

        if (!comingVehicle.isPresent())
        {
            vehicleRepository.save(vehicle);
        }
        else
        {
            vehicle = comingVehicle.get();
        }

        //Check if vehicle already in the park
        Parking oldPark = parkingRepository.findByVehicleLicencePlate(vehicle.getLicencePlate());
        if (oldPark != null)
        {
            LocalDateTime checkIn = oldPark.getStartDate();
            LocalDateTime checkOut = oldPark.getEndDate();

            if (checkIn != null && checkOut == null)
            {
                throw new VehicleAlreadyCheckedInException();
            }
        }

        //Vehicle is ready to check in
        ParkingLot parkingLot = findSuitableLot(vehicle);
        if (parkingLot != null)
        {
            parkingLot.setIsEmpty("F");
            parking = new Parking(parkingLot.getName(),vehicle.getLicencePlate(),checkinDate, null,0);

        }
        else
            throw new ParkingLotNotFoundException();

        return parkingRepository.save(parking);

    }

    @Transactional
    public Parking updateParkingRecord(int parkingId)
    {
        LocalDateTime checkOutDate = java.time.LocalDateTime.now();
        double unitPrice;
        Parking existingParking = parkingRepository.getParkingByParkingId(parkingId)
                                .orElseThrow(ParkingRecordNotFoundException::new);

        if(existingParking.getEndDate()!=null)
            throw new VehicleAlreadyCheckedOutException();

        ParkingLot parkingLot = parkingLotRepository.findPriceByName(existingParking.getParkingLot())
                .orElseThrow(PriceNotFoundException::new);

        unitPrice=parkingLot.getPrice();

        existingParking.setEndDate(checkOutDate);
        existingParking.setPrice(calculatePrice(existingParking.getStartDate(),checkOutDate,unitPrice));
        parkingLot.setIsEmpty("T");

        return parkingRepository.save(existingParking);

    }


    public ParkingLot findSuitableLot(Vehicle vehicle)
    {
        List<ParkingLot> parkingLotList = parkingLotRepository.findAllByHeightGreaterThanEqual(vehicle.getHeight());

        if (parkingLotList != null) {
            for (ParkingLot parkingLot : parkingLotList) {
                if (parkingLot.getWeight() - vehicle.getWeight() > 0 && parkingLot.getIsEmpty().equals("T")) {
                    return parkingLot;
                }
            }
        }
        return null;
    }

    public double calculatePrice(LocalDateTime checkInDate, LocalDateTime checkOutDate, double price)
    {
        int minutes = (int) ChronoUnit.MINUTES.between(checkInDate, checkOutDate);
        return minutes==0 ? price: minutes*price;
    }

}
