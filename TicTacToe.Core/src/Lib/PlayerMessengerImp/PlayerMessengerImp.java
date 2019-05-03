package Lib.PlayerMessengerImp;

import Lib.Data.Field.Field;
import Lib.GameMessengerImp.Messenger;
import Lib.Players.MessagingPlayer.PlayerMessenger;

public class PlayerMessengerImp implements PlayerMessenger {

    private final Messenger messenger;
    private final MarkedFieldMessageProvider provider;

    public PlayerMessengerImp(Messenger messenger, MarkedFieldMessageProvider provider) {
        this.messenger = messenger;
        this.provider = provider;
    }

    public void publishPlayedMove(Field field) {
        String message = provider.getMarkedFieldMessage(field);
        messenger.publish(message);
    }

}
