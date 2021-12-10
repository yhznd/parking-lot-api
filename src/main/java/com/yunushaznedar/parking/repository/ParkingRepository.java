package com.yunushaznedar.parking.repository;

import com.yunushaznedar.parking.entitiy.Parking;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ParkingRepository extends CrudRepository<Parking,Integer>
{
    List<Parking> findByVehicleLicencePlate(String vehicleLicencePlate);

    Optional<Parking> getParkingByParkingId(int parkingId);
}
