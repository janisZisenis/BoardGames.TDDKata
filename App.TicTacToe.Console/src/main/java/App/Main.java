package App;


import Domain.Board.Board;
import Domain.Board.HashingBoard.HashingBoard;
import Domain.Data.Mark;
import Domain.GameEvaluation.EquallyMarkedLineEvaluator.EquallyMarkedLineEvaluator;
import Domain.GameEvaluation.GameEvaluator.GameEvaluator;
import Domain.GameEvaluation.GameEvaluator.LineEvaluator;
import Domain.GameEvaluation.GameEvaluator.LineProvider;
import Domain.GameEvaluation.HumbleLineProvider.HumbleLineProvider;
import Domain.InputFieldGeneratorAdapter.InputFieldGeneratorAdapter;
import Domain.InputGeneration.InputValidators.FieldExistsValidator.FieldExistsValidator;
import Domain.InputGeneration.InputValidators.FieldIsEmptyValidator.FieldIsEmptyValidator;
import Domain.InputGeneration.MinimaxInputGenerator.MinimaxInputGenerator;
import Domain.NumberOfMovesRule.NumberOfMovesRule;
import Domain.Turn.FieldGenerator;
import Domain.Turn.TicTacToeTurn;
import InputGeneration.InputGenerator;
import InputGeneration.ValidInputGenerator.InputValidator;
import Mapping.MarkToStringMappers.MarkToXOMapper;
import Messages.AlertingMessages;
import SequentialGaming.DelegatingGame.Renderer;
import SequentialGaming.Factory;
import SequentialGaming.GameLoop.Game;
import SequentialGaming.GameLoop.GameLoop;
import SequentialGaming.GameOverRules.CompositeGameOverRule.CompositeGameOverRule;
import SequentialGaming.MultiTurn.MultiTurn;
import SequentialRendering.BoardRenderer.BoardRenderer;
import View.ConsoleBoardView;
import View.ConsoleInputAlerter;
import View.ConsoleInputGenerator;

public class Main {

    public static void main(String[] args) {
        Board board = new HashingBoard();

        ConsoleInputGenerator consoleGenerator = new ConsoleInputGenerator();
        ConsoleInputAlerter existsAlerter = new ConsoleInputAlerter(AlertingMessages.inputDoesNotExist);
        ConsoleInputAlerter alreadyMarkedAlerter = new ConsoleInputAlerter(AlertingMessages.inputAlreadyMarked);
        ConsoleBoardView view = new ConsoleBoardView(board, new MarkToXOMapper());

        LineProvider lineProvider = new HumbleLineProvider();
        LineEvaluator lineEvaluator = new EquallyMarkedLineEvaluator(board);
        GameEvaluator gameEvaluator = new GameEvaluator(lineProvider, lineEvaluator);

        InputValidator existsValidator = new FieldExistsValidator();
        InputValidator isEmptyValidator = new FieldIsEmptyValidator(board);

        InputGenerator humanGenerator = InputGeneration.Factory.makeAlertingInputGenerator(consoleGenerator, existsValidator, existsAlerter);
        humanGenerator = InputGeneration.Factory.makeAlertingInputGenerator(humanGenerator, isEmptyValidator, alreadyMarkedAlerter);
        FieldGenerator humanGeneratorAdapter = new InputFieldGeneratorAdapter(humanGenerator);

        InputGenerator minimaxGenerator = new MinimaxInputGenerator(board, Mark.Haley);
        InputGenerator computerGenerator = InputGeneration.Factory.makeAlertingInputGenerator(minimaxGenerator, existsValidator, existsAlerter);
        computerGenerator = InputGeneration.Factory.makeAlertingInputGenerator(computerGenerator, isEmptyValidator, alreadyMarkedAlerter);
        InputFieldGeneratorAdapter computerGeneratorAdapter = new InputFieldGeneratorAdapter(computerGenerator);

        TicTacToeTurn johns = new TicTacToeTurn(Mark.John, board, humanGeneratorAdapter);
        TicTacToeTurn haleys = new TicTacToeTurn(Mark.Haley, board, computerGeneratorAdapter);

        Renderer renderer = new BoardRenderer(view, gameEvaluator);

        CompositeGameOverRule rule = Factory.makeCompositeGameOverRule();
        rule.add(Factory.makeWinnerRule(gameEvaluator));
        rule.add(new NumberOfMovesRule(board));
        MultiTurn turn = Factory.makeMultiTurn(johns);
        turn.add(haleys);
        Game game = Factory.makeGame(rule, turn, renderer);
        GameLoop loop = Factory.makeGameLoop(game);

        renderer.render();
        loop.run();
    }

}
