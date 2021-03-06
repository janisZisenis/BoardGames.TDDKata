package App;

import Domain.Board.BoardDecorators.ListenableBoard.ListenableBoard;
import Domain.Board.BoardDecorators.ObservableBoard.ObservableBoard;
import Domain.Data.BoardBoundaries;
import Domain.Data.Mark;
import Domain.GameEvaluation.GameEvaluator.Api.WinningLineProvider;
import FXSynchronizingView.FXSynchronizingInputView;
import FXSynchronizingView.FXSynchronizingBoardView;
import FXSynchronizingView.FXSynchronizingMessengerView;
import Messaging.GameLoopMessengerImp.MessageProvider;
import Gaming.Factory;
import Gaming.GameFacade.GameOverRule;
import Gaming.GameFacade.NullRenderer;
import Gaming.GameFacade.Player;
import Gaming.GameLoopImp.Game;
import Gaming.GameLoopImp.Api.GameLoop;
import Messaging.MessagingGameLoop.GameLoopMessenger;
import Gaming.MultiPlayer.MultiPlayer;
import Gaming.MultiPlayer.MultiPlayerMessenger;
import Input2D.NullInputProcessor;
import Messaging.MessageProviders.FixedMessageProvider.FixedMessageProvider;
import Messages.TicTacToeMessages;
import Messaging.MessagingBoardListener.HumbleMarkedFieldMessageProviderImp;
import Messaging.MessagingBoardListener.MarkedFieldMessageProvider;
import Messaging.MessagingBoardListener.MessagingBoardListener;
import Presentation.BoardViewPresenter.BoardViewPresenter;
import Presentation.WinningLinePresenter.WinningLinePresenter;
import Utilities.ObjectToStringMapper.DefaultObjectToStringMapper;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        ListenableBoard listenableBoard = Domain.Factory.makeListenableBoard();
        ObservableBoard observableBoard = new ObservableBoard(listenableBoard);

        FXSynchronizingInputView fxGenerator = new FXSynchronizingInputView(200);
        FXSynchronizingBoardView fxBoard = new FXSynchronizingBoardView(BoardBoundaries.rowColumnCount, 200);
        FXSynchronizingMessengerView fxMessenger = new FXSynchronizingMessengerView(445);
        FXSequentialShell fxShell = new FXSequentialShell(fxBoard, fxGenerator, fxMessenger);

        MarkedFieldMessageProvider markedFieldMessageProvider = new HumbleMarkedFieldMessageProviderImp();
        MessageProvider clearMessageProvider = new FixedMessageProvider(TicTacToeMessages.boardClearedMessage);
        MessagingBoardListener listener = new MessagingBoardListener(fxMessenger, markedFieldMessageProvider, clearMessageProvider);
        listenableBoard.addListener(listener);

        FXIODeviceFactory factory = new FXIODeviceFactory();
        FXIODeviceFactory.setHumanInputGenerator(fxGenerator);
        Player john = Domain.Factory.makeHumanPlayer(Mark.John, observableBoard, factory);
        Player haley = Domain.Factory.makeInvincibleComputerPlayer(Mark.Haley, observableBoard, factory);

        DefaultObjectToStringMapper playerMapper = new DefaultObjectToStringMapper(TicTacToeMessages.defaultPlayerMessage);
        playerMapper.register(john, TicTacToeMessages.humanPlayerMessage);
        playerMapper.register(haley, TicTacToeMessages.computerPlayerMessage);
        MultiPlayerMessenger multiPlayerMessenger = Messaging.Factory.makeMappingMultiPlayerMessenger(playerMapper, fxMessenger);

        MultiPlayer player = Factory.makeMessagingMultiPlayer(john, multiPlayerMessenger);
        player.add(haley);

        GameOverRule rule = Domain.Factory.makeGameOverRule(observableBoard);
        WinningLineProvider provider = Domain.Factory.makeWinningLineProvider(observableBoard);
        Game game = Factory.makeGame(rule, player, new NullRenderer());

        WinningLinePresenter winningLinePresenter = new WinningLinePresenter(fxBoard, provider);
        observableBoard.attach(winningLinePresenter);

        BoardViewPresenter boardPresenter = new BoardViewPresenter(observableBoard, fxBoard, new NullInputProcessor());
        listenableBoard.addListener(boardPresenter);

        GameLoopMessenger loopMessenger = Messaging.Factory.makeTicTacToeGameLoopMessenger(observableBoard, fxMessenger);
        GameLoop loop = Messaging.Factory.makeMessagingGameLoop(game, loopMessenger);

        primaryStage.setTitle("TicTacToe");
        primaryStage.setScene(new Scene(fxShell));
        primaryStage.setResizable(false);
        primaryStage.show();

        Thread t = new Thread() {
            public void run(){
                loop.run();
            }
        };

        t.start();

    }

}
