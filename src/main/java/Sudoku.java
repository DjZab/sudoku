package sudoku;

/*
 * Project: Sudoku
 * $Header: $
 * Author:  Sebastian Strungies
 * Last Change:
 *    by:   $Author: $
 *    date: $Date:   $
 * Copyright (c): Sebastian Strungies, 2015
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Sudoku extends Application {

  public Sudoku()
  {

  }

  /**
   * Main-method of Sudoku
   * @param args String[]
   */
  public static void main (String[] args) {
    launch(args);
  }

  /**
   * Generates the layout
   * <br>The controller is initialised through sudoku.fxml.
   * @param  stage     Stage
   * @throws Exception Exception
   */
  @Override
  public void start(Stage stage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("/resources/sudoku.fxml"));

    stage.setScene(new Scene(root, 420, 440));
    stage.setTitle("Sudoku");
    stage.show();
  }
}
