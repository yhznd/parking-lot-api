package com.yunushaznedar.parking.service.impl;

import com.yunushaznedar.parking.entitiy.Parking;
import com.yunushaznedar.parking.entitiy.ParkingLot;
import com.yunushaznedar.parking.entitiy.Vehicle;
import com.yunushaznedar.parking.repository.ParkingLotRepository;
import com.yunushaznedar.parking.repository.ParkingRepository;
import com.yunushaznedar.parking.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingService
{
    @Autowired
    private ParkingRepository parkingRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    public Iterable<Parking> getParkingList()
    {
        return parkingRepository.findAll();
    }


    public Parking createParkingRecord(Vehicle vehicle, Date checkInDate)
    {
        Optional<Vehicle> comingVehicle=vehicleRepository.getVehicleByLicencePlate(vehicle.getLicencePlate());
        Parking parking= null;

        if(!comingVehicle.isPresent())
        {
            vehicleRepository.save(vehicle);
        }
        else
        {
            vehicle = comingVehicle.get();
        }

        //Check if vehicle already in the park
        Parking oldPark=parkingRepository.findByVehicleLicencePlate(vehicle.getLicencePlate());
        if(oldPark != null)
        {
            Date checkIn = oldPark.getStartDate();
            Date checkOut = oldPark.getEndDate();

            if(checkIn != null && checkOut == null)
            {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vehicle is already checked-in.");
            }
        }

        //Vehicle is ready to check in
        ParkingLot parkingLot=findSuitableLot(vehicle);
        if(parkingLot!=null)
        {
            parking=new Parking(parkingLot.getName(),vehicle.getLicencePlate(),checkInDate,null,parkingLot.getPrice());
            parkingLot.addVehicleToLot(vehicle.getWeight());

        }
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle is not suitable for any parking lot.");


         return parkingRepository.save(parking);

        }

        public ParkingLot findSuitableLot(Vehicle vehicle)
        {
            List<ParkingLot> parkingLotList = parkingLotRepository.findAllByHeightLessThanEqual(vehicle.getHeight());

            if (parkingLotList != null)
            {
                for (ParkingLot parkingLot : parkingLotList)
                {
                    if (parkingLot.getWeight() - vehicle.getWeight() > 0)
                    {
                        return parkingLot;
                    }
                    break;
                }
            }
            return null;
        }



}
