package sudoku.gui.javafx;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class RiddleTextField extends TextField {
  private IntegerProperty maxLength = new SimpleIntegerProperty(this, "maxLength", -1);
  private StringProperty restrict = new SimpleStringProperty(this, "restrict");

  /**
  * <br> Installs a changeListener&lt;String&gt; on a textProperty, which checks the keyboard entries 
  * <br> for invalid values and restricts them.
  */
  public RiddleTextField()
  {
    textProperty().addListener(new ChangeListener<String>() {
      private boolean ignore;

      @Override
      public void changed(ObservableValue<? extends String> val, String oldStr, String newStr) {
        if (ignore || newStr == null)
        return;
        if (maxLength.get() > -1 && newStr.length() > maxLength.get()) {
          ignore = true;
          setText(newStr.substring(0, maxLength.get()));
          ignore = false;
        }
        if (restrict.get() != null && !restrict.get().equals("") && !newStr.matches(restrict.get() + "*")) {
          ignore = true;
          setText(oldStr);
          ignore = false;
        }
      }
    });
  }

  /**
  * Property for the max allowed length.
  * @return IntegerProperty
  */
  public IntegerProperty maxLengthProperty() {
    return maxLength;
  }

  /**
  * Gets the max allowed length.
  * @return int
  */
  public int getMaxLength() {
    return maxLength.get();
  }

  /**
  * Sets the max allowed length.
  * @param maxLength int
  */
  public void setMaxLength(int maxLength) {
    this.maxLength.set(maxLength);
  }

  /**
  * Property for the legal values.
  * @return StringProperty
  */
  public StringProperty restrictProperty() {
    return restrict;
  }

  /**
  * Gets the legal values.
  * @return String
  */
  public String getRestrict() {
    return restrict.get();
  }

  /**
  * Sets the legal values.
  * @param restrict String
  */
  public void setRestrict(String restrict) {
    this.restrict.set(restrict);
  }
}
