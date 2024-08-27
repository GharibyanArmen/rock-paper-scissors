package nobel.model;

import java.io.Serializable;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameStatistic implements Serializable
{
	  private int games;
	  private int wins;
	  private int losses;
	  private int draws;

}
