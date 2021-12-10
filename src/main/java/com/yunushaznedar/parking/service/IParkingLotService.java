package com.yunushaznedar.parking.service;

import java.util.Date;

public interface IParkingLotService
{

    void addVehicleToLot(double vehicleWeight);

    void removeVehicleToLot(double vehicleWeight);

    default long timeDifferenceInHours(Date date1, Date date2)
    {
        return (date2.getTime() - date1.getTime()) / (60 * 60 * 1000);
    }

}
