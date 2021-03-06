package Presentation.GameOverViewPresenter;

import Messaging.GameLoopMessengerImp.MessageProvider;
import Gaming.GameFacade.GameOverRule;
import Utilities.Transaction.Transaction;

public class GameOverInteractorFacade implements GameOverInteractor {

    private final GameOverRule rule;
    private final MessageProvider provider;

    private final Transaction cancelAction;
    private final Transaction reconfigureAction;
    private final Transaction restartAction;

    public GameOverInteractorFacade(GameOverRule rule,
                                    MessageProvider provider,
                                    Transaction cancelAction,
                                    Transaction reconfigureAction,
                                    Transaction restartAction) {
        this.rule = rule;
        this.provider = provider;
        this.cancelAction = cancelAction;
        this.reconfigureAction = reconfigureAction;
        this.restartAction = restartAction;
    }

    public void sendCancel() {
        cancelAction.execute();
    }

    public void sendReconfigure() {
        reconfigureAction.execute();
    }

    public void sendRestart() {
        restartAction.execute();
    }

    public GameOverMessageResponse receive() {
        boolean isGameOver = rule.isGameOver();
        String message = provider.getMessage();
        return new GameOverMessageResponse(isGameOver, message);
    }

}
