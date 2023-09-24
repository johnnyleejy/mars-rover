package com.project.marsrover.objects;

import com.project.marsrover.enums.Command;
import com.project.marsrover.enums.Direction;
import com.project.marsrover.exceptions.CollisionException;
import com.project.marsrover.exceptions.DeployRoverException;

public class Rover {
    private int xCoordinate;
    private int yCoordinate;
    private Direction direction;

    public Rover(int xCoordinate, int yCoordinate, String direction) throws DeployRoverException {
        this.setxCoordinate(xCoordinate);
        this.setyCoordinate(yCoordinate);
        this.setDirection(Direction.convertStringToDirectionEnum(direction));
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

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    /**
     * Turns the rover and updates its direction based on current direction and input command
     *
     * @param command The turn command for the rover
     * @throws DeployRoverException if the provided direction is invalid
     */
    public void turn(Command command) throws DeployRoverException {
        switch (this.getDirection()) {
            case NORTH -> {
                if (command == Command.LEFT) {
                    this.setDirection(Direction.WEST);
                } else {
                    this.setDirection(Direction.EAST);
                }
            }
            case SOUTH -> {
                if (command == Command.LEFT) {
                    this.setDirection(Direction.EAST);
                } else {
                    this.setDirection(Direction.WEST);
                }
            }
            case EAST -> {
                if (command == Command.LEFT) {
                    this.setDirection(Direction.NORTH);
                } else {
                    this.setDirection(Direction.SOUTH);
                }
            }
            case WEST -> {
                if (command == Command.LEFT) {
                    this.setDirection(Direction.SOUTH);
                } else {
                    this.setDirection(Direction.NORTH);
                }
            }
            default -> throw new DeployRoverException("Invalid state");
        }
    }

    /**
     * Moves the rover and updates its coordinates based on current direction and input command
     *
     * @param command The turn command for the rover
     * @param mars The 2d Mars object holding the state of other existing rovers
     * @throws DeployRoverException if the provided direction is invalid
     * @throws CollisionException if the rover collides with another existing rover on Mars
     */
    public void move(Command command, int[][] mars) throws DeployRoverException, CollisionException {
        switch (this.getDirection()) {
            case NORTH -> {
                if (command == Command.FORWARD) {
                    this.updateCoordinates(mars, this.getxCoordinate() + 1, this.getyCoordinate());
                } else {
                    this.updateCoordinates(mars, this.getxCoordinate() - 1, this.getyCoordinate());
                }
            }
            case SOUTH -> {
                if (command == Command.FORWARD) {
                    this.updateCoordinates(mars, this.getxCoordinate() - 1, this.getyCoordinate());
                } else {
                    this.updateCoordinates(mars, this.getxCoordinate() + 1, this.getyCoordinate());
                }
            }
            case EAST -> {
                if (command == Command.FORWARD) {
                    this.updateCoordinates(mars, this.getxCoordinate(), this.getyCoordinate() + 1);
                } else {
                    this.updateCoordinates(mars, this.getxCoordinate(), this.getyCoordinate() - 1);
                }
            }
            case WEST -> {
                if (command == Command.FORWARD) {
                    this.updateCoordinates(mars, this.getxCoordinate(), this.getyCoordinate() - 1);
                } else {
                    this.updateCoordinates(mars, this.getxCoordinate(), this.getyCoordinate() + 1);
                }
            }
            default -> throw new DeployRoverException("Invalid state");
        }
    }

    /**
     * Updates the coordinates for the rover
     *
     * @param mars The 2d Mars object holding the state of other existing rovers
     * @param xCoordinate The xCoordinate to be updated to
     * @param yCoordinate The yCoordinate to be updated to
     * @throws CollisionException if the rover collides with another existing rover on Mars
     * @throws DeployRoverException if the rover moves out of Mars boundary
     */
    public void updateCoordinates(int[][] mars, int xCoordinate, int yCoordinate) throws CollisionException, DeployRoverException {
        if (xCoordinate >= 99 || yCoordinate >= 99 || xCoordinate < 0 || yCoordinate < 0) {
            throw new DeployRoverException("Out of Mars boundary.");
        }
        if (mars[xCoordinate][yCoordinate] != 0) {
            throw new CollisionException("Collision detected");
        }
        this.setxCoordinate(xCoordinate);
        this.setyCoordinate(yCoordinate);
    }
}
