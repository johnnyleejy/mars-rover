package com.project.marsrover.enums;

import com.project.marsrover.exceptions.DeployRoverException;

public enum Direction {
    NORTH, SOUTH, EAST, WEST;

    public static Direction convertStringToDirectionEnum(String direction) throws DeployRoverException {
        switch (direction) {
            case "N":
                return Direction.NORTH;
            case "S":
                return Direction.SOUTH;
            case "E":
                return Direction.EAST;
            case "W":
                return Direction.WEST;
            default:
                throw new DeployRoverException("Invalid direction: " + direction);
        }
    }
}
