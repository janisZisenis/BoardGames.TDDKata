package com.company.TicTacToe.TicTacToeWinningLineProviderTest;

import com.company.TicTacToe.Board.Field.Field;
import com.company.TicTacToe.Board.Mark;
import com.company.TicTacToe.BoardPresenter.WinningLineProvider;
import com.company.TicTacToe.GameOverRules.WinningLineRule.LineEvaluatorStub;
import com.company.TicTacToe.GameOverRules.WinningLineRule.LineProviderStub;
import com.company.TicTacToe.Line;
import com.company.TicTacToe.TicTacToeWinningLineProvider.TicTacToeWinningLineProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

public class TwoLinesAreProvided {

    private LineProviderStub provider = new LineProviderStub();
    private LineEvaluatorStub evaluator = new LineEvaluatorStub();
    private TicTacToeWinningLineProvider sut = new TicTacToeWinningLineProvider(provider, evaluator);

    private Line[] lines = {};

    @BeforeEach
    void SetUp() {
        Line first = new Line(new Field(0, 0), new Field(0, 1), new Field(0, 2));
        Line second = new Line(new Field(0, 0), new Field(1, 1), new Field(2, 2));
        lines = new Line[]{ first, second };

        provider.setProvidedLines(lines);
    }

    @Test
    void IfNoneLineHasWinner_ShouldNotHaveAWinningLine(){
        boolean actual = sut.hasWinningLine();

        assertFalse(actual);
    }

    @Test
    void IfSecondHasWinner_ShouldHaveAWinningLine(){
        evaluator.setWinnerForLine(Mark.John, lines[1]);

        boolean actual = sut.hasWinningLine();

        assertTrue(actual);
    }

    @Test
    void IfSecondLineHasWinner_ShouldProvideTheSecondAsWinningLine() {
        evaluator.setWinnerForLine(Mark.John, lines[1]);

        Line actual = sut.getWinningLine();

        Line expected = lines[1];
        assertEquals(expected, actual);
    }

    @Test
    void IfBothLinesHaveWinner_ShouldProvideTheFirstAsWinningLine(){
        evaluator.setWinnerForLine(Mark.John, lines[0]);
        evaluator.setWinnerForLine(Mark.Haley, lines[1]);

        Line actual = sut.getWinningLine();

        Line expected = lines[0];
        assertEquals(expected, actual);
    }

    @Test
    void IfNoneLineHasWinner_GettingTheWinningLineShouldThrow() {
        Executable act = () -> sut.getWinningLine();

        assertThrows(WinningLineProvider.NoWinningLineAvailable.class, act);
    }



    @Test
    void IfSecondLineHasWinner_ShouldHaveAWinner(){
        evaluator.setWinnerForLine(Mark.John, lines[1]);

        boolean actual = sut.hasWinner();

        assertTrue(actual);
    }

    @Test
    void IfNoneLineHasWinner_ShouldNotHaveAWinner(){
        boolean actual = sut.hasWinner();

        assertFalse(actual);
    }

    @Test
    void IfJohnWinsWithSecondLine_ShouldProvideJohnAsWinner() {
        evaluator.setWinnerForLine(Mark.John, lines[1]);

        Mark actual = sut.getWinner();

        Mark expected = Mark.John;
        assertEquals(expected, actual);
    }

    @Test
    void IfHaleyWinsWithSecondLine_ShouldProvideHaleyAsWinner() {
        evaluator.setWinnerForLine(Mark.Haley, lines[1]);

        Mark actual = sut.getWinner();

        Mark expected = Mark.Haley;
        assertEquals(expected, actual);
    }

    @Test
    void IfNoneLineIsWinning_GettingTheWinnerShouldThrow() {
        Executable act = () -> sut.getWinner();

        assertThrows(WinningLineProvider.NoWinnerAvailable.class, act);
    }

}
