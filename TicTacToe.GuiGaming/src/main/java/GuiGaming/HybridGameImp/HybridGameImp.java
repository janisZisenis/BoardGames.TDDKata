package GuiGaming.HybridGameImp;

import GuiGaming.HybridGameRunner.HybridGame;
import InputGeneration.Input.Input;
import SequentialGaming.GameFacade.GameOverRule;

public class HybridGameImp {

    private final HybridPlayer player;
    private final GameOverRule rule;

    public HybridGameImp(GameOverRule rule, HybridPlayer player) {
        this.rule = rule;
        this.player = player;
    }

    public void playHuman(Input input) {
        throwIfIsComputer();
        player.playHuman(input);
    }

    private void throwIfIsComputer() {
        if(player.isComputer())
            throw new HybridGame.CannotPlayHumanOnComputersTurn();
    }

    public boolean nextIsComputer() {
        return player.isComputer();
    }

    public void playComputerTurns() {
        while(player.isComputer() && !rule.isGameOver())
            player.playComputer();
    }

}
