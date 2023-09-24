package com.project.marsrover.enums;

import com.project.marsrover.exceptions.DeployRoverException;

public enum Direction {
    NORTH, SOUTH, EAST, WEST;

    /**
     * Converts the string input into the respective Direction enum
     *
     * @param direction The string direction
     * @return The Direction enum of the input string value
     * @throws DeployRoverException if string direction is invalid
     */
    public static Direction convertStringToDirectionEnum(String direction) throws DeployRoverException {
        return switch (direction) {
            case "N" -> Direction.NORTH;
            case "S" -> Direction.SOUTH;
            case "E" -> Direction.EAST;
            case "W" -> Direction.WEST;
            default -> throw new DeployRoverException("Invalid direction: " + direction);
        };
    }
}
