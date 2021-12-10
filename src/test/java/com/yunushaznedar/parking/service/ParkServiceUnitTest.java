package com.yunushaznedar.parking.service;


import com.yunushaznedar.parking.entitiy.ParkingLot;
import com.yunushaznedar.parking.entitiy.Vehicle;
import com.yunushaznedar.parking.repository.ParkingLotRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ParkServiceUnitTest
{

    @Mock
    private static ParkingLotRepository parkingLotRepository;

    @InjectMocks
    private static ParkingService parkingService;

    private static LocalDateTime checkInTime;
    private static LocalDateTime checkOutTime;


    @BeforeAll
    public static void init()
    {
       checkInTime = LocalDateTime.of(2021, Month.DECEMBER, 10, 10, 30,44); // "2021-12-10 10:30:44"
       checkOutTime = LocalDateTime.of(2021, Month.DECEMBER, 11, 23, 42,11); // "2021-12-11 23:42:11"
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.3,0.5,1.2})
    @DisplayName("Calculate Price Service Test")
    public void calculatePriceTest(double unitPrice)
    {
        int seconds = (int) ChronoUnit.SECONDS.between(checkInTime, checkOutTime);
        assertEquals(seconds*unitPrice,parkingService.calculatePrice(checkInTime,checkOutTime,unitPrice));
    }

    @Test
    @DisplayName("Find Suitable Lot Service Test")
    public void testSuitableLot()
    {
        Vehicle vehicle=new Vehicle(1,1.7,100.4,"34DMD45");
        ParkingLot parkingLotC1=new ParkingLot(1,"C1","C",3.2,400,0.6,"T");
        ParkingLot parkingLotC2=new ParkingLot(2,"C2","C",2,100,0.4,"T");

        List<ParkingLot> parkingLotList = Arrays.asList(parkingLotC1,parkingLotC2);
        when(parkingLotRepository.findAllByHeightGreaterThanEqual(vehicle.getHeight())).thenReturn(parkingLotList);

        assertEquals(parkingLotC1.getName(),parkingService.findSuitableLot(vehicle).getName());

    }



}
