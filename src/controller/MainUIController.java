package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.layout.AnchorPane;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

import builder.DisplayBuilder;
import builder.Builder3D;
import particle.ParticleManager;

public class MainUIController implements Initializable {

    // UI部品
    @FXML
    private AnchorPane displayPane;

    // 描画用
    private Timeline tl;
    private DisplayBuilder dbuilder;
    private ParticleManager pmanager;

    /**
     * UIの初期化を行う
     */
    @Override
    public void initialize(URL location, ResourceBundle resource) {
        changeDBuilder(new Builder3D());
        pmanager = new ParticleManager(0.1, 11, 11, 11);

        // Timelineの初期化(0.5秒周期)
        tl = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
            pmanager.update();
            dbuilder.update(pmanager.getParticles());
        }));
        tl.setCycleCount(Timeline.INDEFINITE);
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