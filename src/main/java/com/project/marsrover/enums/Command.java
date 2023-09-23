package com.project.marsrover.enums;

import com.project.marsrover.exceptions.DeployRoverException;

public enum Command {
    FORWARD, BACKWARD, RIGHT, LEFT;

    public static Command convertStringToCommandEnum(String command) throws DeployRoverException {
        switch (command) {
            case "f":
                return Command.FORWARD;
            case "b":
                return Command.BACKWARD;
            case "r":
                return Command.RIGHT;
            case "l":
                return Command.LEFT;
            default:
                throw new DeployRoverException("Invalid command: " + command);
        }
    }
}