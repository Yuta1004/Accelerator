package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import util.Util;
import data.Settings;

public class SettingsUIController implements Initializable {

    @FXML private CheckBox normalizeAxis;
    @FXML private TextField DT, meshN;
    @FXML private Slider particleSize, mouseSensitivity, updateTickS;

    /**
     * UIの初期化
     */
    @Override
    public void initialize(URL location, ResourceBundle resource) {
        // 値セット
        DT.setText(Settings.DT+"");
        updateTickS.setValue(1050-Settings.updateTickS);
        particleSize.setValue(Settings.particleSize);
        mouseSensitivity.setValue(Settings.mouseSensitivity);
        normalizeAxis.setSelected(Settings.normalizeAxis);
        meshN.setText(Settings.meshN+"");

        // リスナー
        DT.textProperty().addListener(event -> Settings.DT = Util.readTextFieldAsDouble(DT, 0.5));
        updateTickS.valueProperty().addListener((__, oldV, newV) -> Settings.updateTickS = 1050-newV.doubleValue());
        particleSize.valueProperty().addListener((__, oldV, newV) -> Settings.particleSize = newV.doubleValue());
        mouseSensitivity.valueProperty().addListener((__, oldV, newV) -> Settings.mouseSensitivity = newV.doubleValue());
        normalizeAxis.setOnAction(event -> Settings.normalizeAxis = normalizeAxis.isSelected());
        meshN.textProperty().addListener(event -> Settings.meshN = Util.readTextFieldAsDouble(meshN, 20));
    }

}