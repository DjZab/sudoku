package main.java.gui.javafx;

import main.java.values.RiddleValues;
import java.util.ArrayList;
import java.util.Random;
import javafx.beans.Observable;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

public class SudokuController {
  EventHandler<ActionEvent> action = menuChange();
  StringProperty[][] stringPropertyArray;
  RiddleValues ridVals;

  @FXML
  VBox mainBox;

  @FXML
  VBox gridBox;

  @FXML
  GridPane board;

  @FXML
  MenuItem easy;

  @FXML
  MenuItem medium;

  @FXML
  MenuItem hard;

  @FXML
  MenuItem defaulttheme;

  @FXML
  MenuItem darktheme;

  @FXML
  MenuItem pinkflamingo;

  @FXML
  MenuItem about;

  @FXML
  public void initialize() {
    setMenuAction();
  }

  /**
  * Creates an instance of ridVals, initialises stringPropertyArray and calls setDifficulty.
  * @param emptyFields int
  */
  private void setGridValues(int emptyFields) {
    this.ridVals = new RiddleValues();
    this.stringPropertyArray = this.ridVals.toStringPropertyArray();
    setDifficulty(emptyFields);
  }

  /**
  * Paints the sudoku-gridpane and binds the listener.
  */
  private void paintGrid() {
    for (int blockCol = 0; blockCol < 9; blockCol = blockCol + 3) {
      for (int blockRow = 0; blockRow < 9; blockRow = blockRow + 3) {
        GridPane block = new GridPane();
        block.getStyleClass().add("block");
        this.board.setHgrow(block, Priority.ALWAYS);
        this.board.setVgrow(block, Priority.ALWAYS);
        block.setMaxHeight(Double.MAX_VALUE);
        block.setMaxWidth(Double.MAX_VALUE);

        for (int col = blockCol; col < (blockCol + 3); col++) {
          for (int row = blockRow; row < (blockRow + 3); row++) {
            final RiddleTextField field = new RiddleTextField();
            block.setHgrow(field, Priority.ALWAYS);
            block.setVgrow(field, Priority.ALWAYS);
            field.setMaxHeight(Double.MAX_VALUE);
            field.setMaxWidth(Double.MAX_VALUE);
            //
            field.textProperty().bindBidirectional(this.stringPropertyArray[row][col]);
            fieldEntryListener(field);

            if (!field.getText().isEmpty()){
              field.getStyleClass().add("hint-field");
              field.setEditable(false);
            }
            else {
              field.getStyleClass().add("empty-field");
              field.setMaxLength(1);
              field.setRestrict("[1-9]");
            }

            block.setConstraints(field, col, row);
            block.getChildren().add(field);
          }
        }
        this.board.setConstraints(block, blockCol, blockRow);
        this.board.getChildren().add(block);
      }
    }
  }

  /**
  * Checks for invalid entries
  * @param field RestrictiveTextField
  */
  private void fieldEntryListener (final RiddleTextField field) {
    field.textProperty().addListener(new ChangeListener<String>() {
      GridPane board = this.board;

      /**
      * Initialises the listener
      * @param observableValue String
      * @param s String
      * @param s1 String
      */
      @Override
      public void changed(ObservableValue<? extends String> observableValue, String s, String s1) {
        int row = board.getRowIndex(field);
        int col = board.getColumnIndex(field);

        if (checkRow(row, col, s1)
        || checkColumn(row, col, s1)
        || checkBlock(field, row, col, s1)) {
          field.getStyleClass().remove("empty-field");
          field.getStyleClass().add("illegal-field");
        }
        else {
          field.getStyleClass().remove("illegal-field");
          field.getStyleClass().add("empty-field");
        }
      }
    });
  }

  /**
  * Checks the row if the value already exists.
  * @param  row int
  * @param  col int
  * @param  val String
  * @return     boolean
  */
  public boolean checkRow (int row, int col, String val) {
    boolean state = false;
    for (int c = 0; c < 9; c++) {
      if (this.stringPropertyArray[row][c].get().equals(val) && c != col && !val.isEmpty()) {
        state = true;
        return state;
      }
    }
    return state;
  }

  /**
  * Checks the column if the value already exists.
  * @param  row int
  * @param  col int
  * @param  val String
  * @return     boolean
  */
  public boolean checkColumn (int row, int col, String val) {
    boolean state = false;
    for (int r = 0; r < 9; r++) {
      if (this.stringPropertyArray[r][col].get().equals(val) && r != row && !val.isEmpty()) {
        state = true;
        return state;
      }
    }
    return state;
  }

  /**
  * Checks the block if the value already exists.
  * @param  field RiddleTextField
  * @param  row int
  * @param  col int
  * @param  val String
  * @return     boolean
  */
  public boolean checkBlock (RiddleTextField field, int row, int col, String val) {
    boolean state = false;
    Parent parent = field.getParent();
    ObservableList<Node> list = parent.getChildrenUnmodifiable();

    for (Node o : list) {
      int r = this.board.getRowIndex(o);
      int c = this.board.getColumnIndex(o);
      if (this.stringPropertyArray[r][c].get().equals(val) && !(r == row && c == col) && !val.isEmpty()){
        state = true;
        return state;
      }
    }
    return state;
  }

  /**
  * Has to be done...
  * @param emptyFields int
  */
  private void setDifficulty(int emptyFields) {
    Random rand = new Random();

    for (int i = 0; i < emptyFields; i++) {
      int row = rand.nextInt(9);
      int col = rand.nextInt(9);

      this.stringPropertyArray[row][col].set("");
    }
  }

  /**
  * Sets the stylesheet
  * @param style String
  */
  private void setTheme (String style) {
    String str = "/main/resources/".concat(style).concat(".css");
    this.mainBox.getStylesheets().clear();
    this.mainBox.getStylesheets().add(str);
  }

  /**
  * Installs the listener for the menu.
  */
  private void setMenuAction () {
    this.easy.setOnAction(action);
    this.medium.setOnAction(action);
    this.hard.setOnAction(action);
    this.defaulttheme.setOnAction(action);
    this.darktheme.setOnAction(action);
    this.pinkflamingo.setOnAction(action);
    this.about.setOnAction(action);
  }

  /**
  * EventHandler for the menu
  * @return EventHandler&lt;ActionEvent&gt;
  */
  private EventHandler<ActionEvent> menuChange() {
    return new EventHandler<ActionEvent>() {

      /**
      * Calls the methods for the menu-selection.
      * @param event ActionEvent
      */
      public void handle(ActionEvent event) {
        MenuItem mItem = (MenuItem) event.getSource();

        switch (mItem.getId()) {
          case "easy" :   setGridValues(45);
                          paintGrid();
                          break;
          case "medium" : setGridValues(55);
                          paintGrid();
                          break;
          case "hard" :   setGridValues(65);
                          paintGrid();
                          break;
          case "defaulttheme" : setTheme("defaulttheme");
                          break;
          case "darktheme" :  setTheme("darktheme");
                          break;
          case "pinkflamingo" : setTheme("pinkflamingo");
                          break;
          case "about" : System.out.println("About");
                          break;
          default     :   break;
        }
      }
    };
  }
}
