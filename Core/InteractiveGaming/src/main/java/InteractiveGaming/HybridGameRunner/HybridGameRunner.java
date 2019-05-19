package InteractiveGaming.HybridGameRunner;

import InputGeneration.Input.Input;
import InputGeneration.InputProcessor;
import InteractiveGaming.ResetTransaction.GameRunner;

public class HybridGameRunner implements InputProcessor, GameRunner {

    private final HybridGame game;

    public HybridGameRunner(HybridGame game) {
        this.game = game;
    }

    public void run() {
        game.runToNextInputTurn();
    }

    public void process(Input input) {
        if(nextIsInputTurn())
            runInput(input);
    }

    private void runInput(Input input) {
        game.playInput(input);
        run();
    }

    private boolean nextIsInputTurn() {
        return game.nextIsInputTurn();
    }
}

