package com.project.marsrover.objects;

public class DeployRoverParams {

    public DeployRoverParams(String currentCoordinates, String commands) {
        this.setCommands(commands);
        this.setCurrentCoordinates(currentCoordinates);
    }
    // 3,4,N
    private String currentCoordinates;
    // f,f,r,f,f
    private String commands;

    public String getCurrentCoordinates() {
        return currentCoordinates;
    }

    public void setCurrentCoordinates(String currentCoordinates) {
        this.currentCoordinates = currentCoordinates;
    }

    public String getCommands() {
        return commands;
    }

    public void setCommands(String commands) {
        this.commands = commands;
    }
}