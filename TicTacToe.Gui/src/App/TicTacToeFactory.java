package App;

import Lib.Board.Board;
import Lib.Board.HashingBoard.HashingBoard;
import Lib.BoardRenderer.BoardRenderer;
import Lib.BoardRenderer.WinningLineProvider;
import Lib.Data.Mark;
import Lib.GameEvaluation.EquallyMarkedLineEvaluator.EquallyMarkedLineEvaluator;
import Lib.GameEvaluation.GameEvaluator.GameEvaluator;
import Lib.GameEvaluation.GameEvaluator.LineEvaluator;
import Lib.GameEvaluation.GameEvaluator.LineProvider;
import Lib.GameEvaluation.HumbleLineProvider.HumbleLineProvider;
import Lib.GameLoopImp.GameLoopImp;
import Lib.GameLoopImp.GameOverRule;
import Lib.GameLoopImp.Renderer;
import Lib.GameLoopImp.Turn;
import Lib.GameMessengerImp.GameMessengerImp;
import Lib.GameOverMessageProviderImp.GameOverMessageProviderImp;
import Lib.GameOverMessageProviderImp.WinnerMessageProviderImp.MarkToStringMapper;
import Lib.GameOverMessageProviderImp.WinnerMessageProviderImp.WinnerMessageProviderImp;
import Lib.GameOverMessageProviderImp.WinnerMessageProviderImp.WinnerProvider;
import Lib.GameOverRules.CompositeGameOverRule.CompositeGameOverRule;
import Lib.GameOverRules.NumberOfMovesRule.NumberOfMovesRule;
import Lib.GameOverRules.WinnerRule.HasWinnerProvider;
import Lib.GameOverRules.WinnerRule.WinnerRule;
import Lib.Games.GameImp.GameImp;
import Lib.Games.GameImp.GameLoop;
import Lib.Games.MessagingGame.Game;
import Lib.Games.MessagingGame.GameMessenger;
import Lib.Games.MessagingGame.MessagingGame;
import Lib.InputGenerators.AlertingInputGenerator.AlertingInputGenerator;
import Lib.InputGenerators.AlertingInputGenerator.InputValidator;
import Lib.InputGenerators.AlertingInputGenerator.InputValidatorImp.InputAlerter;
import Lib.InputGenerators.AlertingInputGenerator.InputValidatorImp.InputRule;
import Lib.InputGenerators.AlertingInputGenerator.InputValidatorImp.InputValidatorImp;
import Lib.InputGenerators.AlertingInputGenerator.InputValidatorImp.RuleChoosingInputAlerter.RuleChoosingInputAlerter;
import Lib.InputGenerators.RandomInputGenerator.RandomInputGenerator;
import Lib.InputGenerators.ValidatingInputGenerator.ValidatingInputGenerator;
import Lib.InputRules.CompositeInputRule.CompositeInputRule;
import Lib.InputRules.FieldExistsRule.FieldExistsRule;
import Lib.InputRules.FieldIsEmptyRule.FieldIsEmptyRule;
import Lib.MarkToStringMappers.MarkToMessageMapper;
import Lib.MarkToStringMappers.MarkToXOMapper;
import Lib.Messages.AlertingMessages;
import Lib.ObjectToStringMapper.ObjectToMessageMapper;
import Lib.ObjectToStringMapper.ObjectToStringMapper;
import Lib.Players.InputGenerator;
import Lib.Players.PlayerContext;
import Lib.TurnMessengerImp.TurnMessengerImp;
import Lib.TwoPlayerTurn.MessagingTwoPlayerTurn.MessagingTwoPlayerTurn;
import Lib.TwoPlayerTurn.MessagingTwoPlayerTurn.TurnMessenger;
import Lib.TwoPlayerTurn.Player;
import Player.MessagingPlayer.PlayerMessenger;
import Player.PlayerMessengerImp.MarkedFieldMessageProviderImp;
import Player.PlayerMessengerImp.PlayerMessengerImp;
import View.FXBoardView;
import View.FXMessenger;
import Player.MessagingPlayer.MessagingPlayer;

public class TicTacToeFactory {

    private FXMessenger messenger;
    private FXBoardView boardView;
    private FXInputView inputView;
    public FXShell shell;

    public Game makeGame() {
        Board board = makeBoard();
        MarkToStringMapper mapper = makeMarkToStringMapper();

        boardView = new FXBoardView(200, board, mapper);
        inputView = new FXInputView(200);
        messenger = new FXMessenger(450);

        shell = new FXShell(boardView, inputView, messenger);

        Renderer renderer = makeRenderer(board);
        GameLoop loop = makeGameLoop(board);
        Game game = new GameImp(renderer, loop);

        GameMessenger gameMessenger = makeGameMessenger(board);
        return new MessagingGame(game, gameMessenger);
    }

    private PlayerMessenger makePlayerMessenger() {
        MarkedFieldMessageProviderImp fieldProvider = new MarkedFieldMessageProviderImp();
        return new PlayerMessengerImp(messenger, fieldProvider);
    }

    private TurnMessenger makeTurnMessenger(ObjectToStringMapper mapper) {
        return new TurnMessengerImp(messenger, mapper);
    }

    private GameMessenger makeGameMessenger(Board board) {
        WinnerProvider provider = makeGameEvaluator(board);
        MarkToStringMapper messageMapper = new MarkToMessageMapper("You win!", "Computer wins!");
        WinnerMessageProviderImp winnerMessageProvider = new WinnerMessageProviderImp(provider, messageMapper);
        GameOverMessageProviderImp goMessageProvider = new GameOverMessageProviderImp(winnerMessageProvider, "Draw!");
        return new GameMessengerImp(messenger, goMessageProvider, "Welcome To TicTacToe!");
    }


