import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { GameService } from '../service/game-service.service';

@Component({
  selector: 'game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.scss']
})
export class GameComponent implements OnInit {

  pitsP1: number[];
  pitsP2: number[];

  constructor(
    private gameService: GameService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.gameService.startStompClient();
    this.gameService.gameStatusChanged.subscribe((status: Status) => {
        console.log(status);
        pitsP1[0] = status.pits[0];
        pitsP1[1] = status.pits[1];
        pitsP1[2] = status.pits[2];
        pitsP1[3] = status.pits[3];
        pitsP1[4] = status.pits[4];
        pitsP1[5] = status.pits[5];
        pitsP1[6] = status.pits[6];
        pitsP2[0] = status.pits[7];
        pitsP2[1] = status.pits[8];
        pitsP2[2] = status.pits[9];
        pitsP2[3] = status.pits[10];
        pitsP2[4] = status.pits[11];
        pitsP2[5] = status.pits[12];
        pitsP2[6] = status.pits[13];
    });
  }

  onQuitGame() {
    this.gameService.retire().subscribe(result => {
      this.gameService.setGameInfo(null, null);
      this.gameService.stopStompClient();
      this.gotoLobby();
    });
  }

  onMove(position: number) {
    this.gameService.play(position).subscribe(result => console.log('Move: '+position));
  }

  gotoLobby() {
    this.router.navigate(['/lobby']);
  }
}
