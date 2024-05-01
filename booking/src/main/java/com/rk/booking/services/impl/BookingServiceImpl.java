package com.rk.booking.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rk.booking.entities.Booking;
import com.rk.booking.repositories.BookingRepository;
import com.rk.booking.services.BookingService;

@Service
public class BookingServiceImpl implements BookingService {
	
	@Autowired
	private BookingRepository bookingRepository;
	
	@Override
	public List<Booking> get() {
		return bookingRepository.findAll();
	}
	
	@Override
	public Booking getBookingsByRoomId(String roomId) {
		return bookingRepository.findByRoomId(roomId);
	}
	
	@Override
	public String bookRoom(Booking booking) {
		List<String> alreadyExistList = new ArrayList<>();
		
		Booking roomBookingDetails = getBookingsByRoomId(booking.getRoomId());
		
		if(roomBookingDetails == null) {
			bookingRepository.save(booking);
			return "Success";
		}
		
		for(String dateTime : booking.getBookedSlots()) {
			if(roomBookingDetails.getBookedSlots().contains(dateTime)) {
				alreadyExistList.add(dateTime);
			}else {
				roomBookingDetails.getBookedSlots().add(dateTime);
			}
		}
		if(alreadyExistList.size()>0) {
//			throw new RuntimeException(alreadyExistList.toString()+" Time Slots are already booked. Please choose another one.");
//			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,alreadyExistList.toString()+" Time Slots are already booked. Please choose another one.");
			return alreadyExistList.toString().replace("[", "").replace("]", "")+" Time Slots are already booked. Please choose another one.";
		}
		
		bookingRepository.save(roomBookingDetails);
		
		return "Success";
	}
	
}
