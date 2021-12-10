# Parking Lot API

# Tech Stack

- Java 8
- Spring Boot v2.6.1
- JUnit
- H2 Database

# Introduction

When API starts, it will be inserted 9 parking lots into database automatically. You can remove this
action from application properties.

In this API, there are 3 main services:

## Vehicle service:

- You can get the vehicles list that inserted. **GET api/v1/vehicles/**
- You can create a vehicle. **POST api/v1/vehicles/create**

## Parking Lot service:

- You can get list of all parking lots. **GET api/v1/api/v1/parking_lot/**
- You can get list of empty parking lots. **GET api/v1/api/v1/parking_lot/empty_lots**
- You can get list of filled parking lots. **GET api/v1/api/v1/parking_lot/filled_lots**
- You can create a parking lot. **POST api/v1/api/v1/parking_lot/create**

## Parking service:

- You can get all parking lots list. **GET api/v1/api/v1/parking/**
- You can get current parking list. **GET api/v1/api/v1/parking/current**
- You can search the parking history record with licence plate. **GET api/v1/api/v1/parking_lot/get/{licence_plate}**
- You can check-in to parking. The pricing will be starting with current time. **POST api/v1/api/v1/checkin/**
- You can check-out from parking with parking ID. **PUT api/v1/api/v1/checkout/{id}/**


*Note: The pricing will be calculated by multiplying the seconds of the vehicle stays in the parking lot and the unit price of the parking lot.*