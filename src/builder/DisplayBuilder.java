package builder;

import java.util.ArrayList;

import javafx.scene.SubScene;

import particle.Particle;

public interface DisplayBuilder {

    public SubScene getScene();
    public void update(ArrayList<Particle> particles);

}