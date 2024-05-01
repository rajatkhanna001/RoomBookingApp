package com.rk.booking.services;

import java.util.List;

import com.rk.booking.entities.Booking;

import jakarta.websocket.Session;

public interface BookingService {
	
	List<Booking> get();
	Booking getBookingsByRoomId(String roomId);
	String bookRoom(Booking booking);
	
}
