import { Player } from './player';

export class RoomInput {
  id: string;
  name: string;
  player: Player;

  constructor(id: string, name: string, player: Player) {
    this.id = id;
    this.name = name;
    this.player = player;
  }
}
