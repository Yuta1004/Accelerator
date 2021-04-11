package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.lang.Runnable;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Slider;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

import builder.DisplayBuilder;
import builder.Builder3D;
import particle.ParticleManager;

public class MainUIController implements Initializable {

    // UI部品
    @FXML private AnchorPane displayPane;
    @FXML private Slider cameraX, cameraY, cameraZ, cameraRH, cameraRV;

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
        pmanager = new ParticleManager(1.0, 11, 11, 11);
        pmanager.setElectricField(-1.0E6, -1.0E6, -1.0E6);
        pmanager.addElectron(0.01, 0.01, 0.01, 0.1, 0.1, 0.0);

        setupUIComponets();

        // Timelineの初期化(0.5秒周期)
        tl = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
            pmanager.update();
            dbuilder.update(pmanager.getParticles());
        }));
        tl.setCycleCount(Timeline.INDEFINITE);

        tl.play();
    }

    /**
     * UIのセットアップを行う
     */
    private void setupUIComponets() {
        // 3Dカメラ
        Runnable updateCamera3D = () -> {
            double x = cameraX.getValue();
            double y = cameraY.getValue();
            double z = -cameraZ.getValue();
            double rh = cameraRH.getValue();
            double rv = cameraRV.getValue();
            dbuilder.set3DCamera(x, y, z, rh, rv);
        };
        cameraX.valueProperty().addListener((__, oldV, newV) -> updateCamera3D.run());
        cameraY.valueProperty().addListener((__, oldV, newV) -> updateCamera3D.run());
        cameraZ.valueProperty().addListener((__, oldV, newV) -> updateCamera3D.run());
        cameraRH.valueProperty().addListener((__, oldV, newV) -> updateCamera3D.run());
        cameraRV.valueProperty().addListener((__, oldV, newV) -> updateCamera3D.run());
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