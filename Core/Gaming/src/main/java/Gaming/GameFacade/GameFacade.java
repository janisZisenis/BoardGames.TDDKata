package Gaming.GameFacade;

import Gaming.GameLoopImp.Game;

public class GameFacade implements Game {

    private final GameOverRule rule;
    private final Player player;
    private final Renderer renderer;

    public GameFacade(GameOverRule rule, Renderer renderer, Player player) {
        this.player = player;
        this.renderer = renderer;
        this.rule = rule;
    }

    public void play() {
        player.play();
    }

    public void render() {
        renderer.render();
    }

    public boolean isOver() {
        return rule.isGameOver();
    }
}
