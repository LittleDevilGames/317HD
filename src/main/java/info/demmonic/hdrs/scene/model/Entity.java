package info.demmonic.hdrs.scene.model;

import info.demmonic.hdrs.Game;
import info.demmonic.hdrs.cache.impl.Sequence;
import info.demmonic.hdrs.node.impl.Renderable;

public class Entity extends Renderable {

    public static final byte[] DIRECTION_DELTA_X = {-1, 0, 1, -1, 1, -1, 0, 1};
    public static final byte[] DIRECTION_DELTA_Y = {1, 1, 1, 0, 0, -1, -1, -1};

    public int seqCycle;
    public int seqFrame;
    public int seqIndex;
    public int seqResetCycle;
    public int seqDelayCycle;
    public boolean canRotate;
    public int combatCycle;
    public int currentHealth;
    public int destRotation;
    public int faceEntity;
    public int faceX;
    public int faceY;
    public int spotanimCycle;
    public int spotanimCycleEnd;
    public int spotanimFrame;
    public int spotanimIndex;
    public int graphicOffsetY;
    public int height;
    public int hitCycle[];
    public int hitDamage[];
    public int hitType[];
    public int maxHealth;
    public int moveSeqCycle;
    public int moveSeqFrame;
    public int moveSeqIndex;
    public int moveCycleEnd;
    public int moveCycleStart;
    public int moveDirection;
    public int moveEndX;
    public int moveEndY;
    public int moveStartX;
    public int moveStartY;
    public int pathPosition;
    public boolean pathRun[];
    public int pathX[];
    public int pathY[];
    public int resyncWalkCycle;
    public int rotation;
    public int runAnimation;
    public int sceneX;
    public int sceneY;
    public int size;
    public int spokenColor;
    public int spokenEffect;
    public int spokenLife;
    public String spokenMessage;
    public int standAnimation;
    public int standTurnAnimation;
    public int stillPathPosition;
    public int turn180Animation;
    public int turnLAnimation;
    public int turnRAnimation;
    public int turnSpeed;
    public int updateCycle;
    public int walkAnimation;

    public Entity() {
        this.pathX = new int[10];
        this.pathY = new int[10];
        this.faceEntity = -1;
        this.turnSpeed = 32;
        this.runAnimation = -1;
        this.height = 200;
        this.standAnimation = -1;
        this.standTurnAnimation = -1;
        this.hitDamage = new int[4];
        this.hitType = new int[4];
        this.hitCycle = new int[4];
        this.moveSeqIndex = -1;
        this.spotanimIndex = -1;
        this.seqIndex = -1;
        this.combatCycle = -1000;
        this.spokenLife = 100;
        this.size = 1;
        this.canRotate = false;
        this.pathRun = new boolean[10];
        this.walkAnimation = -1;
        this.turn180Animation = -1;
        this.turnRAnimation = -1;
        this.turnLAnimation = -1;
    }

    public int getLocalX() {
        return this.sceneX >> 7;
    }

    public int getLocalY() {
        return this.sceneY >> 7;
    }

    public int getTileX() {
        return Game.mapBaseX + (this.sceneX >> 7);
    }

    public int getTileY() {
        return Game.mapBaseY + (this.sceneY >> 7);
    }

    public void hit(int type, int damage, int tick) {
        for (int i = 0; i < 4; i++) {
            if (hitCycle[i] <= tick) {
                hitDamage[i] = damage;
                hitType[i] = type;
                hitCycle[i] = tick + 70;
                return;
            }
        }
    }

    public boolean isVisible() {
        return false;
    }

    public void move(int direction, boolean running) {
        int x = pathX[0];
        int y = pathY[0];

        x += DIRECTION_DELTA_X[direction];
        y += DIRECTION_DELTA_Y[direction];

        if (seqIndex != -1 && Sequence.instance[seqIndex].walkFlag == 1) {
            seqIndex = -1;
        }

        if (pathPosition < 9) {
            pathPosition++;
        }

        for (int l = pathPosition; l > 0; l--) {
            pathX[l] = pathX[l - 1];
            pathY[l] = pathY[l - 1];
            pathRun[l] = pathRun[l - 1];
        }

        pathX[0] = x;
        pathY[0] = y;
        pathRun[0] = running;
    }

    public void moveTo(int x, int y, boolean discardWalkQueue) {
        if (seqIndex != -1 && Sequence.instance[seqIndex].walkFlag == 1) {
            seqIndex = -1;
        }

        if (!discardWalkQueue) {
            int deltaX = x - pathX[0];
            int deltaY = y - pathY[0];

            if (deltaX >= -8 && deltaX <= 8 && deltaY >= -8 && deltaY <= 8) {
                if (pathPosition < 9) {
                    pathPosition++;
                }

                for (int i = pathPosition; i > 0; i--) {
                    pathX[i] = pathX[i - 1];
                    pathY[i] = pathY[i - 1];
                    pathRun[i] = pathRun[i - 1];
                }

                pathX[0] = x;
                pathY[0] = y;
                pathRun[0] = false;
                return;
            }
        }

        pathPosition = 0;
        stillPathPosition = 0;
        resyncWalkCycle = 0;
        pathX[0] = x;
        pathY[0] = y;
        sceneX = pathX[0] * 128 + size * 64;
        sceneY = pathY[0] * 128 + size * 64;
    }

    public void resetPositions() {
        pathPosition = 0;
        stillPathPosition = 0;
    }
}
