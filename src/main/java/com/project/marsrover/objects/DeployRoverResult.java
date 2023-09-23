package com.project.marsrover.objects;

import com.project.marsrover.enums.Direction;

public class DeployRoverResult {
    private int xCoordinate;
    private int yCoordinate;
    private Direction finalDirection;

    public DeployRoverResult(int xCoordinate, int yCoordinate, Direction direction) {
        this.setxCoordinate(xCoordinate);
        this.setyCoordinate(yCoordinate);
        this.setFinalDirection(direction);
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public Direction getFinalDirection() {
        return finalDirection;
    }

    public void setFinalDirection(Direction finalDirection) {
        this.finalDirection = finalDirection;
    }
}
