package com.company.TicTacToe.FieldExistsValidator;

import com.company.Core.InputGeneration.Input.Input;
import com.company.Core.InputGeneration.InputValidator;
import com.company.TicTacToe.Constants.BoardBoundaries;

public class FieldExistsValidator implements InputValidator {

    private final int rowColumnCount = BoardBoundaries.rowColumnCount;

    public boolean isValid(Input input) {
        int row = input.getRow();
        int col = input.getColumn();

        return isOutOfBounds(row) && isOutOfBounds(col);
    }

    private boolean isOutOfBounds(int i) {
        return i >= 0 && i < rowColumnCount;
    }

}
