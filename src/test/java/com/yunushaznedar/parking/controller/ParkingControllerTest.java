package com.yunushaznedar.parking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yunushaznedar.parking.entitiy.ParkingLot;
import com.yunushaznedar.parking.entitiy.Vehicle;
import com.yunushaznedar.parking.service.ParkingService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


@RunWith(SpringRunner.class)
@WebMvcTest(ParkingController.class)
@AutoConfigureMockMvc
public class ParkingControllerTest
{

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ParkingService parkingService;

    @InjectMocks
    private ParkingController parkingController;

    @Test
    @DisplayName("Test Check-In")
    public void checkIn() throws Exception
    {
        Vehicle vehicle=new Vehicle(2,2.2,100.8,"34 YH 1024");
        ParkingLot testParkingLot=new ParkingLot("C1","C",4.0,500.0,0.7,"F");
        when(parkingService.findSuitableLot(vehicle)).thenReturn(testParkingLot);
        //Then
        String body = objectMapper.writeValueAsString(vehicle);
        mockMvc.perform(post("/api/v1/parking/checkin")
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Check-In Fail Test")
    public void checkInFail() throws Exception
    {
        Vehicle vehicle=new Vehicle(2,2.2,100.8,"34 YH 1024");
        ParkingLot testParkingLot=new ParkingLot("C1","C",2.0,500.0,0.7,"F");
        when(parkingService.findSuitableLot(vehicle)).thenReturn(testParkingLot);
        //Then
        String body = objectMapper.writeValueAsString(vehicle);
        mockMvc.perform(post("/api/v1/parking/checkin")
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isBadRequest());
    }

}
