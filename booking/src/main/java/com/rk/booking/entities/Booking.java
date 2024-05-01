package com.rk.booking.entities;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "booking_details")
public class Booking {
	
	@Id
	private String id;
	private String roomId;
	private String roomName;
	private ArrayList<String> bookedSlots;
	
}
