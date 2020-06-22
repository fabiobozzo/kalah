export class Move {

  roomId: string;
  playerId: string;
  position: number;

  constructor(roomId: string, playerId: string, position: number) {
    this.roomId = roomId;
    this.playerId = playerId;
    this.position = position;
  }
}
