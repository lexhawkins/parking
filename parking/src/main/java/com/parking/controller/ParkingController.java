package com.parking.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parking.model.Vehicle;
import com.parking.service.ParkingService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ParkingController {

    private final ParkingService parkingService;

    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @PostMapping("/vehicles/park")
    public Vehicle parkVehicle(@RequestBody Map<String, String> body) {
        String vehicleId = body.get("vehicleId");
        return parkingService.parkVehicle(vehicleId);
    }

    @PostMapping("/vehicles/remove")
    public Vehicle removeVehicle(@RequestBody Map<String, String> body) {
        String vehicleId = body.get("vehicleId");
        return parkingService.removeVehicle(vehicleId);
    }

    @GetMapping("/spots")
    public Map<String, Integer> getAvailableSpots() {
        return Map.of("availableSpots", parkingService.getAvailableSpots());
    }

    @GetMapping("/vehicles")
    public List<Vehicle> getVehicles() {
        return parkingService.getParkedVehicles();
    }
}