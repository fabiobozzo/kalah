import { Injectable, EventEmitter } from '@angular/core';
import { Settings } from '../settings';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';

import { Move } from '../model/move';
import { Status } from '../model/status';

@Injectable({
  providedIn: 'root'
})
export class GameService {

  playUrl: string;
  retireUrl: string;
  stompUrl: string;

  gameStatusChanged = new EventEmitter<Status[]>();

  roomId: string;
  playerId: string;
  playerSide: number;

  private stompClient;

  constructor(private http: HttpClient) {
    this.playUrl = Settings.API_ENDPOINT + '/play';
    this.retireUrl = Settings.API_ENDPOINT + '/retire';
  }

  public startStompClient() {
    this.stompClient = Stomp.over(new SockJS(Settings.STOMP_ENDPOINT));
    let _this = this;
    this.stompClient.connect({}, function(frame) {
      _this.stompClient.subscribe("/game", (message) => {
        console.log(message);
        _this.gameStatusChanged.emit(JSON.parse(message.body));
      });
    });
  }

  public stopStompClient() {
    if (this.stompClient !== null) {
      this.stompClient.disconnect();
    }
  }

  public play(position: number) {
    return this.http.post<Move>(this.playUrl, new Move(
      this.roomId,
      this.playerId,
      position
    ));
  }

  public retire() {
    return this.http.post<Move>(this.retireUrl, new Move(
      this.roomId,
      this.playerId,
      null
    ));
  }

  public setGameInfo(roomId: string, playerId: string, playerSide: number) {
    this.roomId = roomId;
    this.playerId = playerId;
    this.playerSide = playerSide;
  }

  public isOwnPlayer(playerId: string) {
    return playerId === this.playerId;
  }

}
