package com.company.TicTacToeBoard;

import com.company.IsOnBoardValidatorImp.Field;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

public class FieldIsEmptyTest {

    private TicTacToeBoard sut = new TicTacToeBoard();
    private Field first = new Field(1, 2);
    private Field second = new Field(0, 1);
    private Field third = new Field(2, 1);
    private Field fourth = new Field(2, 2);

    @Test
    void FreshInstance_FieldShouldBeEmpty() {
        boolean actual = sut.isEmpty(first);
        assertTrue(actual);
    }

    @Test
    void IfOneFieldIsMarked_ItShouldNotBeEmpty() {
        makeOneFieldMarked();

        boolean actual = sut.isEmpty(first);
        assertFalse(actual);
    }

    @Test
    void IfOneFieldIsMarked_ASecondFieldShouldBeEmpty() {
        makeOneFieldMarked();

        boolean actual = sut.isEmpty(second);
        assertTrue(actual);
    }

    @Test
    void IfTwoFieldsAreMarked_FirstFieldShouldNotBeEmpty() {
        makeTwoFieldsMarked();

        boolean actual = sut.isEmpty(first);
        assertFalse(actual);
    }

    @Test
    void IfTwoFieldsAreMarked_SecondFieldShouldNotBeEmpty() {
        makeTwoFieldsMarked();

        boolean actual = sut.isEmpty(second);
        assertFalse(actual);
    }

    @Test
    void IfTwoFieldsAreMarked_ThirdFieldShouldBeEmpty() {
        makeTwoFieldsMarked();

        boolean actual = sut.isEmpty(third);
        assertTrue(actual);
    }

    @Test
    void IfThreeFieldsAreMarked_ThirdFieldShouldNotBeEmpty() {
        markThreeFields();

        boolean actual = sut.isEmpty(third);
        assertFalse(actual);
    }

    @Test
    void IfThreeFieldsAreMarked_FourthFieldShouldBeEmpty() {
        markThreeFields();

        boolean actual = sut.isEmpty(fourth);
        assertTrue(actual);
    }

    @Test
    void IfRowIsNegative_ShouldThrowException() {
        Field field = new Field(-1, 0);

        Executable act = () -> sut.isEmpty(field);

        assertThrows(TicTacToeBoard.FieldDoesNotExist.class, act);
    }

    @Test
    void IfColumnIsNegative_ShouldThrowException() {
        Field field = new Field(0, -1);

        Executable act = () -> sut.isEmpty(field);

        assertThrows(TicTacToeBoard.FieldDoesNotExist.class, act);
    }

    @Test
    void IfRowIsBiggerThan2_ShouldThrowException() {
        Field field = new Field(3, 0);

        Executable act = () -> sut.isEmpty(field);

        assertThrows(TicTacToeBoard.FieldDoesNotExist.class, act);
    }

    @Test
    void IfColumnIsBiggerThan2_ShouldThrowException() {
        Field field = new Field(0, 3);

        Executable act = () -> sut.isEmpty(field);

        assertThrows(TicTacToeBoard.FieldDoesNotExist.class, act);
    }



    private void makeOneFieldMarked() {
        sut.mark(first, Mark.X);
    }

    private void makeTwoFieldsMarked() {
        makeOneFieldMarked();
        sut.mark(second, Mark.X);
    }

    private void markThreeFields() {
        makeTwoFieldsMarked();
        sut.mark(third, Mark.X);
    }

}
