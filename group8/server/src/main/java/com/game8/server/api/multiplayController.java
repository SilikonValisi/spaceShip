package com.game8.server.api;


import com.game8.server.models.Player;
import com.game8.server.models.Score;
import com.game8.server.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequestMapping("api/multiplay")
@RestController
public class multiplayController {

    private int id=1;
    Set<Player> waitingPlayers = new HashSet<Player>();
    Set<Integer> playedScores = new HashSet<Integer>();

    @PostMapping(path="/addPlayer")
    public ResponseEntity<String> addPlayer(@RequestBody Player player) {
        player.setId(id);
        id++;
        waitingPlayers.add(player);
        System.out.printf("PLayer %s added with id %d",player.getPlayerName(),player.getId());
        return ResponseEntity.ok().body("PLAYER ADDED");
    }

    @PostMapping(path="/getOtherPlayer")
    public ResponseEntity<Player> getOtherPlayer(@RequestBody Player player) {


        Player otherPlayer=new Player("nap");
        for (Player p : waitingPlayers){
            if(!p.getPlayerName().equals(player.getPlayerName())){
                otherPlayer = p;
                break;
            }
        }
        System.out.printf("current player is %s %n",player.getPlayerName());
        System.out.printf("we are returning the other player, %s %n",otherPlayer.getPlayerName());
        return ResponseEntity.ok().body(otherPlayer);

    }

    @PostMapping(path="/otherPlayerMovement")
    public ResponseEntity<Player> otherPlayerMovement(@RequestBody Player player) {

        Player otherPlayer = new Player("nap");
        for (Player p : waitingPlayers){
            if(p.getPlayerName().equals(player.getPlayerName())){
                p.x=player.x;
                p.y=player.y;
            }
            else {
                otherPlayer=p;
            }
        }
        return ResponseEntity.ok().body(otherPlayer);

    }

    @PostMapping(path="/otherPlayerScore")
    public ResponseEntity<Score> otherPlayerMovement(@RequestBody Score score) {

        playedScores.add(score.getScore());
        System.out.printf("Arrived players score is: %d %n",score.getScore());
        int otherPlayerScore = 0;

        for (Integer i : playedScores){
            if(!i.equals(score.getScore())){
                otherPlayerScore=i;
                break;
            }
            else {
                otherPlayerScore=0;
            }
        }
        System.out.printf("Other players score is: %d %n",otherPlayerScore);

        return ResponseEntity.ok().body(new Score(otherPlayerScore));

    }







}
