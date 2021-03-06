package Domain.Board.BoardDecorators.ObservableBoard;

import Domain.Board.BoardDummy;
import Domain.Data.Field.Field;
import Domain.Data.Mark;
import Utilities.Observer.ObserverSpy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NotifyBoardObserverTest {

    private BoardDummy board = new BoardDummy();
    private ObserverSpy[] observers = {};
    private ObservableBoard sut = new ObservableBoard(board);

    private Field field = new Field(0, 1);
    private Mark mark = Mark.John;

    @Test
    void IfOneObserverIsAttached_ItShouldBeUpdatedWhenMarkingAField() {
        makeOneObserverAttached();

        sut.mark(field, mark);

        boolean actual = observers[0].wasUpdated();
        assertTrue(actual);
    }

    @Test
    void IfTwoObserversAreAttached_FirstShouldBeUpdatedWhenMarkingAField() {
        makeTwoObserversAttached();

        sut.mark(field, mark);

        boolean actual = observers[1].wasUpdated();
        assertTrue(actual);
    }

    @Test
    void IfTwoObserversAreAttached_SecondShouldBeUpdatedWhenMarkingAField() {
        makeTwoObserversAttached();

        sut.mark(field, mark);

        boolean actual = observers[1].wasUpdated();
        assertTrue(actual);
    }

    @Test
    void IfSecondObserverIsDetachedAfterBeingAttached_ItShouldNotBeUpdatedWhenMarkingAField() {
        makeSecondObserverIsDetachedAfterBeingAttached();

        sut.mark(field, mark);

        boolean actual = observers[1].wasUpdated();
        assertFalse(actual);
    }


    @Test
    void IfOneObserverIsAttached_ItShouldBeUpdatedWhenClearing() {
        makeOneObserverAttached();

        sut.clear();

        boolean actual = observers[0].wasUpdated();
        assertTrue(actual);
    }


    private void makeSecondObserverIsDetachedAfterBeingAttached() {
        makeTwoObserversAttached();
        sut.detach(observers[1]);
    }

    private void makeOneObserverAttached() {
        observers = new ObserverSpy[]{ new ObserverSpy() };
        sut.attach(observers[0]);
    }

    private void makeTwoObserversAttached() {
        observers = new ObserverSpy[]{new ObserverSpy(),
                new ObserverSpy()};
        sut.attach(observers[0]);
        sut.attach(observers[1]);
    }

}
