package com.yunushaznedar.parking.repository;

import com.yunushaznedar.parking.entitiy.Parking;
import com.yunushaznedar.parking.entitiy.ParkingLot;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ParkingLotRepository extends CrudRepository<ParkingLot,Integer>
{
    List<ParkingLot> findAllByHeightGreaterThanEqual(double vehicleHeight);

    Optional<ParkingLot> findPriceByName(String lotName);


}
