package com.flight.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.flight.model.Booking;
import com.flight.service.BookingService;
import com.flight.vo.BookingVO;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<String> createBooking(@RequestBody Booking booking) {
        try {
            bookingService.saveBooking(booking);
            return ResponseEntity.ok("Booking successful!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to book the flight. Please try again.");
        }
    }

    @PostMapping("/updateBooking")
    public ResponseEntity<String> updateBooking(@RequestBody BookingVO bookingVO) {
        try {
            Booking existingBooking = bookingService.getBookingById(bookingVO.getId());
            if (existingBooking != null) {
                // Update only specific fields
                if (bookingVO.getName() != null) {
                    existingBooking.setName(bookingVO.getName());
                }
                if (bookingVO.getPhone() != null) {
                    existingBooking.setPhone(bookingVO.getPhone());
                }
                if (bookingVO.getAddress() != null) {
                    existingBooking.setAddress(bookingVO.getAddress());
                }
                bookingService.saveBooking(existingBooking);
                return ResponseEntity.ok("Booking updated successfully!");
            } else {
                return ResponseEntity.status(404).body("Booking not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to update booking. Please try again.");
        }
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<String> cancelBooking(@PathVariable("id") Long id) {
        try {
            Booking existingBooking = bookingService.getBookingById(id);
            if (existingBooking != null) {
                bookingService.deleteBooking(id);
                return ResponseEntity.ok("Booking canceled successfully!");
            } else {
                return ResponseEntity.status(404).body("Booking not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to cancel booking. Please try again.");
        }
    }
}
