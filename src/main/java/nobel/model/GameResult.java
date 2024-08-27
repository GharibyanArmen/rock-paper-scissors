package nobel.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameResult {
	private Move userMove;
    private Move computerMove;
    private Result result;

}
