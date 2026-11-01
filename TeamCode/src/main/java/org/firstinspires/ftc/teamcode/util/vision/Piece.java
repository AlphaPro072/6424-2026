package org.firstinspires.ftc.teamcode.util.vision;

import org.firstinspires.ftc.teamcode.util.globals.Alliance;

public enum Piece {
    POLLEN(1.4),
    NECTAR(1.8);
    private final double radius;
    private Piece(double radius) {
        this.radius = radius;
    }
    public double getRadius() {
        return this.radius;
    }
    public int getPipeline(Alliance currentAlliance) {
        if (this == POLLEN) {
            return 0;
        }
        if (currentAlliance == Alliance.BLUE) {
            return 1;
        } else {
            return 2;
        }
    }
}