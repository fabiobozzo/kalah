import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { GameService } from '../service/game-service.service';
import { Status } from '../model/status';

@Component({
  selector: 'game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.scss']
})
export class GameComponent implements OnInit {

  pitsP1: number[] = [6, 6, 6, 6, 6, 6, 0];
  pitsP2: number[] = [6, 6, 6, 6, 6, 6, 0];
  ownTurn: boolean;
  finished: boolean;
  victory: boolean;

  constructor(
    private gameService: GameService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {

    this.ownTurn = false;
    this.finished = false;
    this.victory = false;

    let _this = this;
    let playerSide = this.gameService.playerSide;

    this.gameService.startStompClient();
    this.gameService.gameStatusChanged.subscribe((status: Status) => {
        console.log(status);

        _this.setOwnTurn( _this.gameService.isOwnPlayer(status.currentPlayerId) );

        for (let i = 0; i < status.pits.length/2; i++) {
          this.pitsP1[i] = playerSide === 1 ? status.pits[i] : status.pits[i+(status.pits.length/2)];
          this.pitsP2[i] = playerSide === 2 ? status.pits[i] : status.pits[i+(status.pits.length/2)];
        }

        if (status.winner) {
          this.finished = true;
          if (_this.gameService.isOwnPlayer(status.winner)) {
            this.victory = true;
          }
        }
    });
  }

  setOwnTurn(ownTurn: boolean) {
    this.ownTurn = ownTurn;
  }

  onQuitGame() {
    this.gameService.retire().subscribe(result => {
      this.gameService.setGameInfo(null, null, null);
      this.gameService.stopStompClient();
      this.gotoLobby();
    });
  }

  onMove(position: number) {
    if (this.finished) {
      this.gotoLobby();

    } else if (!this.ownTurn) {
      alert('It\'s not your turn!');

    } else if (this.pitsP1[position] === 0) {
      alert('Can\'t make this move! Pit\'s empty!');

    } else {
      this.gameService.play(position).subscribe(result => console.log('Move: '+position));

    }
  }

  gotoLobby() {
    this.router.navigate(['/lobby']);
  }
}
