package com.yunushaznedar.parking.service;


import com.yunushaznedar.parking.entitiy.Parking;
import com.yunushaznedar.parking.entitiy.ParkingLot;
import com.yunushaznedar.parking.entitiy.Vehicle;
import com.yunushaznedar.parking.exceptionhandler.ParkingLotNotFoundException;
import com.yunushaznedar.parking.repository.ParkingLotRepository;
import com.yunushaznedar.parking.repository.ParkingRepository;
import com.yunushaznedar.parking.repository.VehicleRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ParkServiceTest
{

    @Mock
    private static ParkingLotRepository parkingLotRepository;

    @InjectMocks
    private static ParkingService parkingService;

    private static LocalDateTime checkInTime;
    private static LocalDateTime checkOutTime;

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this); //without this you will get NPE
    }

    @BeforeAll
    public static void init()
    {
       checkInTime = LocalDateTime.of(2021, Month.DECEMBER, 10, 10, 30,44); // "2021-12-10 10:30:44"
       checkOutTime = LocalDateTime.of(2021, Month.DECEMBER, 11, 23, 42,11); // "2021-12-11 23:42:11"
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.3,0.5,1.2})
    @DisplayName("Calculate Price Test")
    public void calculatePriceTest(double unitPrice)
    {
        int minutes = (int) ChronoUnit.MINUTES.between(checkInTime, checkOutTime);
        assertEquals(minutes*unitPrice,parkingService.calculatePrice(checkInTime,checkOutTime,unitPrice));
    }



}
