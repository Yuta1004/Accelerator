package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.layout.AnchorPane;

import builder.DisplayBuilder;

public class MainUIController implements Initializable {

    // UI部品
    @FXML
    private AnchorPane displayPane;

    // 描画用
    private DisplayBuilder dbuilder;

    @Override
    public void initialize(URL location, ResourceBundle resource) {
    }

    /**
     * 画面描画構成の変更を行う
     */
    private void changeDBuilder(DisplayBuilder newBuilder) {
        SubScene oldScene = dbuilder == null ? null : dbuilder.getScene();
        SubScene newScene = newBuilder.getScene();

        // AnchorPane設定 (全方向のAnchor->0.0)
        AnchorPane.setTopAnchor(newScene, 0.0);
        AnchorPane.setBottomAnchor(newScene, 0.0);
        AnchorPane.setLeftAnchor(newScene, 0.0);
        AnchorPane.setRightAnchor(newScene, 0.0);
        displayPane.getChildren().remove(oldScene);
        displayPane.getChildren().add(newScene);

        dbuilder = newBuilder;
    }

}