package builder;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.chart.XYChart;

import data.Settings;
import particle.Vector3;
import particle.Particle;
import particle.ParticleManager;

public class Builder2DXY extends Builder2D {

    /**
     * Builder2DXYのコンストラクタ
     */
    public Builder2DXY() {
        super();

        hAxis.setLabel("X-Axis");
        vAxis.setLabel("Y-Axis");
    }

    /**
     * 画面更新を行う
     *
     * @param pmanager 描画する粒子を管理するParticleManager
     */
    @Override
    public void update(ParticleManager pmanager) {
        XYChart.Series<Number, Number> series = data.get(0);

        // 古いデータは色を薄く小さくする
        for(XYChart.Data<Number, Number> oldD : series.getData()) {
            Node node = oldD.getNode();
            node.setScaleX(0.7);
            node.setScaleY(0.7);
            node.setOpacity(0.5);
        }

        // 新規粒子追加
        for(Particle p : pmanager.getParticles()) {
            Vector3 pos = p.getPos();
            XYChart.Data<Number, Number> newD = new XYChart.Data<Number, Number>(pos.x, pos.y);
            newD.setNode(new Circle(Settings.particleSize*30, Color.web(Color.DODGERBLUE.toString(), 1.0)));
            series.getData().add(newD);
        }

        data.set(0, series);
    }

}