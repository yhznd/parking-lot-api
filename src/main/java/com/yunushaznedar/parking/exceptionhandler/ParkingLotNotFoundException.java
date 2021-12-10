package com.yunushaznedar.parking.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ParkingLotNotFoundException extends RuntimeException
{
    public ParkingLotNotFoundException() {
    }
}