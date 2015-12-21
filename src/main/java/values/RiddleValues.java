/*
 * Project: Sudoku
 * $Header: $
 * Author:  Sebastian Strungies
 * Last Change:
 *    by:   $Author: $
 *    date: $Date:   $
 * Copyright (c): Sebastian Strungies, 2015
 */

package main.java.values;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RiddleValues {

  private int[][] intArr;

  /**
   * Calls setIntArr() which creates an instance of intArr with valid values.
   */
  public RiddleValues()
  {
    this.intArr = new int[9][9];
    setIntArr();
  }

  /**
   * Gets intArr
   * @return int[][]
   */
  public int[][] getIntArr() {
    return this.intArr;
  }

  /**
   * No real Setter
   * <br>Creates values for intArr.
   * <br>Loops itself through resetArray until valid values where created.
   * <br>(Backtracking with brute force)
   */
  private void setIntArr() {
    for (int row = 0; row < 9; row++) {
      List<Integer> list = createRandList();
      
      for (int col = 0; col < 9; col++) {
        Iterator<Integer> it = list.iterator();
        
        while (it.hasNext()) { 
          int val = it.next(); //
          
          if (checkColumn(col, val) && checkBlock(row, col, val)) {
            this.intArr[row][col] = val;
            it.remove();
            
            break;              
          }
          if (!it.hasNext()) {
            resetArray();
            
            return;
          } 
        }
      }
    }
    
    return;    
  }

  /**
   * Creates a new instance of intArr and calls setIntArr().
   */
  private void resetArray() {
    this.intArr = new int[9][9];

    setIntArr();
  }

  /**
   * Generates a random list of integers from 1 to 9.
   * @return List&lt;Integer&gt;
   */
  private List<Integer> createRandList() {
    List<Integer> list = new ArrayList<>();

    for (int i = 1; i < 10; i++){
      list.add(i);
    }

    Collections.shuffle(list);

    return list;
  }
  
  /**
   * Transforms intArr for an easy binding.
   * @return StringProperty[][]
   */
  public StringProperty[][] toStringPropertyArray() {
    StringProperty[][] stringPropertyArray = new StringProperty[9][9];
    
    for (int row = 0; row < 9; row++) {
      for (int col = 0; col < 9; col++) {
        StringProperty str = new SimpleStringProperty(String.valueOf(this.intArr[row][col]));
        
        stringPropertyArray[row][col] = str;
      }
    }
    
    return stringPropertyArray;
  }
  
  /**
   * Checks the column if the value already exists.
   * @param  col int
   * @param  val int
   * @return     boolean
   */
  public boolean checkColumn (int col, int val) {
    boolean state = true;

    for (int i = 0; i < 9; i++){
      if (val == this.intArr[i][col]){
        state = false;
        
        return state;
      }
    }

    return state;
  }

  /**
   * Checks the row if the value already exists.
   * @param  row int
   * @param  col int
   * @param  val int
   * @return     boolean
   */
  public boolean checkBlock (int row, int col, int val) {
    boolean state = true;
    int x = 0;
    int y = 0;
    int r = 0;
    int c = 0;

    switch (row) {
      case 0: case 1: case 2: r = 3;
        x = 0;
        break;
      case 3: case 4: case 5: r = 6;
        x = 3;
        break;
      case 6: case 7: case 8: r = 9;
        x = 6;
      break;  
    }

    switch (col) {
      case 0: case 1: case 2: c = 3;
        y = 0;
        break;
      case 3: case 4: case 5: c = 6;
        y = 3;
        break;
      case 6: case 7: case 8: c = 9;
        y = 6;
      break;  
    }

    for (int i = x; i < r; i++) {
      for (int j = y; j < c; j++) {
        if (val == this.intArr[i][j]) {
          state = false;
          
          return state;
        }
      }
    }

    return state;
  }
}