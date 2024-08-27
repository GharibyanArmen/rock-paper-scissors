package nobel.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nobel.db.repository.GameStatisticsRepository;
import nobel.model.GameResult;
import nobel.model.GameStatistic;
import nobel.model.Move;
import nobel.model.Result;
import nobel.service.GameService;



@RestController
@RequestMapping(value = "/game")
public class GameController {

	
    @Autowired
    private GameService gameService;

	
	 @Value("${spring.application.name}")
	 private String applicationName;
	
	 //   private final Logger LOGGER = LoggerFactory.getLogger(GameController.class);

	
	
	    @PostMapping("/start")
	    public ResponseEntity<Void> startGame(@RequestParam String userId) {
	        gameService.startGame(userId);
	        return ResponseEntity.ok().build();
	    }

	    @PostMapping("/move")
	    public ResponseEntity<GameResult> makeMove(@RequestParam String userId, @RequestParam String userMove) {
	    	Move move = Move.valueOf(userMove.toUpperCase());
	    	GameResult result =gameService.makeMove(userId, move);
	    	
	    	gameService.updateStatistics(userId, result.getResult());
	        return ResponseEntity.ok(result);
	    }

	    @PostMapping("/terminate")
	    public ResponseEntity<Void> terminateGame(@RequestParam String userId) {
	        gameService.terminateGame(userId);
	        return ResponseEntity.ok().build();
	    }

	    @GetMapping("/stats")
	    public ResponseEntity<GameStatistic> getStatistics(@RequestParam String userId) {
	        GameStatistic stats = gameService.getStatistics(userId);
	        return ResponseEntity.ok(stats);
	    }

	    
}
