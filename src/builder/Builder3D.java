package builder;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.PerspectiveCamera;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.DrawMode;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;

import particle.Vector3;
import particle.Particle;

public class Builder3D implements DisplayBuilder {

    private Group root;
    private PerspectiveCamera cam;
    private ArrayList<Sphere> newerAddedModels;

    /**
     * Buidler3Dのコンストラクタ
     */
    public Builder3D() {
        root = new Group();
        newerAddedModels = new ArrayList<Sphere>();

        // カメラセットアップ
        cam = new PerspectiveCamera(true);
        cam.setFieldOfView(45.5d);
        cam.setNearClip(1.0d);
        cam.setFarClip(1000000.0d);
        cam.setTranslateZ(-20.0d);
    }

    /**
     * 構築済みSubSceneを返す
     *
     * @return SubScene
     */
    public SubScene getScene() {
        SubScene scene = new SubScene(root, 960, 720);
        scene.setCamera(cam);
        return scene;
    }

    /**
     * 画面更新を行う
     *
     * @param particles 描画する粒子
     */
    public void update(ArrayList<Particle> particles) {
        // 直前に追加した粒子の色を落とす
        PhongMaterial material = genPhongMaterial(Color.DODGERBLUE, 0.6);
        for(Sphere s : newerAddedModels) {
            s.setMaterial(material);
        }
        newerAddedModels.clear();

        // 新規粒子追加
        material = genPhongMaterial(Color.DODGERBLUE, 1.0);
        for(Particle p : particles) {
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
     * 指定色でPhongMaterialを生成する
     */
    private PhongMaterial genPhongMaterial(Color c, double trans) {
        c = Color.web(c.toString(), trans);
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(c);
        material.setSpecularColor(c.brighter());
        return material;
    }

}