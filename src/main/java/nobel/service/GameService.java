package nobel.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import nobel.db.entity.Statistics;
import nobel.db.repository.GameStatisticsRepository;
import nobel.model.GameResult;
import nobel.model.GameStatistic;
import nobel.model.Move;
import nobel.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class GameService {
	private static final Logger log = LoggerFactory.getLogger(GameService.class);

	@Autowired
	private GameStatisticsRepository gameStatisticsRepository;

	@Cacheable(value = "gameStatisticCache", key = "#userId")
	public GameStatistic startGame(String userId) {
	    log.debug("startGame ");

		return getFromDB(userId);

	}

	@Cacheable(value = "gameStatisticCache", key = "#userId")
	public GameStatistic getStatistics(String userId) {
		log.debug("getStatistics ");
		return getFromDB(userId);
		
	}

	public GameStatistic getFromDB(String userId) {
		Optional<Statistics> statistics = gameStatisticsRepository.findById(userId);

		if (statistics.isEmpty()) {
			Statistics statistic = new Statistics();
			statistic.setId(userId);
			gameStatisticsRepository.save(statistic);
			log.debug("getStatistics from db");
			return new GameStatistic();
		} else
			return converToGameStatistics(statistics.get());
	}
	
	
	@CacheEvict(value = "gameStatisticCache", key = "#userId")
	public void terminateGame(String userId) {
		GameStatistic statistics = getStatistics(userId);
		Statistics statistic = convertToStatistics(userId, statistics);
		gameStatisticsRepository.save(statistic);

	}

	
	public GameResult makeMove(String userId, Move userMove) {

		Move computerMove = Move.random();

		Result result = determineResult(userMove, computerMove);
		
		log.debug("makeMove {} ", result);
	
		return new GameResult(userMove, computerMove, result);
	}
	
	@CachePut(value = "gameStatisticCache", key = "#userId")
	public GameStatistic updateStatistics(String userId, Result result) {
         
        GameStatistic statistic = getStatistics(userId);
        
		statistic.setGames(statistic.getGames() + 1);
		switch (result) {
		case WIN  -> statistic.setWins(statistic.getWins() + 1);
		case DRAW -> statistic.setDraws(statistic.getDraws() + 1);
		case LOSE -> statistic.setLosses(statistic.getLosses() + 1);
		}

		return statistic;

	}

	private Result determineResult(Move userMove, Move computerMove) {
		if (userMove.equals(computerMove))
			return Result.DRAW;
		if (userMove.equals(Move.PAPER))
			return computerMove.equals(Move.ROCK) ? Result.WIN : Result.LOSE;

		if (userMove.equals(Move.ROCK))
			return computerMove.equals(Move.SCISSORS) ? Result.WIN : Result.LOSE;

		if (userMove.equals(Move.SCISSORS))
			return computerMove.equals(Move.PAPER) ? Result.WIN : Result.LOSE;

		throw new IllegalArgumentException("Illegal case");
	}

	private Statistics convertToStatistics(String userId, GameStatistic gameStatistic) {
		return new Statistics(userId, gameStatistic.getGames(), gameStatistic.getWins(), gameStatistic.getLosses(),
				gameStatistic.getDraws());

	}

	private GameStatistic converToGameStatistics(Statistics statistic) {
		return new GameStatistic(statistic.getGames(), statistic.getWins(), statistic.getLosses(),
				statistic.getDraws());
	}
}
