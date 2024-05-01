import { Component, OnInit } from '@angular/core';
import {FormControl} from '@angular/forms';
import {Observable} from 'rxjs';
import {map, min, startWith} from 'rxjs/operators';
import { RoomService } from '../room.service';
import { formatDate } from '@angular/common';
import {ThemePalette} from '@angular/material/core';
import {ToastrService} from 'ngx-toastr';

export interface ChipColor {
  value: string;
  color: ThemePalette;
}

@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrls: ['./room.component.scss']
})
export class RoomComponent {

  myControl = new FormControl();
  options: string[] = [];
  filteredOptions: Observable<string[]> | undefined;
  roomsData: any = [];
  rooms:any = [];
  roomSelected:any = false;
  roomName:string = "";
  roomDetails:any;
  timeSlots: ChipColor[] = [
    {value: '10:00am-10:30am', color: 'primary'},
    {value: '10:30am-11:00am', color: 'primary'},
    {value: '11:00am-11:30am', color: 'accent'},
    {value: '11:30am-12:00pm', color: 'warn'},
    {value: '12:00pm-12:30pm', color: 'warn'},
    {value: '12:30pm-01:00pm', color: 'warn'},
    {value: '02:00pm-02:30pm', color: 'warn'},
    {value: '02:30pm-03:00pm', color: 'warn'},
    {value: '03:00pm-03:30pm', color: 'warn'},
    {value: '03:30pm-04:00pm', color: 'warn'},
    {value: '04:00pm-04:30pm', color: 'warn'},
    {value: '04:30pm-05:00pm', color: 'warn'},
    {value: '05:00pm-05:30pm', color: 'warn'},
    {value: '05:30pm-06:00pm', color: 'warn'},
    {value: '06:00pm-06:30pm', color: 'warn'},
    {value: '06:30pm-07:00pm', color: 'warn'},
  ];
  selectedDate:string = "";
  selectedTimeSlot:string = "";
  alreadyBookedSlots:Array<String> = [];
  showSlotTime:boolean = true;

  constructor(
    private roomService : RoomService, 
    private toast: ToastrService) {
    
  }

  ngOnInit() {
    this.filteredOptions = this.myControl.valueChanges
      .pipe(
        startWith(''),
        map(value => this._filter(value))
      );
    this.getRooms();
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();

    return this.options.filter(option => option.toLowerCase().includes(filterValue));
  }

  getRooms() {
    this.roomService.getRooms().subscribe((res:any)=>{
      console.log("Response: ", res);
      // this.roomsData = res;
      res.forEach((element: { [x: string]: string; }) => {
        this.options.push(element["name"]);
        this.options.push(element["capacity"]);
        console.log(this.options);

        // ---------------------------------------
        this.roomService.getBookingByRoomId(element["id"]).subscribe((booking:any)=>{
          var currentDate = formatDate(new Date(),'yyyy-MM-dd','en_US');
          var currentHour = new Date().getHours();
          var hour = currentHour;
          var hourPlusOne = currentHour+1;
          var mins = new Date().getMinutes();
          hour = hour % 12;
          hour = hour ? hour : 12;
          var newHour = hour < 10 ? '0'+hour : hour;
          hourPlusOne = hourPlusOne % 12;
          hourPlusOne = hourPlusOne ? hourPlusOne : 12;
          var newHourPlusOne = hourPlusOne < 10 ? '0'+hourPlusOne : hourPlusOne;

          if(currentHour>=19 && mins>30) {
            element["status"] = "available for tomorrow";
          } else if((booking["bookedSlots"].includes(currentDate+"T"+newHour+":00pm"+"-"+newHour+":30pm"))
                  || (booking["bookedSlots"].includes(currentDate+"T"+newHour+":30pm"+"-"+(newHourPlusOne)+":00pm")) && 
            !(booking["bookedSlots"].includes(currentDate+"T"+newHour+":00") && booking["bookedSlots"].includes(currentDate+"T"+newHour+":30"))) {
              element["status"] = "available after 30mins.";
          } else {
            element["status"] = "currently available";
          }

          // ------------------------------------

          element["bookedSlots"] = booking["bookedSlots"];
          this.roomsData.push(element);
        });
      });
    });
  }

  searchRoom() {
    console.log("Option: ", this.myControl.value);
    this.rooms = [];
    if(this.myControl.value == null || this.myControl.value == "") {
      this.toast.warning("Please enter some Room Name or Room Capacity.");
    } else {
      for(let i in this.roomsData) {
        if(this.roomsData[i]["name"].toLowerCase().startsWith(this.myControl.value.toLowerCase()) 
            || this.roomsData[i]["capacity"].toLowerCase().startsWith(this.myControl.value.toLowerCase())) {
          this.rooms.push(this.roomsData[i]);
        }
      }
    }
    this.roomSelected = false;
  }

  getBookingByRoomId(room:any) {
    this.roomService.getBookingByRoomId(room.id).subscribe((res:any)=>{
      console.log("Bookings by RoomId: ", res);
      this.alreadyBookedSlots = res.bookedSlots;
    });
  }

  openRoom(room:any) {
    console.log("Opening Room...", room);
    this.roomSelected = true;
    this.getBookingByRoomId(room);
    this.roomDetails = room;
  }

  onDateChange(selectedDate:any) {
    console.log("Selected Date: ", selectedDate);
    
    var currentDate = formatDate(new Date(),'yyyy-MM-dd','en_US');
    var date = formatDate(selectedDate,'yyyy-MM-dd','en_US');

    if(currentDate>date){
      console.log("Can't schedule meeting in past...");
      this.toast.error("You can't schedule meeting in past. Please choose new TimeSlot", 'Error:');
      this.showSlotTime = true;
    }else{
      this.selectedDate = date;
      console.log('Valid Date Selected...');
      this.showSlotTime = false;
    }
  }

  selectTimeSlot(timeSlot:any) {
    console.log("Selected Slot: ", timeSlot);
    this.selectedTimeSlot = timeSlot;
  }

  bookRoom() {
    console.log("Date: ", this.selectedDate);
    console.log("Time Slot: ", this.selectedTimeSlot);
    if(this.selectedDate == "") {
      this.toast.info("Please Choose Date.");
    }
    if(this.selectedTimeSlot == "") {
      this.toast.info("Please Choose Time Slot.");
    }

    if(this.selectedDate != "" && this.selectedTimeSlot != "") {
      var arr:any = [];
      arr.push(this.selectedDate+"T"+this.selectedTimeSlot);
      var bookingData = {
                          "roomId"      : this.roomDetails.id,
                          "roomName"    : this.roomDetails.name,
                          "bookedSlots" : arr
                        };

      this.roomService.bookRoom(bookingData).subscribe((e:any)=>{
        
      }, (res:any)=>{
        if(res.error.text) {
          console.log("Success!");
          this.toast.success("Room Booked Successfully!");
          this.alreadyBookedSlots.push(this.selectedDate+"T"+this.selectedTimeSlot);
        } else {
          console.log("Error: ", res.error);
          this.toast.error(res.error);
        }
        console.log("Error: ", res);
      })
    }
  }

}
