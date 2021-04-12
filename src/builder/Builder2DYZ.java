package builder;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.chart.XYChart;

import particle.Vector3;
import particle.Particle;
import particle.ParticleManager;

public class Builder2DYZ extends Builder2D {

    /**
     * Builder2DXYのコンストラクタ
     */
    public Builder2DYZ() {
        super();

        hAxis.setLabel("Y-Axis");
        vAxis.setLabel("Z-Axis");
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
            XYChart.Data<Number, Number> newD = new XYChart.Data<Number, Number>(pos.y, pos.z);
            newD.setNode(new Circle(10.0, Color.web(Color.DODGERBLUE.toString(), 1.0)));
            series.getData().add(newD);
        }

        data.set(0, series);
    }

}