package com.rk.booking.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.rk.booking.entities.Booking;

public interface BookingRepository extends MongoRepository<Booking, Long> {

	Booking findByRoomId(String roomId);

}