    private HashingBoard makeBoard() {
        return new HashingBoard();
    }

    private GameLoop makeGameLoop(Board board) {
        GameOverRule rule = makeGameOverRule(board);
        Turn turn = makeTurn(board);
        Renderer renderer = makeRenderer(board);
        return new GameLoopImp(rule, turn, renderer);
    }

    private Renderer makeRenderer(Board board) {
        WinningLineProvider provider = makeWinningLineProvider(board);
        return new BoardRenderer(boardView, provider);
    }

    private MarkToStringMapper makeMarkToStringMapper() {
        return new MarkToXOMapper();
    }

    private WinningLineProvider makeWinningLineProvider(Board board) {
        return makeGameEvaluator(board);
    }

    private EquallyMarkedLineEvaluator makeLineEvaluator(Board board) {
        return new EquallyMarkedLineEvaluator(board);
    }

    private LineProvider makeLineProvider() {
        return new HumbleLineProvider();
    }

    private GameOverRule makeGameOverRule(Board board) {
        GameOverRule numberOfMovesRule = makeNumberOfMovesRule(board);
        GameOverRule winningLineRule = makeWinnerRule(board);

        CompositeGameOverRule composite = new CompositeGameOverRule();
        composite.add(numberOfMovesRule);
        composite.add(winningLineRule);
        return composite;
    }

    private GameOverRule makeWinnerRule(Board board) {
        HasWinnerProvider winningLineProvider = makeHasWinnerProvider(board);
        return new WinnerRule(winningLineProvider);
    }

    private NumberOfMovesRule makeNumberOfMovesRule(Board board) {
        return new NumberOfMovesRule(board);
    }

    private HasWinnerProvider makeHasWinnerProvider(Board board) {
        return makeGameEvaluator(board);
    }

    private GameEvaluator makeGameEvaluator(Board board) {
        LineProvider provider = makeLineProvider();
        LineEvaluator evaluator = makeLineEvaluator(board);
        return new GameEvaluator(provider, evaluator);
    }


    private Turn makeTurn(Board board) {
        Player john = makeHumanPlayer(board, Mark.John);
        Player haley = makeComputerPlayer(board, Mark.Haley);

        ObjectToMessageMapper objectMapper = new ObjectToMessageMapper();
        objectMapper.register(john, "It's your turn!");
        objectMapper.register(haley, "It's computer's turn");
        TurnMessenger messenger = makeTurnMessenger(objectMapper);

        return new MessagingTwoPlayerTurn(john, haley, messenger);
    }

    private Player makeHumanPlayer(Board board, Mark m) {
        InputGenerator humanGenerator = makeHumanInputGenerator(board);
        return makePlayer(board, humanGenerator, m);
    }

    private Player makeComputerPlayer(Board board, Mark m) {
        InputGenerator computerGenerator = makeComputerInputGenerator(board);
        return makePlayer(board, computerGenerator, m);
    }

    private Player makePlayer(Board board, InputGenerator generator, Mark m) {
        PlayerContext context = new PlayerContext(generator, board, m);
        PlayerMessenger messenger = makePlayerMessenger();
        return new MessagingPlayer(context, messenger);
    }

    private InputGenerator makeComputerInputGenerator(Board board) {
        InputRule inputRule = makeInputRule(board);
        InputGenerator randomGenerator = makeRandomInputGenerator();
        return new ValidatingInputGenerator(randomGenerator, inputRule);
    }

    private RandomInputGenerator makeRandomInputGenerator() {
        return new RandomInputGenerator();
    }

    private InputGenerator makeHumanInputGenerator(Board board) {
        InputValidator validator = makeInputValidator(board);
        InputGenerator consoleGenerator = inputView;
        return new AlertingInputGenerator(consoleGenerator, validator);
    }

    private InputRule makeInputRule(Board board) {
        InputRule existsRule = makeFieldExistsRule();
        InputRule isFreeRule = makeFieldIsEmptyRule(board);

        CompositeInputRule inputRule = new CompositeInputRule();
        inputRule.add(existsRule);
        inputRule.add(isFreeRule);

        return inputRule;
    }

    private FieldExistsRule makeFieldExistsRule() {
        return new FieldExistsRule();
    }

    private InputAlerter makeInputAlerter(Board board) {
        InputRule existsRule = makeFieldExistsRule();
        InputRule isFreeRule = makeFieldIsEmptyRule(board);
        InputAlerter existsAlerter = makeFXInputAlerter(AlertingMessages.inputDoesNotExist);
        InputAlerter isFreeAlerter = makeFXInputAlerter(AlertingMessages.inputAlreadyMarked);

        RuleChoosingInputAlerter alerter = new RuleChoosingInputAlerter();
        alerter.register(existsRule, existsAlerter);
        alerter.register(isFreeRule, isFreeAlerter);

        return alerter;
    }

    private FXInputAlerter makeFXInputAlerter(String inputDoesNotExist) {
        return new FXInputAlerter(inputDoesNotExist);
    }

    private FieldIsEmptyRule makeFieldIsEmptyRule(Board board) {
        return new FieldIsEmptyRule(board);
    }

    private InputValidator makeInputValidator(Board board) {
        InputRule rule = makeInputRule(board);
        InputAlerter alerter = makeInputAlerter(board);
        return new InputValidatorImp(rule, alerter);
    }

}
