package com.yunushaznedar.parking.repository;

import com.yunushaznedar.parking.entitiy.Vehicle;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VehicleRepository extends CrudRepository<Vehicle, Integer>
{

     Optional<Vehicle> getVehicleByLicencePlate(String licencePlate);
}
