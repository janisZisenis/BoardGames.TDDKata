package com.company.TicTacToe.ObservableBoard;

import com.company.TicTacToe.Field.Field;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardObservingTest {

    private Board board = new BoardDummy();
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
