package GuiGaming.HybridGameImp;

import InputGeneration.Input.Input;

public interface HybridPlayer {
    boolean isComputer();
    void playHuman(Input input);
    void playComputer();
}
