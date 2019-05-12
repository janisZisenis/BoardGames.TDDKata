package GuiGaming.Presentation.WinningLinePresenter;

import Domain.Data.Field.Field;
import Domain.Data.Line.Line;
import Domain.GameEvaluation.GameEvaluator.Api.WinningLineProvider;

public class WinningLinePresenter {

    private final WinningLineProvider provider;
    private final WinningLineView view;

    public WinningLinePresenter(WinningLineView view, WinningLineProvider provider) {
        this.view = view;
        this.provider = provider;
    }

    public void onFieldUpdated(Field f) {
        if(provider.hasWinningLine()) {
            Line line = provider.getWinningLine();
            view.highlightLine(line);
        }
    }
}
