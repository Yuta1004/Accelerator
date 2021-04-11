package builder;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.PerspectiveCamera;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.DrawMode;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.geometry.Point3D;

import particle.Vector3;
import particle.Particle;
import particle.ParticleManager;

public class Builder3D implements DisplayBuilder {

    private Group root;
    private PerspectiveCamera cam;
    private ArrayList<Sphere> newerAddedModels;

    /**
     * Buidler3Dのコンストラクタ
     */
    public Builder3D() {
        root = new Group();
        reset();

        // カメラセットアップ
        cam = new PerspectiveCamera(true);
        cam.setFieldOfView(45.5);
        cam.setNearClip(1.0);
        cam.setFarClip(1000000.0);
        set3DCamera(30, 0, 0, 45, 30);
    };

    /**
     * 構築済みSubSceneを返す
     *
     * @return SubScene
     */
    public SubScene getScene() {
        SubScene scene = new SubScene(root, 1026, 748);
        scene.setCamera(cam);
        return scene;
    }

    /**
     * 3Dカメラ位置をセットする
     */
    public void set3DCamera(double x, double y, double z, double rh, double rv) {
        cam.getTransforms().clear();
        cam.getTransforms().addAll(
            new Rotate(-90.0, new Point3D(1, 0, 0)),    // z軸補正
            new Rotate(-rh-90, new Point3D(0, 1, 0)),   // H
            new Rotate(-rv, new Point3D(1, 0, 0)),      // V
            new Translate(y, z, -x)                      // 座標
        );
    }

    /**
     * 画面更新を行う
     *
     * @param particles 描画する粒子
     */
    public void update(ParticleManager pmanager) {
        // 直前に追加した粒子の色を落とす
        PhongMaterial material = genPhongMaterial(Color.DODGERBLUE, 0.6);
        for(Sphere s : newerAddedModels) {
            s.setMaterial(material);
        }
        newerAddedModels.clear();

        // 新規粒子追加
        material = genPhongMaterial(Color.DODGERBLUE, 1.0);
        for(Particle p : pmanager.getParticles()) {
            Vector3 pos = p.getPos();
            Sphere model = new Sphere(0.3);
            model.setTranslateX(pos.x);
            model.setTranslateY(pos.y);
            model.setTranslateZ(pos.z);
            model.setMaterial(material);
            model.setDrawMode(DrawMode.FILL);
            newerAddedModels.add(model);
            root.getChildren().add(model);
        }
    }

    /**
     * 表示内容のリセットを行う
     */
    public void reset() {
        root.getChildren().clear();
        newerAddedModels = new ArrayList<Sphere>();

        // 軸モデル
        Vector3 positions[] = {new Vector3(15.0, 0.0, 0.0), new Vector3(0.0, 15.0, 0.0), new Vector3(0.0, 0.0, 15.0)};
        Vector3 sizes[] = {new Vector3(30.0, 0.05, 0.05), new Vector3(0.05, 30.0, 0.05), new Vector3(0.05, 0.05, 30.0)};
        for(int idx = 0; idx < 3; ++ idx) {
            Sphere dot = new Sphere(0.2);
            Box axis = new Box(sizes[idx].x, sizes[idx].y, sizes[idx].z);
            PhongMaterial material = genPhongMaterial(idx == 0 ? Color.RED : idx == 1 ? Color.GREEN : Color.BLUE, 1.0);
            dot.getTransforms().add(new Translate(positions[idx].x, positions[idx].y, positions[idx].z));
            dot.setMaterial(material);
            dot.setDrawMode(DrawMode.FILL);
            axis.setMaterial(material);
            axis.setDrawMode(DrawMode.FILL);
            root.getChildren().addAll(axis, dot);
        }
    }

    /**
     * 指定色でPhongMaterialを生成する
     */
    private PhongMaterial genPhongMaterial(Color c, double trans) {
        c = Color.web(c.toString(), trans);
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(c);
        material.setSpecularColor(c.brighter());
        return material;
    }

    /* 未使用メソッド */
    public void set2DCamera(double widthS, double widthF, double heightS, double heightF){}

}