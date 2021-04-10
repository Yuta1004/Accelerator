package src.particle;

public class Particle {

    private final double Q;     // 粒子のもつ電荷
    private final double SM;    // 粒子の静止質量
    private final double DT;    // 時間進み幅

    private Vector3 Ef;         // 空間中の電界 (E)
    private Vector3 Bmfd;       // 空間中の磁束密度 (B)
    private Vector3 pos;        // 粒子の位置
    private Vector3 velocity;   // 粒子の速度
    private Vector3 meshN;      // 各軸方向の空間メッシュ数

    /**
     * Particleのコンストラクタ
     * 位置, 速度, 電場, 磁場は全て0で初期化される
     *
     * @param Q  粒子のもつ電荷
     * @param SM 粒子の静止質量
     * @param DT 時間進み幅
     * @param NX x軸方向の空間メッシュ数
     * @param NY y軸方向の空間メッシュ数
     * @param NZ z軸方向の空間メッシュ数
     */
    public Particle(double Q, double SM, double DT, double NX, double NY, double NZ) {
        this.Q = Q;
        this.SM = SM;
        this.DT = DT;

        Ef = new Vector3(0, 0, 0);
        Bmfd = new Vector3(0, 0, 0);
        pos = new Vector3(0, 0, 0);
        velocity = new Vector3(0, 0, 0);
        meshN = new Vector3(NX, NY, NZ);
    }

    /**
     * 粒子位置をセットする
     *
     * @param x 位置のx成分
     * @param y 位置のy成分
     * @param z 位置のz成分
     */
    public void setPos(double x, double y, double z) {
        pos.x = x/Const.R0; pos.y = y/Const.R0; pos.z = z/Const.R0;
    }

    /**
     * 粒子の速度をセットする
     * ※入力値は光速によって規格化される
     *
     * @param x 速度のx成分
     * @param y 速度のy成分
     * @param z 速度のz成分
     */
    public void setVelocity(double x, double y, double z) {
        velocity.x = x; velocity.y = y; velocity.z = z;
    }

    /**
     * 電界をセットする
     *
     * @param x 空間中の電界のx成分 [V/m]
     * @param y 空間中の電界のy成分 [V/m]
     * @param z 空間中の電界のz成分 [V/m]
     */
    public void setElectricField(double x, double y, double z) {
        Ef.x = x/Const.E0; Ef.y = y/Const.E0; Ef.z = z/Const.E0;
    }

    /**
     * 磁束密度をセットする
     *
     * @param x 空間中の磁束密度のx成分 [T]
     * @param y 空間中の磁束密度のy成分 [T]
     * @param z 空間中の磁束密度のz成分 [T]
     */
    public void setMagneticFluxDensity(double x, double y, double z) {
        Bmfd.x = x/Const.B0; Bmfd.y = y/Const.B0; Bmfd.z = z/Const.B0;
    }

    /**
     * 粒子の位置を取得する
     *
     * @return Vector3 粒子の位置
     */
    public Vector3 getPos() {
        return pos;
    }

    /**
     * 粒子の速度を取得する
     *
     * @return Vector3 粒子の速度
     */
    public Vector3 getVelocity() {
        return velocity;
    }
}