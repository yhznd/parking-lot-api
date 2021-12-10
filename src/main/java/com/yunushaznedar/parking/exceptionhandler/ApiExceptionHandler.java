package com.yunushaznedar.parking.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({ParkingLotNotFoundException.class})
    public ResponseEntity<String> parkingLotNotFoundException()
    {
        return new ResponseEntity<>("Vehicle is not suitable for any parking lot.", HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler({ParkingRecordNotFoundException.class})
    public ResponseEntity<String> parkingRecordNotFoundException()
    {
        return new ResponseEntity<>("Parking record not found.", HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler({PriceNotFoundException.class})
    public ResponseEntity<String> priceNotFoundException()
    {
        return new ResponseEntity<>("Price not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({VehicleAlreadyCheckedOutException.class})
    public ResponseEntity<String> alreadyCheckedOutException()
    {
        return new ResponseEntity<>("Vehicle already checked-out", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({VehicleAlreadyCheckedInException.class})
    public ResponseEntity<String> vehicleAlreadyCheckedInException()
    {
        return new ResponseEntity<>("Vehicle is already checked-in.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({VehicleNotFoundException.class})
    public ResponseEntity<String> vehicleNotFoundException()
    {
        return new ResponseEntity<>("Vehicle not found.", HttpStatus.NOT_FOUND);
    }
}