import { Player } from './player';

export class Room {

  id: string;
  name: string;
  createdAt: string;
  status: string;
  playerTurn: string;
  player1: Player;
  player2: Player;
  winner: Player;
}
