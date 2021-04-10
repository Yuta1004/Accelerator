package particle;

import java.util.ArrayList;

public class ParticleManager {

    private final double DT;        // 時間進み幅
    private final Vector3 meshN;    // 各軸方向の空間メッシュ数
    private       Vector3 Ef;       // 空間中の電界(E)
    private       Vector3 Bmfd;     // 空間中の磁束密度(B)

    private int updateCnt = 0;
    private ArrayList<Particle> particles;

    /**
     * ParticleManagerのコンストラクタ
     * ※電界, 磁束密度は0で初期化される
     *
     * @param DT 時間進み幅
     * @param NX x軸方向の空間メッシュ数
     * @param NY y軸方向の空間メッシュ数
     * @param NZ z軸方向の空間メッシュ数
     */
    public ParticleManager(double DT, double NX, double NY, double NZ) {
        this.DT = DT;
        this.meshN = new Vector3(NX, NY, NZ);
        this.Ef = new Vector3(0, 0, 0);
        this.Bmfd = new Vector3(0, 0, 0);

        particles = new ArrayList<Particle>();
    }

    /**
     * 電界をセットする
     *
     * @param x 空間中の電界のx成分 [V/m]
     * @param y 空間中の電界のy成分 [V/m]
     * @param z 空間中の電界のz成分 [V/m]
     */
    public void setElectricField(double x, double y, double z) {
        Ef.x = x; Ef.y = y; Ef.z = z;
    }

    /**
     * 磁束密度をセットする
     *
     * @param x 空間中の磁束密度のx成分 [T]
     * @param y 空間中の磁束密度のy成分 [T]
     * @param z 空間中の磁束密度のz成分 [T]
     */
    public void setMagneticFluxDensity(double x, double y, double z) {
        Bmfd.x = x; Bmfd.y = y; Bmfd.z = z;
    }

    /**
     * 電子を追加する
     *
     * @param x  初期座標のx成分
     * @param y  初期座標のy成分
     * @param z  初期座標のz成分
     * @param vx 初速度のx成分
     * @param vy 初速度のy成分
     * @param vz 初速度のz成分
     */
    public void addElectron(double x, double y, double z, double vx, double vy, double vz) {
        __addParticle(-1.0, 1.0, x, y, z, vx, vy, vz);
    }

    /**
     * 陽子を追加する
     *
     * @param x  初期座標のx成分
     * @param y  初期座標のy成分
     * @param z  初期座標のz成分
     * @param vx 初速度のx成分
     * @param vy 初速度のy成分
     * @param vz 初速度のz成分
     */
    public void addProton(double x, double y, double z, double vx, double vy, double vz) {
        __addParticle(1.0, 1836.1, x, y, z, vx, vy, vz);
    }

    private void __addParticle(double Q, double SM, double x, double y, double z, double vx, double vy, double vz) {
        Particle p = new Particle(Q, SM, DT, meshN.x, meshN.y, meshN.z);
        p.setPos(x, y, z);
        p.setVelocity(vx, vy, vz);
        p.setElectricField(Ef.x, Ef.y, Ef.z);
        p.setMagneticFluxDensity(Bmfd.x, Bmfd.y, Bmfd.z);
        particles.add(p);
    }

    /**
     * 管理する全ての粒子の更新処理を行う
     */
    public void update() {
        ++ updateCnt;
        for(Particle p: particles) {
            p.update();
        }
    }

    /**
     * 経過時間を返す
     */
    public double getTime() {
        return updateCnt*Const.T0;
    }

    /**
     * 管理下にある粒子を返す
     */
    public ArrayList<Particle> getParticles() {
        return particles;
    }

}