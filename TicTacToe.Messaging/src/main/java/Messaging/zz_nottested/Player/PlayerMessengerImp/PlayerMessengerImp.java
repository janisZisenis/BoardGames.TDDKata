package Messaging.zz_nottested.Player.PlayerMessengerImp;

import Domain.Data.Field.Field;
import Messaging.MappingMultiTurnMessenger.Messenger;
import Messaging.MessagingBoardListener.MarkedFieldMessageProvider;
import Messaging.zz_nottested.Player.MessagingPlayer.PlayerMessenger;

public class PlayerMessengerImp implements PlayerMessenger {

    private final Messenger messenger;
    private final MarkedFieldMessageProvider provider;

    public PlayerMessengerImp(Messenger messenger, MarkedFieldMessageProvider provider) {
        this.messenger = messenger;
        this.provider = provider;
    }

    public void publishMarkedField(Field f) {
        String message = provider.getMarkedFieldMessage(f);
        messenger.publish(message);
    }

}
