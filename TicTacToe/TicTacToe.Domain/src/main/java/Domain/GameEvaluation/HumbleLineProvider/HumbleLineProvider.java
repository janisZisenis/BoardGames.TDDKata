package Domain.GameEvaluation.HumbleLineProvider;

import Domain.Data.Field.Field;
import Domain.Data.Line.Line;
import Domain.GameEvaluation.GameEvaluator.LineProvider;

public class HumbleLineProvider implements LineProvider {

    private final Line[] lines = {
                //rows
                new Line(new Field(0, 0), new Field(0, 1), new Field(0, 2)),
                new Line(new Field(1, 0), new Field(1, 1), new Field(1, 2)),
                new Line(new Field(2, 0), new Field(2, 1), new Field(2, 2)),

                //columns
                new Line(new Field(0, 0), new Field(1, 0), new Field(2, 0)),
                new Line(new Field(0, 1), new Field(1, 1), new Field(2, 1)),
                new Line(new Field(0, 2), new Field(1, 2), new Field(2, 2)),

                //diagonals
                new Line(new Field(0, 0), new Field(1, 1), new Field(2, 2)),
                new Line(new Field(0, 2), new Field(1, 1), new Field(2, 0))
            };

    public int getLineCount() {
        return lines.length;
    }

    public Line getLine(int index) {
        throwIfIndexIsNotAvailable(index);
        return lines[index];
    }

    private void throwIfIndexIsNotAvailable(int index) {
        if(index > getLineCount())
            throw new LineIndexNotAvailable();
    }
}
