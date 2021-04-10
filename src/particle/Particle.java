package particle;

public class Particle {

    private final double Q;     // 粒子のもつ電荷
    private final double SM;    // 粒子の静止質量
    private final double DT;    // 時間進み幅

    private       Vector3 Ef;         // 空間中の電界 (E)
    private       Vector3 Bmfd;       // 空間中の磁束密度 (B)
    private       Vector3 pos;        // 粒子の位置
    private       Vector3 velocity;   // 粒子の速度
    private final Vector3 meshN;      // 各軸方向の空間メッシュ数

    private boolean isEnableP = true;   // 計算が有効な粒子かどうか

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
        isEnableP = true;
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

    /**
     * まだ計算が有効な量子であるかどうかを返す
     * @return isEnableP
     */
    public boolean isEnableP() {
        return isEnableP;
    }

    /**
     * 量子の状態を更新する
     *   → 相対論的運動方程式をブネマンスキームによって解く
     * +------------------------------+
     * | γ m0 (dv/dt) = q (E + v x B) |
     * +------------------------------+
     */
    public void update() {
        double x = pos.x, y = pos.y, z = pos.z;
        double vx = velocity.x, vy = velocity.y, vz = velocity.z;

        // 粒子が有効領域から外れた場合、今後の計算を無効にする
        isEnableP &= !(x <= 0.0 || x >= meshN.x-1);
        isEnableP &= !(y <= 0.0 || y >= meshN.y-1);
        isEnableP &= !(z <= 0.0 || z >= meshN.z-1);
        if(!isEnableP) return;

        double fac = (Q/SM)*DT*0.5*Const.ZMOVE;                         // 電界から受けるローレンツ力の係数
        double gamma = 1.0 / Math.sqrt(1.0 - vx*vx - vy*vy - vz*vz);    // 相対論的係数
        double x2 = vx*gamma + fac*Ef.x;                                // 電界の影響を半分加える
        double y2 = vy*gamma + fac*Ef.y;
        double z2 = vz*gamma + fac*Ef.z;

        gamma = Math.sqrt(1.0 + x2*x2 + y2*y2 + z2*z2);                 // 電界の影響を半分加えたあとの相対論的係数
        double beta = Q*DT*0.5/(gamma*SM)*Const.ZMOVE;                  // 磁界から受けるローレンツ力の係数
        double bb2 = Bmfd.x*Bmfd.x + Bmfd.y*Bmfd.y + Bmfd.z*Bmfd.z;
        double f1 = beta + 0.333333*beta*beta*beta*bb2 + 0.133333*Math.pow(beta, 5)*bb2*bb2;
        double f2 = 2.0*f1/(1.0 + f1*f1*bb2);

        double x3 = x2 + (y2*Bmfd.z - z2*Bmfd.y)*f1;                    // 磁界によるサイクロトロン運動による運動量の変化を計算する
        double y3 = y2 + (z2*Bmfd.x - x2*Bmfd.z)*f1;
        double z3 = z2 + (x2*Bmfd.y - y2*Bmfd.x)*f1;

        double x4 = x2 + (y3*Bmfd.z - z3*Bmfd.y)*f2;
        double y4 = y2 + (z3*Bmfd.x - x3*Bmfd.z)*f2;
        double z4 = z2 + (x3*Bmfd.y - y3*Bmfd.x)*f2;

        double x5 = x4 + fac*Ef.x;                                      // 残り半分の次回の影響を加える
        double y5 = y4 + fac*Ef.y;
        double z5 = z4 + fac*Ef.z;

        gamma = Math.sqrt(1.0 + x5*x5 + y5*y5 + z5*z5);                 // 電磁界の影響を受けたあとの相対論係数を求める

        velocity.x = x5/gamma;
        velocity.y = y5/gamma;
        velocity.z = z5/gamma;

        pos.x += velocity.x*DT;
        pos.y += velocity.y*DT;
        pos.z += velocity.z*DT;
    }
}