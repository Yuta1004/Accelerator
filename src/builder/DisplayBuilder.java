package builder;

import java.util.ArrayList;

import javafx.scene.SubScene;

import particle.Particle;

public interface DisplayBuilder {

    public SubScene getScene();
    public void set2DCamera(double widthS, double widthF, double heightS, double heightF);
    public void set3DCamera(double x, double y, double z, double rh, double rv);
    public void update(ArrayList<Particle> particles);

}