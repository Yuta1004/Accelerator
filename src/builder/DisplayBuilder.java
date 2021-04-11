package builder;

import javafx.scene.SubScene;

import particle.ParticleManager;

public interface DisplayBuilder {

    public SubScene getScene();
    public void set2DCamera(double horizontalS, double horizontalF, double verticalS, double verticalF);
    public void set3DCamera(double x, double y, double z, double rh, double rv);
    public void update(ParticleManager pmanager);
    public void reset();

}