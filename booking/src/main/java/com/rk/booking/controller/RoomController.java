package com.rk.booking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rk.booking.entities.Booking;
import com.rk.booking.entities.Room;
import com.rk.booking.services.BookingService;
import com.rk.booking.services.RoomService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/room")
public class RoomController {

	private RoomService roomService;
	private BookingService bookingService;
	private boolean sessionCreated = false;

	public RoomController(RoomService roomService, BookingService bookingService) {
		super();
		this.roomService = roomService;
		this.bookingService = bookingService;
	}
	
	// Get All
	@GetMapping("/")
	public ResponseEntity<?> getAllRooms(HttpSession session, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
		response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,DELETE,PUT");
		
		sessionCreated = true;
		
        return ResponseEntity.ok(roomService.get());
	}
	
	// Create Room
	@PostMapping("/")
	public ResponseEntity<?> createRoom(@RequestBody Room room, HttpSession session) {
		return ResponseEntity.ok(roomService.create(room));
	}
	
	// Get One Room
	@GetMapping("/{id}")
	public ResponseEntity<?> getRoom(@PathVariable Long id, HttpSession session) {
		return ResponseEntity.ok(roomService.get(id));
	}
	
	// Get Rooms By Name
	@GetMapping("/by-name/{name}")
	public ResponseEntity<?> getRoomsByName(@PathVariable String name, HttpSession session) {
		return ResponseEntity.ok(roomService.getByName(name));
	}
	
	// Get Rooms By Name
	@GetMapping("/search/{nameOrCap}")
	public ResponseEntity<?> getRoomsByNameOrCapacity(@PathVariable String nameOrCap, HttpSession session) {
		return ResponseEntity.ok(roomService.getByNameOrCapacity(nameOrCap));
	}
	
	//Get All Bookings
	@GetMapping("/bookings")
	public ResponseEntity<?> getAllBookings(HttpSession session) {
		return ResponseEntity.ok(bookingService.get());
	}
	
	//Get Bookings By Room Id
	@GetMapping("/bookings-by-room/{roomId}")
	public ResponseEntity<?> getBookingsByRoomId(@PathVariable String roomId, HttpSession session, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
		response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,DELETE,PUT");
		return ResponseEntity.ok(bookingService.getBookingsByRoomId(roomId));
	}
	
	// Book Room
	@PostMapping("/bookRoom")
	public ResponseEntity<?> bookRoom(@RequestBody Booking booking) {
		String status = "";
		
		if(sessionCreated) {
			System.out.println("Session Exist!");
			status = bookingService.bookRoom(booking);
			if(status != "Success") {
				return new ResponseEntity<>(status, HttpStatus.NOT_ACCEPTABLE);
			}
		}
		return new ResponseEntity<>(status, HttpStatus.OK);
	}
	
}
