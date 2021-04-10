package src.particle;

/**
 * 定数を定義するデータクラス
 */
class Const {

    public static double C      = 2.9997E+8;                // 真空中の光の速さ [m/sec]
    public static double R0     = 0.01;                     // 長さに関する規格化定数 [m]
    public static double SM0    = 9.1091E-31;               // 電子の静止質量 [kg]
    public static double Q0     = 1.6021E-19;               // 単位静電荷 [C]
    public static double T0     = R0/C;                     // 時間に関する規格化定数
    public static double V0     = C;                        // 速度に関する規格化定数
    public static double VOLT0  = 1.E6;                     // 静電ポテンシャルに関する規格化定数
    public static double E0     = VOLT0/R0;                 // 静電界に関する規格化定数
    public static double B0     = E0/C;                     // 磁束密度に関する規格化定数
    public static double ZMOVE  = (VOLT0*Q0) / (C*C*SM0);   // 運動量に関する規格化定数

}