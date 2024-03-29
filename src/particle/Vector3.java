package particle;

public class Vector3 {

    public double x, y, z;

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void print() {
        System.out.println(this.getClass().getSimpleName() + ": (" + x + ", " + y + ", " + z + ") ");
    }

}