import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams, HttpParamsOptions } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})

export class RoomService {

  constructor(private http: HttpClient) { }

  getRooms() {
    return this.http.get("http://localhost:8080/room/", { withCredentials: true });
  }

  getBookingByRoomId(roomId:string) {
    return this.http.get("http://localhost:8080/room/bookings-by-room/"+roomId, { withCredentials: true });
  }

  bookRoom(data:any) {
    return this.http.post("http://localhost:8080/room/bookRoom", data);
  }

}
