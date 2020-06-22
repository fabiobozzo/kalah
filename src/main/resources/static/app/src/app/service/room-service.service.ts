import { Injectable } from '@angular/core';
import { Settings } from '../settings';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { Room } from '../model/room';
import { RoomInput } from '../model/room-input';

@Injectable({
  providedIn: 'root'
})
export class RoomService {

  roomsUrl: string;
  leaderboardUrl: string;

  constructor(private http: HttpClient) {
    this.roomsUrl = Settings.API_ENDPOINT + '/room';
    this.leaderboardUrl = Settings.API_ENDPOINT + '/leaderboard';
  }

  public findAllOpenRooms(): Observable<Room[]> {
    return this.http.get<Room[]>(this.roomsUrl);
  }

  public create(room: RoomInput) {
    return this.http.post<Room>(this.roomsUrl, room);
  }

  public join(room: RoomInput) {
    return this.http.put<Room>(this.roomsUrl, room);
  }
}
