package com.parking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parking.model.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, String> {

    List<Vehicle> findByParkedTrue();
}