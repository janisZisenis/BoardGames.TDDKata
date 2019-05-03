package View;

import Lib.Board.ReadOnlyBoard;
import Lib.BoardRenderer.BoardView;
import Lib.Data.BoardBoundaries;
import Lib.Data.Field.Field;
import Lib.Data.Line;
import Lib.Data.Mark;
import Messaging.Mapping.MarkToStringMapper;

public class ConsoleBoardView implements BoardView {

    private final String empty = "\u00B7";
    private final String winningColor = "\u001B[32m";
    private final String colorReset = "\u001B[0m";

    private final int rowColumn = BoardBoundaries.rowColumnCount;

    private final MarkToStringMapper mapper;
    private final ReadOnlyBoard board;

    public ConsoleBoardView(ReadOnlyBoard board, MarkToStringMapper mapper) {
        this.board = board;
        this.mapper = mapper;
    }

    public void showBoard() {
        StringBuilder board = new StringBuilder();
        board.append("  R 012  \n");
        board.append("C +-----+\n");

        for(int i = 0; i < rowColumn; i++)
            board.append(i + " | ").append(getRow(i)).append(" |\n");

        board.append("  +-----+");

        System.out.println(board);
    }

    private String getRow(int row) {
        StringBuilder s = new StringBuilder();

        for(int i = 0; i < rowColumn; i++) {
            Field f = new Field(i, row);
            s.append(getField(f));
        }

        return s.toString();
    }

    private boolean lineContains(Field f, Line line) {
        return f.equals(line.getFirst())
                || f.equals(line.getSecond())
                || f.equals(line.getThird());
    }

    private String getField(Field f) {
        return board.isMarked(f) ? mapper.map(board.getMarkAt(f)) : empty;
    }

    private String map(Mark m) {
        return mapper.map(m);
    }


    public void showWinningLine(Line line) {
        StringBuilder board = new StringBuilder();
        board.append("+-----+\n");

        for(int i = 0; i < rowColumn; i++)
            board.append("| " + getRow(i, line) + " |\n");

        board.append("+-----+");

        System.out.println(board);
    }

    private String getRow(int row, Line line) {
        StringBuilder s = new StringBuilder();

        for(int i = 0; i < rowColumn; i++) {
            Field f = new Field(i, row);
            String field = lineContains(f, line) ? getWinningField(f) : getField(f);
            s.append(field);
        }

        return s.toString();
    }

    private String getWinningField(Field f) {
        StringBuilder s = new StringBuilder();
        s.append(winningColor);
        s.append(getField(f));
        s.append(colorReset);

        return s.toString();
    }

}
