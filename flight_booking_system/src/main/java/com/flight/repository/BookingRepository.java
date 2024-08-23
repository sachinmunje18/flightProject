package com.flight.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.flight.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
