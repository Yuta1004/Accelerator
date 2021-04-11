package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.RadioButton;

public class AddParticleUIController implements Initializable {

    // UI部品
    @FXML Button done;
    @FXML Label errorMsg;
    @FXML RadioButton electron, proton;
    @FXML TextField inpX, inpY, inpZ, inpVx, inpVy, inpVz;

    // 呼び出し側と共有する値
    public boolean inpOk = false;
    public boolean setElectron;
    public double x, y, z, vx, vy, vz;

    /**
     * UIの初期化を行う
     */
    @Override
    public void initialize(URL location, ResourceBundle resource) {
        done.setOnAction(event -> {
            if(!checkInput()){
                errorMsg.setVisible(true);
                return;
            }

            // 入力値取り出し
            inpOk = true;
            setElectron = electron.isSelected();
            x = Double.parseDouble(inpX.getText());
            y = Double.parseDouble(inpY.getText());
            z = Double.parseDouble(inpZ.getText());
            vx = Double.parseDouble(inpVx.getText());
            vy = Double.parseDouble(inpVy.getText());
            vz = Double.parseDouble(inpVz.getText());
            done.getScene().getWindow().hide();
        });
    }

    /**
     * 入力されている値が適切な数値かどうか検証する
     */
    private boolean checkInput() {
        return __isValidatedDouble(inpX.getText()) &&
               __isValidatedDouble(inpY.getText()) &&
               __isValidatedDouble(inpZ.getText()) &&
               __isValidatedDouble(inpVx.getText()) &&
               __isValidatedDouble(inpVy.getText()) &&
               __isValidatedDouble(inpVz.getText());
    }

    private boolean __isValidatedDouble(String inpValue) {
        try {
            Double.parseDouble(inpValue);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
