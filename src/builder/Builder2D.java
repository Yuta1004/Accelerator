package builder;

import javafx.scene.SubScene;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import data.Settings;
import particle.ParticleManager;

public abstract class Builder2D implements DisplayBuilder {

    private SubScene scene;
    private ScatterChart<Number, Number> chart;
    protected NumberAxis hAxis, vAxis;
    protected ObservableList<XYChart.Series<Number, Number>> data;

    /**
     * Builder2Dのコンストラクタ
     */
    public Builder2D() {
        data = FXCollections.observableArrayList();
        data.add(new XYChart.Series<Number, Number>());

        hAxis = new NumberAxis();
        vAxis = new NumberAxis();
        chart = new ScatterChart<Number, Number>(hAxis, vAxis);
        chart.setAnimated(false);
        chart.setLegendVisible(false);
        chart.setData(data);
        set2DCamera(0, 1.5, 0, 1.5);
    }

    /**
     * 構築済みSubSceneを返す
     *
     * @return SubScene
     */
    public SubScene getScene() {
        if(scene == null) {
            scene = new SubScene(chart, 1026, 748);
        }
        return scene;
    }

    /**
     * 2Dカメラ位置をセットする (=グラフの表示範囲をセット)
     */
    public void set2DCamera(double horizontalS, double horizontalF, double verticalS, double verticalF) {
        // 軸正規化
        if(Settings.normalizeAxis) {
            double waSize = ((Number)chart.getXAxis().getWidth()).doubleValue();
            double haSize = ((Number)chart.getYAxis().getHeight()).doubleValue();
            double aratio = haSize+waSize == 0 ? 0.7035 : haSize/waSize;
            double baseSize = horizontalF-horizontalS;
            verticalF = verticalS+baseSize*aratio;
        }

        hAxis.setAutoRanging(false);
        hAxis.setTickUnit(0.2);
        hAxis.setLowerBound(horizontalS);
        hAxis.setUpperBound(Math.max(horizontalS, horizontalF));

        vAxis.setAutoRanging(false);
        vAxis.setTickUnit(0.2);
        vAxis.setLowerBound(verticalS);
        vAxis.setUpperBound(Math.max(verticalS, verticalF));
    }

    /**
     * 表示内容をリセットする
     */
    public void reset() {
        data.clear();
        data.add(new XYChart.Series<Number, Number>());
    }

    /* 子クラスで定義してもらうメソッド */
    public void update(ParticleManager pmanager) {}

    /* 未使用メソッド */
    public void set3DCamera(double x, double y, double z, double rh, double rv) {}

}