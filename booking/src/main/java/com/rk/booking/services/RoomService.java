package com.rk.booking.services;

import java.util.List;

import com.rk.booking.entities.Room;

public interface RoomService {
	
	Room create(Room room);
	List<Room> get();
	Room get(Long id);
	List<Room> getByName(String name);
	List<Room> getByNameOrCapacity(String nameOrCap);
	
}
