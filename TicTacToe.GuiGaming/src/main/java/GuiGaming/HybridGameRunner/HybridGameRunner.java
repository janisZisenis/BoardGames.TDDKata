package GuiGaming.HybridGameRunner;

import InputGeneration.Input.Input;
import InputGeneration.InputProcessor;

public class HybridGameRunner implements InputProcessor {

    private final HybridGameLoop loop;

    public HybridGameRunner(HybridGameLoop loop) {
        this.loop = loop;
    }

    public void run() {
        loop.run();
    }

    public void process(Input input) {
        if(loop.needsInput()) {
            loop.run(input);
            loop.run();
        }
    }
}

