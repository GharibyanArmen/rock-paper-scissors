package nobel.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import nobel.db.entity.Statistics;

public interface GameStatisticsRepository extends JpaRepository<Statistics, String>{

}
