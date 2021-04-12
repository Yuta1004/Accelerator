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

import data.Settings;
import particle.Vector3;
import particle.Particle;
import particle.ParticleManager;

public class Builder3D implements DisplayBuilder {

    private Group root;
    private SubScene scene;
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
        set3DCamera(5, 0, 0, 45, 30);
    };

    /**
     * 構築済みSubSceneを返す
     *
     * @return SubScene
     */
    public SubScene getScene() {
        if(scene == null) {
            scene = new SubScene(root, 1026, 748);
            scene.setCamera(cam);
        }
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
        for(Sphere s : newerAddedModels) {
            s.setOpacity(0.6);
        }
        newerAddedModels.clear();

        // 新規粒子追加
        PhongMaterial materialE = genPhongMaterial(Color.web("#1F93FF"), 1.0);
        PhongMaterial materialP = genPhongMaterial(Color.web("#FF7021"), 1.0);
        for(Particle p : pmanager.getParticles()) {
            Vector3 pos = p.getPos();
            Sphere model = new Sphere(Settings.particleSize);
            model.setTranslateX(pos.x);
            model.setTranslateY(pos.y);
            model.setTranslateZ(pos.z);
            model.setDrawMode(DrawMode.FILL);
            model.setMaterial(p.Q == 1 ? materialP : materialE);
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
        Vector3 positions[] = {new Vector3(5.0, 0.0, 0.0), new Vector3(0.0, 5.0, 0.0), new Vector3(0.0, 0.0, 5.0)};
        Vector3 sizes[] = {new Vector3(5.0, 0.03, 0.03), new Vector3(0.03, 5.0, 0.03), new Vector3(0.03, 0.03, 5.0)};
        for(int idx = 0; idx < 3; ++ idx) {
            Sphere dot = new Sphere(0.1);
            Box axis = new Box(sizes[idx].x, sizes[idx].y, sizes[idx].z);
            PhongMaterial material = genPhongMaterial(idx == 0 ? Color.RED : idx == 1 ? Color.GREEN : Color.BLUE, 1.0);
            dot.setMaterial(material);
            dot.setDrawMode(DrawMode.FILL);
            dot.getTransforms().add(new Translate(positions[idx].x, positions[idx].y, positions[idx].z));
            axis.setMaterial(material);
            axis.setDrawMode(DrawMode.FILL);
            axis.getTransforms().add(new Translate(positions[idx].x/2, positions[idx].y/2, positions[idx].z/2));
            root.getChildren().addAll(dot, axis);
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