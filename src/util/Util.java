package util;

import javafx.scene.control.TextField;

public class Util {

    /**
     * TextFieldの値を読みつつ、有効な入力でなかった場合には修正を行う
     *
     * @param target 読み取り対象のTextField
     * @param fixValue 修正値
     * @return double 読み取った値、修正が行われた場合にはfixValue
     */
    public static double readTextFieldAsDouble(TextField target, double fixValue) {
        try {
            return Double.parseDouble(target.getText());
        } catch (Exception e) {
            target.setText(""+fixValue);
            return fixValue;
        }
    }

}