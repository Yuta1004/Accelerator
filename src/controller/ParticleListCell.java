package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

import particle.Vector3;

/**
 * Particle一覧を表示するListViewのカスタムセル
 */
public class ParticleListCell extends ListCell<ParticleStatData> {

    @FXML private HBox root;
    @FXML private Circle circle;
    @FXML private Label idLabel, initialPosLabel, initialVeloLabel;

    @Override
    public void updateItem(ParticleStatData data, boolean empty) {
        super.updateItem(data, empty);

        // 無効なデータ
        if(data == null || empty) {
            setText(null);
            setGraphic(null);
            return;
        }

        // FXMLLoaderの初期化
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ParticleListCell.fxml"));
        loader.setController(this);
        try{
            loader.load();
        } catch (Exception e) {
            e.printStackTrace();
            setText(null);
            setGraphic(null);
        }

        // 円の色指定
        Color c = data.isElectron ? Color.web("#1F93FFB1") : Color.web("FF7021B0");
        circle.setFill(c);

        // ラベル指定
        Vector3 pos = data.initialPosition, velocity = data.initialVelocity;
        idLabel.setText(String.format("%02d", data.id));
        initialPosLabel.setText(String.format("(%.2f, %.2f, %.2f)", pos.x, pos.y, pos.z));
        initialVeloLabel.setText(String.format("(%.2f, %.2f, %.2f)", velocity.x, velocity.y, velocity.z));

        setText(null);
        setGraphic(root);
    }

}