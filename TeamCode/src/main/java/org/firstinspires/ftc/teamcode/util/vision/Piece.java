package org.firstinspires.ftc.teamcode.util.vision;

import org.firstinspires.ftc.teamcode.util.globals.Alliance;
import org.firstinspires.ftc.teamcode.util.globals.Globals;

public enum Piece {
    POLLEN(1.4),
    NECTAR(1.8);
    private final double radius;
    Piece(double radius) {
        this.radius = radius;
    }
    public double getRadius() {
        return this.radius;
    }
    public int getPipeline() {
        if (this == POLLEN) {
            return 0;
        }
        if (Globals.alliance == Alliance.BLUE) {
            return 1;
        } else {
            return 2;
        }
    }
    public Piece other() {
        if (this == POLLEN) {
            return NECTAR;
        }
        return POLLEN;
    }
}