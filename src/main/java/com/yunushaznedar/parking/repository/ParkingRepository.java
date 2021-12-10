package com.yunushaznedar.parking.repository;

import com.yunushaznedar.parking.entitiy.Parking;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ParkingRepository extends CrudRepository<Parking,Integer>
{
    Parking findByVehicleLicencePlate(String vehicleLicencePlate);
}
