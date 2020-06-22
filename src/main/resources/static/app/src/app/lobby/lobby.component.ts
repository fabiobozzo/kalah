import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { Room } from '../model/room';
import { Player } from '../model/player';
import { RoomInput } from '../model/room-input';
import { RoomService } from '../service/room-service.service';
import { GameService } from '../service/game-service.service';

@Component({
  selector: 'lobby',
  templateUrl: './lobby.component.html',
  styleUrls: ['./lobby.component.scss']
})
export class LobbyComponent implements OnInit {

  rooms: Room[];

  constructor(
    private roomService: RoomService,
    private gameService: GameService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.roomService.findAllOpenRooms().subscribe(data => {
      this.rooms = data;
    });
  }

  onCreateRoom(
    playerNameInput: HTMLInputElement,
    playerEmailInput: HTMLInputElement,
    newRoomNameInput: HTMLInputElement
  ) {

    if (
      !newRoomNameInput.value.trim().length ||
      !playerNameInput.value.trim().length ||
      !playerEmailInput.value.trim().length
    ) {
      alert("Please enter Player data and room name");
      return;
    }

    const room = new RoomInput(
      null,
      newRoomNameInput.value,
      new Player(
        playerNameInput.value,
        playerEmailInput.value
      )
    );

    this.roomService.create(room).subscribe(result => {
      this.gameService.setGameInfo(result.id, result.player1.id, 1);
      this.gotoGame();
    });
  }

  onJoinRoom(
    playerNameInput: HTMLInputElement,
    playerEmailInput: HTMLInputElement,
    roomId: string
  ) {

    if (
      !playerNameInput.value.trim().length ||
      !playerEmailInput.value.trim().length
    ) {
      alert("Please enter Player data");
      return;
    }

    const room = new RoomInput(
      roomId,
      null,
      new Player(
        playerNameInput.value,
        playerEmailInput.value
      )
    );

    this.roomService.join(room).subscribe(result => {
      this.gameService.setGameInfo(result.id, result.player2.id, 2);
      this.gotoGame();
    });
  }

  gotoGame() {
    this.router.navigate(['/game']);
  }
}
