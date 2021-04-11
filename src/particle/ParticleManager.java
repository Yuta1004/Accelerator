package particle;

import java.util.HashMap;

public class ParticleManager {

    private final double DT;        // 時間進み幅
    private final Vector3 meshN;    // 各軸方向の空間メッシュ数
    private       Vector3 Ef;       // 空間中の電界(E)
    private       Vector3 Bmfd;     // 空間中の磁束密度(B)

    private int updateCnt = 0;
    private HashMap<Integer, Particle> particles;
    private HashMap<Integer, Vector3> initialPositions;
    private HashMap<Integer, Vector3> initialVelocities;

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

        particles = new HashMap<Integer, Particle>();
        initialPositions = new HashMap<Integer, Vector3>();
        initialVelocities = new HashMap<Integer, Vector3>();
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
     * @return int 登録されたID
     */
    public int addElectron(double x, double y, double z, double vx, double vy, double vz) {
        return __addParticle(-1.0, 1.0, x, y, z, vx, vy, vz);
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
     * @return int 登録されたid
     */
    public int addProton(double x, double y, double z, double vx, double vy, double vz) {
        return __addParticle(1.0, 1836.1, x, y, z, vx, vy, vz);
    }

    private int __addParticle(double Q, double SM, double x, double y, double z, double vx, double vy, double vz) {
        Particle p = new Particle(Q, SM, DT, meshN.x, meshN.y, meshN.z);
        p.setPos(x, y, z);
        p.setVelocity(vx, vy, vz);
        p.setElectricField(Ef.x, Ef.y, Ef.z);
        p.setMagneticFluxDensity(Bmfd.x, Bmfd.y, Bmfd.z);

        int id = particles.size();
        particles.put(id, p);
        initialPositions.put(id, new Vector3(x, y, z));
        initialVelocities.put(id, new Vector3(vx, vy, vz));

        return id;
    }

    /**
     * 管理する全ての粒子の更新処理を行う
     */
    public void update() {
        ++ updateCnt;
        for(Particle p: particles.values()) {
            p.update();
        }
    }

    /**
     * 管理する全ての粒子の状態を初期状態に戻す
     */
    public void reset() {
        for(int id : particles.keySet()) {
            Vector3 pos = initialPositions.get(id);
            Vector3 velocity = initialVelocities.get(id);
            particles.get(id).setPos(pos.x, pos.y, pos.z);
            particles.get(id).setVelocity(velocity.x, velocity.y, velocity.z);
        }
    }

    /**
     * 管理対象にある粒子のうち、指定されたIDのものを削除する
     *
     * @return boolean 削除結果(指定IDが存在するときtrue)
     */
    public boolean remove(int id) {
        if(particles.get(id) == null) {
            return false;
        }
        particles.remove(id);
        initialPositions.remove(id);
        initialVelocities.remove(id);
        return true;
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
    public Particle[] getParticles() {
        return particles.values().toArray(new Particle[particles.size()]);
    }

}