package com.project.marsrover.service;

import com.project.marsrover.enums.Command;
import com.project.marsrover.exceptions.CollisionException;
import com.project.marsrover.exceptions.DeployRoverException;
import com.project.marsrover.objects.DeployRoverParams;
import com.project.marsrover.objects.DeployRoverResult;
import com.project.marsrover.objects.Rover;
import org.springframework.stereotype.Service;

@Service
public class MarsRoverService {
    // Use a 2d array as an in memory solution to keep track of which coordinates are taken
    // Assume Mars has a fixed coordinates of 99, 99
    public int[][] mars = new int[99][99];

    /**
     * Deploys the rover based on input coordinates and direction and moves it according to input commands
     *
     * @param deployRoverParams The deployment params passed in by the client
     * @throws DeployRoverException
     */
    public DeployRoverResult deployRover(DeployRoverParams deployRoverParams) throws DeployRoverException {
        Rover rover = null;
        try {
            String[] commands = deployRoverParams.getCommands().split(",");
            String[] coordinates = deployRoverParams.getCurrentCoordinates().split(",");
            if (coordinates.length != 3) {
                throw new DeployRoverException("Invalid coordinates params. Specify in the format of e.g 3,4,N.");
            }
            // 1. Check if deployed coordinates clash with another rover
            int xCoordinate = Integer.parseInt(coordinates[0]);
            int yCoordinate = Integer.parseInt(coordinates[1]);
            String directionParams = coordinates[2];
            if (xCoordinate >= 99 || yCoordinate >= 99 || xCoordinate < 0 || yCoordinate < 0) {
                throw new DeployRoverException("Out of Mars boundary.");
            }
            if (mars[xCoordinate][yCoordinate] == 1) {
                throw new DeployRoverException("There is an existing rover at this coordinate.");
            }
            rover = new Rover(xCoordinate, yCoordinate, coordinates[2]);
            // 2. For each command, move the rover and check if it clashes
            for (String commandParam: commands) {
                Command command = Command.convertStringToCommandEnum(commandParam);
                if (command == Command.FORWARD || command == Command.BACKWARD) {
                    // Only forward and back commands can shift coordinates
                    rover.move(command, mars);
                }
                else {
                    // Only left and right commands can change directions
                    rover.turn(command);
                }
            }
            // 3. After the rover finishes its commands, mark the location in Mars as occupied
            mars[rover.getxCoordinate()][rover.getyCoordinate()] = 1;
            System.out.println("Final Coordinate: " + rover.getxCoordinate() + ", " + rover.getyCoordinate());
            System.out.println("Final Direction:  " + rover.getDirection());
            return new DeployRoverResult(rover.getxCoordinate(), rover.getyCoordinate(), rover.getDirection());
        }
        catch (CollisionException e) {
            // Stop rover and mark location on Mars
            mars[rover.getxCoordinate()][rover.getyCoordinate()] = 1;
            System.out.println("Final Coordinate: " + rover.getxCoordinate() + ", " + rover.getyCoordinate());
            System.out.println("Final Direction:  " + rover.getDirection());
            return new DeployRoverResult(rover.getxCoordinate(), rover.getyCoordinate(), rover.getDirection());
        }
    }
}
