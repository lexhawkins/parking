package com.parking.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.parking.model.Vehicle;
import com.parking.repository.VehicleRepository;

@Service
public class ParkingService {

    private final VehicleRepository vehicleRepository;

    private final double RATE = 2.5;
    private final int MAX_CAPACITY = 10;

    public ParkingService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Vehicle parkVehicle(String vehicleId) {

        long parkedCount = vehicleRepository.findByParkedTrue().size();

        if (parkedCount >= MAX_CAPACITY) {
            throw new RuntimeException("Parking lot full");
        }

        Vehicle vehicle = new Vehicle(vehicleId, true, LocalDateTime.now());

        return vehicleRepository.save(vehicle);
    }

    public Vehicle removeVehicle(String vehicleId) {

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        if (!vehicle.isParked()) {
            throw new RuntimeException("Vehicle is not parked");
        }

        LocalDateTime exitTime = LocalDateTime.now();

        vehicle.setExitTime(exitTime);
        vehicle.setParked(false);

        Duration duration = Duration.between(vehicle.getEntryTime(), exitTime);

        double hours = duration.toMinutes() / 60.0;

        double fee = hours * RATE;

        vehicle.setParkingFee(fee);

        return vehicleRepository.save(vehicle);
    }

    public int getAvailableSpots() {

        int occupied = vehicleRepository.findByParkedTrue().size();

        return MAX_CAPACITY - occupied;
    }

    public List<Vehicle> getParkedVehicles() {

        return vehicleRepository.findByParkedTrue();
    }
}