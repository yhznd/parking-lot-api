package com.yunushaznedar.parking.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class VehicleAlreadyCheckedInException extends RuntimeException
{
    public VehicleAlreadyCheckedInException() {
    }
}