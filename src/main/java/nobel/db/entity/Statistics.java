package nobel.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nobel.model.GameStatistic;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Statistics {
	@Id
	private String id;
	@Column
	private int games;
	@Column
	private int wins;
	@Column
	private int losses;
	@Column
	private int draws;
}
