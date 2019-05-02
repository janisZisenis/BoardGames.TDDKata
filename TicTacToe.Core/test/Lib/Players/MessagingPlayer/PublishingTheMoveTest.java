package Lib.Players.MessagingPlayer;

import Lib.Data.Field.Field;
import Lib.Data.Input.Input;
import Lib.Data.Mark;
import Lib.Players.CountingGeneratorStub;
import Lib.Players.MarkFieldServiceDummy;
import Lib.Players.PlayerContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PublishingTheMoveTest {

    private CountingGeneratorStub generator = new CountingGeneratorStub();
    private MarkFieldServiceDummy markService = new MarkFieldServiceDummy();
    private PlayerMessengerSpy messenger = new PlayerMessengerSpy();
    private MessagingPlayer sut;

    @Test
    void IfRow0Column1GetsMarked_ShouldPublishTheMarkedFieldWithRow0Column1() {
        makePlayerWillMarkRow0Column1();

        sut.playMove();

        Field actual = messenger.getPublishedField();
        Field expected = new Field(0, 1);
        assertEquals(expected, actual);
    }

    @Test
    void IfRow2Column2GetsMarked_ShouldPublishTheMarkedFieldWithRow2Column2() {
        makePlayerWillMarkRow2Column2();

        sut.playMove();

        Field actual = messenger.getPublishedField();
        Field expected = new Field(2, 2);
        assertEquals(expected, actual);
    }


    private void makePlayerWillMarkRow2Column2() {
        makePlayer();
        Input[] inputs = { new Input(2, 2) };
        generator.setGeneratedInputs(inputs);
    }


    private void makePlayerWillMarkRow0Column1() {
        makePlayer();
        Input[] inputs = { new Input(0, 1) };
        generator.setGeneratedInputs(inputs);
    }

    private void makePlayer() {
        PlayerContext context = new PlayerContext(generator, markService, Mark.John);
        sut = new MessagingPlayer(context, messenger);
    }

}
