package App;

import Domain.Board.Board;
import Domain.Data.BoardBoundaries;
import Domain.Data.Mark;
import Domain.IODeviceFactory;
import Domain.MinimaxInputGenerator.MinimaxInputGenerator;
import Input2D.InputGenerator;
import Input2D.ValidInputGenerator.InputAlerter;
import Messages.AlertingMessages;
import FXSynchronizingView.FXSynchronizingInputAlerter;
import FXSynchronizingView.FXSynchronizingInputView;

public class FXIODeviceFactory implements IODeviceFactory {

    private static FXSynchronizingInputView humanGenerator;

    public InputGenerator makeHumanInputGenerator() {
        if(humanGenerator == null)
            humanGenerator = new FXSynchronizingInputView(200);
        return humanGenerator;
    }

    public InputGenerator makeInvincibleInputGenerator(Board board, Mark m) {
        return new MinimaxInputGenerator(board, m);
    }

    public InputGenerator makeHumbleInputGenerator() {
        int rowColumnCount = BoardBoundaries.rowColumnCount;
        return Input2D.Factory.makeRandomInputGenerator(rowColumnCount, rowColumnCount);
    }

    public InputAlerter makeFieldExistsAlerter() {
        return makeInputAlerter(AlertingMessages.inputDoesNotExist);
    }

    public InputAlerter makeFieldIsEmptyAlerter() {
        return makeInputAlerter(AlertingMessages.inputAlreadyMarked);
    }

    private InputAlerter makeInputAlerter(String inputAlreadyMarked) {
        return new FXSynchronizingInputAlerter(inputAlreadyMarked);
    }

    public static void setHumanInputGenerator(FXSynchronizingInputView humanGenerator) {
        FXIODeviceFactory.humanGenerator = humanGenerator;
    }
}
