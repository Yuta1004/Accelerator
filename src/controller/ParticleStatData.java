package controller;

import particle.Vector3;

/**
 * ListViewでのデータ保持用
 */
public class ParticleStatData {

    public final int id;
    public final boolean isElectron, isEnableP;
    public final Vector3 initialPosition;
    public final Vector3 initialVelocity;

    /**
     * ParticleStatDataのコンストラクタ
     * @param id ID
     * @param isElectron 電子かどうか
     * @param isEnableP 計算可能な粒子かどうか
     * @param initialPosition 初期座標
     * @param initialVelocity 初速度
     */
    public ParticleStatData(int id, boolean isElectron, boolean isEnableP, Vector3 initialPosition, Vector3 initialVelocity) {
        this.id = id;
        this.isElectron = isElectron;
        this.isEnableP = isEnableP;
        this.initialPosition = initialPosition;
        this.initialVelocity = initialVelocity;
    }

}