package Domain.NumberOfMovesRule;

import Domain.Data.BoardBoundaries;
import Gaming.GameLoopImp.GameOverRule;

public class NumberOfMovesRule implements GameOverRule, SequentialGaming.DelegatingGame.GameOverRule {

    private final int fieldCount = BoardBoundaries.fieldCount;
    private final MarkedFieldCountProvider provider;

    public NumberOfMovesRule(MarkedFieldCountProvider provider) {
        this.provider = provider;
    }

    public boolean isGameOver() {
        return provider.getMarkedFieldCount() >= fieldCount;
    }

}
