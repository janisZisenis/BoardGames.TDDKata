package Domain.NumberOfMovesRule;

import Domain.Data.BoardBoundaries;
import Gaming.GameFacade.GameOverRule;

public class NumberOfMovesRule implements GameOverRule {

    private final int fieldCount = BoardBoundaries.fieldCount;
    private final MarkedFieldCountProvider provider;

    public NumberOfMovesRule(MarkedFieldCountProvider provider) {
        this.provider = provider;
    }

    public boolean isGameOver() {
        return provider.getMarkedFieldCount() >= fieldCount;
    }

}
