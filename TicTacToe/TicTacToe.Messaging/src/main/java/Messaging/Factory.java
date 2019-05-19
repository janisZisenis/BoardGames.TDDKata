package Messaging;

import Domain.Board.Board;
import Domain.GameEvaluation.GameEvaluator.Api.WinnerProvider;
import GameLoopMessengerImp.GameLoopMessengerImp;
import Gaming.GameLoopImp.Api.GameLoop;
import Gaming.GameLoopImp.Game;
import Gaming.MultiPlayer.MultiPlayerMessenger;
import MappingPlayerMessenger.MappingMultiPlayerMessenger;
import MappingPlayerMessenger.Messenger;
import MessageProviders.FixedMessageProvider.FixedMessageProvider;
import MessageProviders.GameOverMessageProvider.GameOverMessageProvider;
import Messages.TicTacToeMessages;
import Messaging.MarkToStringMappers.DefaultMarkToStringMapper;
import Messaging.WinnerMessageProviderImp.WinnerMessageProviderImp;
import MessagingGameLoop.GameLoopMessenger;
import Utilities.ObjectToStringMapper.Api.ObjectToStringMapper;
import MessagingGameLoop.MessagingGameLoop;

public abstract class Factory {

    public static MultiPlayerMessenger makeMappingMultiPlayerMessenger(ObjectToStringMapper mapper, Messenger messenger) {
        return new MappingMultiPlayerMessenger(mapper, messenger);
    }

    public static GameLoopMessenger makeTicTacToeGameLoopMessenger(Board board, Messenger messenger) {
        FixedMessageProvider startProvider = new FixedMessageProvider(TicTacToeMessages.salutation);

        WinnerProvider winnerProvider = Domain.Factory.makeWinnerProvider(board);
        DefaultMarkToStringMapper mapper = new DefaultMarkToStringMapper(TicTacToeMessages.humanWinMessage, TicTacToeMessages.computerWinMessage);
        WinnerMessageProviderImp winnerMessageProvider = new WinnerMessageProviderImp(winnerProvider, mapper);
        FixedMessageProvider drawMessageProvider = new FixedMessageProvider(TicTacToeMessages.drawMessage);
        GameOverMessageProvider endProvider = new GameOverMessageProvider(winnerMessageProvider, drawMessageProvider);

        return new GameLoopMessengerImp(messenger, startProvider, endProvider);
    }

    public static GameLoop makeMessagingGameLoop(Game game, GameLoopMessenger messenger) {
        GameLoop loop = Gaming.Factory.makeGameLoop(game);
        return new MessagingGameLoop(loop, messenger);
    }
}