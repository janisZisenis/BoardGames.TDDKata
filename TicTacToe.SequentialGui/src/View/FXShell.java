package View;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class FXShell extends Pane {

    public FXShell(FXBoardView board, FXInputView input, FXMessenger messenger) {
        GridPane grid = makeGrid();

        GridPane.setConstraints(board, 0, 0);
        GridPane.setConstraints(input, 1, 0);
        GridPane.setConstraints(messenger, 0, 1, 2, 1);

        grid.getChildren().addAll(board, input, messenger);

        getChildren().add(grid);
    }

    private GridPane makeGrid() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);
        return grid;
    }

}
