package com.project.marsrover.enums;

import com.project.marsrover.exceptions.DeployRoverException;

public enum Command {
    FORWARD, BACKWARD, RIGHT, LEFT;


    /**
     * Converts the string input into the respective Command enum
     *
     * @param command The string command
     * @return The Command enum of the input string value
     * @throws DeployRoverException if string command is invalid
     */
    public static Command convertStringToCommandEnum(String command) throws DeployRoverException {
        return switch (command) {
            case "f" -> Command.FORWARD;
            case "b" -> Command.BACKWARD;
            case "r" -> Command.RIGHT;
            case "l" -> Command.LEFT;
            default -> throw new DeployRoverException("Invalid command: " + command);
        };
    }
}