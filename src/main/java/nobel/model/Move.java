package nobel.model;

import java.util.concurrent.ThreadLocalRandom;

public enum Move {
	ROCK, PAPER, SCISSORS;

    public static Move random() {
    	int index = ThreadLocalRandom.current().nextInt(0,values().length);
        return  values() [index];
    }

}
