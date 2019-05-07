package SequentialRendering.BoardRenderer;

import Domain.Data.Line.Line;

public interface BoardRenderingView {
    void showBoard();
    void showWinningLine(Line line);
}
