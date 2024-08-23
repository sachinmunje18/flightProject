package com.flight.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.flight.model.Booking;
import com.flight.service.BookingService;
import com.flight.vo.BookingVO;

import java.util.List;

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

    @PutMapping("/{id}")
    public ResponseEntity<String> updateBooking(@PathVariable("id") Long id, @RequestBody BookingVO bookingVO) {
        try {
            Booking existingBooking = bookingService.getBookingById(id);
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
                bookingService.updateBooking(existingBooking);
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

    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        try {
            List<Booking> bookings = bookingService.getAllBookings();
            return ResponseEntity.ok(bookings);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable("id") Long id) {
        try {
            Booking booking = bookingService.getBookingById(id);
            if (booking != null) {
                return ResponseEntity.ok(booking);
            } else {
                return ResponseEntity.status(404).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
