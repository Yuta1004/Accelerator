package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.lang.Runnable;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

import builder.DisplayBuilder;
import builder.Builder3D;
import particle.Vector3;
import particle.ParticleManager;

public class MainUIController implements Initializable {

    // UI部品
    @FXML private AnchorPane displayPane;
    @FXML private Label time, timeE;
    @FXML private Tab cameraTab3D;
    @FXML private TabPane cameraTab;
    @FXML private Slider cameraX, cameraY, cameraZ, cameraRH, cameraRV;
    @FXML private Button playBtn, initBtn, resetBtn, nextBtn;
    @FXML private ListView<ParticleStatData> particleList;

    // 描画用
    private Timeline tl;
    private DisplayBuilder dbuilder;
    private ParticleManager pmanager;

    // カメラ操作用
    private Vector3 oldMousePos;
    private double sensitivity = 0.01;

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
            time.setText(String.format("%.12f", pmanager.getTime()));
            timeE.setText(String.format("%.2E", pmanager.getTime()));
            dbuilder.update(pmanager.getParticles());
        }));
        tl.setCycleCount(Timeline.INDEFINITE);
    }

    /**
     * UIのセットアップを行う
     */
    private void setupUIComponets() {
        // コントロールパネル
        playBtn.setOnAction(event -> {
            if(playBtn.getText().equals("再生")) {
                playBtn.setText("停止");
                tl.play();
            } else {
                playBtn.setText("再生");
                tl.stop();
            }
        });
        initBtn.setOnAction(event -> {
            tl.stop();
            playBtn.setText("再生");
            time.setText("0.0000000000");
            timeE.setText("0.00E-11");
            pmanager = new ParticleManager(1.0, 11, 11, 11);
            dbuilder.reset();
        });
        resetBtn.setOnAction(event -> {
            tl.stop();
            playBtn.setText("再生");
            time.setText("0.0000000000");
            timeE.setText("0.00E-11");
            // pmanager.reset();
            dbuilder.reset();
        });
        nextBtn.setOnAction(event -> {
            pmanager.update();
            dbuilder.update(pmanager.getParticles());
        });

        // 粒子一覧を表示するListView
        particleList.setCellFactory(__ -> new ParticleListCell());

        // 2D/3Dカメラ操作タブ
        cameraTab.getStyleClass().add("floating");
        cameraTab.getSelectionModel().select(cameraTab3D);

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

        // 3Dカメラ(マウス操作)
        displayPane.setOnMousePressed((event) -> oldMousePos = new Vector3(event.getX(), event.getY(), 0.0));
        displayPane.setOnMouseDragged((event) -> {
            double diffX = -(event.getX()-oldMousePos.x) * sensitivity;
            double diffY =  (event.getY()-oldMousePos.y) * sensitivity;
            cameraRH.setValue((cameraRH.getValue()+diffX+360) % 360);
            cameraRV.setValue((cameraRV.getValue()+diffY+360) % 360);
            updateCamera3D.run();
        });
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