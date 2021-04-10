package builder;

import java.util.ArrayList;

import javafx.scene.Scene;

import particle.Particle;

public interface DisplayBuilder {

    public Scene getScene();
    public void update(ArrayList<Particle> particles);

}