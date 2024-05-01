package com.rk.booking.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rk.booking.entities.Room;
import com.rk.booking.repositories.RoomRepository;
import com.rk.booking.services.RoomService;

@Service
public class RoomServiceImpl implements RoomService {

	@Autowired
	private RoomRepository roomRepository;
	
	public RoomServiceImpl(RoomRepository RoomRepository) {
		super();
		this.roomRepository = RoomRepository;
	}

	@Override
	public Room create(Room Room) {
		return roomRepository.save(Room);
	}

	@Override
	public List<Room> get() {
		return roomRepository.findAll();
	}

	@Override
	public Room get(Long id) {
		return roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Room Not Found!"));
	}

	@Override
	public List<Room> getByName(String name) {
		return roomRepository.findByName(name);
	}

	@Override
	public List<Room> getByNameOrCapacity(String nameOrCap) {
		return roomRepository.findByNameOrCapacity(nameOrCap, nameOrCap);
	}
	
}
