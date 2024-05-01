package com.rk.booking.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.rk.booking.entities.Room;

public interface RoomRepository extends MongoRepository<Room, Long> {
	
	List<Room> findAll();

	List<Room> findByName(String name);

	List<Room> findByNameOrCapacity(String nameOrCap, String nameOrCap2);

}
