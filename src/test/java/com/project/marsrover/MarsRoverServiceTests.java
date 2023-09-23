package com.project.marsrover;

import com.project.marsrover.enums.Direction;
import com.project.marsrover.exceptions.DeployRoverException;
import com.project.marsrover.objects.DeployRoverParams;
import com.project.marsrover.objects.DeployRoverResult;
import com.project.marsrover.service.MarsRoverService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Assertions;

@SpringBootTest
class MarsRoverServiceTests {

    @Autowired
    private MarsRoverService marsRoverService;

    @BeforeEach
    private void setUp() {
        marsRoverService = new MarsRoverService();
    }

    @Nested
    class ErrorScenarios {
        @Test
        public void deployMarsRover_fail_invalidCoordinatesFormat() {
            // Given params with invalid coordinates 3 4 N
            DeployRoverParams deployRoverParams = new DeployRoverParams("3 4 N", "f,f,f,f");

            // When deploy rover is called
            // Then an exception will be thrown with the correct exception message
            Exception exception = Assertions.assertThrows(DeployRoverException.class, ()-> marsRoverService.deployRover(deployRoverParams));

            Assertions.assertEquals("Invalid coordinates params. Specify in the format of e.g 3,4,N.", exception.getMessage());
        }

        @Test
        public void deployMarsRover_fail_invalidCoordinatesLength() {
            // Given params with invalid coordinates length 3 4
            DeployRoverParams deployRoverParams = new DeployRoverParams("3,4", "f,f,f,f");

            // When deploy rover is called
            // Then an exception will be thrown with the correct exception message
            Exception exception = Assertions.assertThrows(DeployRoverException.class, ()-> marsRoverService.deployRover(deployRoverParams));

            Assertions.assertEquals("Invalid coordinates params. Specify in the format of e.g 3,4,N.", exception.getMessage());
        }

        @Test
        public void deployMarsRover_fail_invalidCommandsFormat() {
            // Given params with invalid command format f f f f
            DeployRoverParams deployRoverParams = new DeployRoverParams("3,4,N", "f f f f");

            // When deploy rover is called
            // Then an exception will be thrown with the correct exception message
            Exception exception = Assertions.assertThrows(DeployRoverException.class, ()-> marsRoverService.deployRover(deployRoverParams));

            Assertions.assertEquals("Invalid command: f f f f", exception.getMessage());
        }

        @Test
        public void deployMarsRover_fail_invalidDirection() {
            // Given params with invalid direction Hello
            DeployRoverParams deployRoverParams = new DeployRoverParams("3,4,Hello", "f f f f");

            // When deploy rover is called
            // Then an exception will be thrown with the correct exception message
            Exception exception = Assertions.assertThrows(DeployRoverException.class, ()-> marsRoverService.deployRover(deployRoverParams));

            Assertions.assertEquals("Invalid direction: Hello", exception.getMessage());
        }

        @Test
        public void deployMarsRover_fail_existingRoverAtCurrentLocation() throws DeployRoverException {
            // Given an existing rover at coordinates 1,1
            DeployRoverParams deployRoverParams = new DeployRoverParams("1,1,N", "l");
            marsRoverService.deployRover(deployRoverParams);

            // When a new rover is deployed to coordinates 1,1
            // Then an exception will be thrown with the correct exception message
            Exception exception = Assertions.assertThrows(DeployRoverException.class, ()-> marsRoverService.deployRover(deployRoverParams));

            Assertions.assertEquals("There is an existing rover at this coordinate.", exception.getMessage());
        }

        @Test
        public void deployMarsRover_fail_deploymentOutOfBounds() {
            // Given a rover with invalid coordinates as params
            DeployRoverParams deployRoverParams = new DeployRoverParams("100,200,N", "l");

            // When a rover is deployed outside of Mars boundary
            // Then an exception will be thrown with the correct exception message
            Exception exception = Assertions.assertThrows(DeployRoverException.class, ()-> marsRoverService.deployRover(deployRoverParams));

            Assertions.assertEquals("Out of Mars boundary.", exception.getMessage());
        }

        @Test
        public void deployMarsRover_fail_roverMoveOutOfBounds() {
            // Given a rover with invalid coordinates as params
            DeployRoverParams deployRoverParams = new DeployRoverParams("97,97,N", "f,f,f");

            // When a rover is moved out of bounds
            // Then an exception will be thrown with the correct exception message
            Exception exception = Assertions.assertThrows(DeployRoverException.class, ()-> marsRoverService.deployRover(deployRoverParams));

            Assertions.assertEquals("Out of Mars boundary.", exception.getMessage());
        }
    }

    @Nested
    class RoverTurningTests {
        @Test
        public void deployMarsRover_success_TurnNorthToEast() throws DeployRoverException {
            // Given a rover with valid params
            // When a rover facing north turns right
            DeployRoverParams deployRoverParams = new DeployRoverParams("10,10,N", "r");
            DeployRoverResult deployRoverResult = marsRoverService.deployRover(deployRoverParams);

            // Then it should be successfully deployed in the correct coordinates and facing the east
            Assertions.assertEquals(Direction.EAST, deployRoverResult.getFinalDirection());
            Assertions.assertEquals(10, deployRoverResult.getxCoordinate());
            Assertions.assertEquals(10, deployRoverResult.getyCoordinate());
        }

        @Test
        public void deployMarsRover_success_TurnNorthToWest() throws DeployRoverException {
            // Given a rover with valid params
            // When a rover facing north turns left
            DeployRoverParams deployRoverParams = new DeployRoverParams("11,11,N", "l");
            DeployRoverResult deployRoverResult = marsRoverService.deployRover(deployRoverParams);

            // Then it should be successfully deployed in the correct coordinates and facing the west
            Assertions.assertEquals(Direction.WEST, deployRoverResult.getFinalDirection());
            Assertions.assertEquals(11, deployRoverResult.getxCoordinate());
            Assertions.assertEquals(11, deployRoverResult.getyCoordinate());
        }

        @Test
        public void deployMarsRover_success_TurnNorthToSouth() throws DeployRoverException {
            // Given a rover with valid params
            // When a rover facing north turns right twice
            DeployRoverParams deployRoverParams = new DeployRoverParams("12,12,N", "r,r");
            DeployRoverResult deployRoverResult = marsRoverService.deployRover(deployRoverParams);

            // Then it should be successfully deployed in the correct coordinates and facing the south
            Assertions.assertEquals(Direction.SOUTH, deployRoverResult.getFinalDirection());
            Assertions.assertEquals(12, deployRoverResult.getxCoordinate());
            Assertions.assertEquals(12, deployRoverResult.getyCoordinate());
        }

        @Test
        public void deployMarsRover_success_TurnNorthToNorth() throws DeployRoverException {
            // Given a rover with valid params
            // When a rover facing north turns left 4 times
            DeployRoverParams deployRoverParams = new DeployRoverParams("13,13,N", "l,l,l,l");
            DeployRoverResult deployRoverResult = marsRoverService.deployRover(deployRoverParams);

            // Then it should be successfully deployed in the correct coordinates and still face the north
            Assertions.assertEquals(Direction.NORTH, deployRoverResult.getFinalDirection());
            Assertions.assertEquals(13, deployRoverResult.getxCoordinate());
            Assertions.assertEquals(13, deployRoverResult.getyCoordinate());
        }

        @Test
        public void deployMarsRover_success_TurnEastToNorth() throws DeployRoverException {
            // Given a rover with valid params
            // When a rover facing east turns left
            DeployRoverParams deployRoverParams = new DeployRoverParams("14,14,E", "l");
            DeployRoverResult deployRoverResult = marsRoverService.deployRover(deployRoverParams);

            // Then it should be successfully deployed in the correct coordinates and face north
            Assertions.assertEquals(Direction.NORTH, deployRoverResult.getFinalDirection());
            Assertions.assertEquals(14, deployRoverResult.getxCoordinate());
            Assertions.assertEquals(14, deployRoverResult.getyCoordinate());
        }

        @Test
        public void deployMarsRover_success_TurnEastToSouth() throws DeployRoverException {
            // Given a rover with valid params
            // When a rover facing east turns right
            DeployRoverParams deployRoverParams = new DeployRoverParams("15,15,E", "r");
            DeployRoverResult deployRoverResult = marsRoverService.deployRover(deployRoverParams);

            // Then it should be successfully deployed in the correct coordinates and face south
            Assertions.assertEquals(Direction.SOUTH, deployRoverResult.getFinalDirection());
            Assertions.assertEquals(15, deployRoverResult.getxCoordinate());
            Assertions.assertEquals(15, deployRoverResult.getyCoordinate());
        }

        @Test
        public void deployMarsRover_success_TurnEastToWest() throws DeployRoverException {
            // Given a rover with valid params
            // When a rover facing east turns right twice
            DeployRoverParams deployRoverParams = new DeployRoverParams("16,16,E", "r,r");
            DeployRoverResult deployRoverResult = marsRoverService.deployRover(deployRoverParams);

            // Then it should be successfully deployed in the correct coordinates and face west
            Assertions.assertEquals(Direction.WEST, deployRoverResult.getFinalDirection());
            Assertions.assertEquals(16, deployRoverResult.getxCoordinate());
            Assertions.assertEquals(16, deployRoverResult.getyCoordinate());
        }

        @Test
        public void deployMarsRover_success_TurnEastToEast() throws DeployRoverException {
            // Given a rover with valid params
            // When a rover facing east turns left 4 times
            DeployRoverParams deployRoverParams = new DeployRoverParams("17,17,E", "l,l,l,l");
            DeployRoverResult deployRoverResult = marsRoverService.deployRover(deployRoverParams);

            // Then it should be successfully deployed in the correct coordinates and still face east
            Assertions.assertEquals(Direction.EAST, deployRoverResult.getFinalDirection());
            Assertions.assertEquals(17, deployRoverResult.getxCoordinate());
            Assertions.assertEquals(17, deployRoverResult.getyCoordinate());
        }

        @Test
        public void deployMarsRover_success_TurnSouthToEast() throws DeployRoverException {
            // Given a rover with valid params
            // When a rover facing south turns left
            DeployRoverParams deployRoverParams = new DeployRoverParams("18,18,S", "l");
            DeployRoverResult deployRoverResult = marsRoverService.deployRover(deployRoverParams);

            // Then it should be successfully deployed in the correct coordinates and face east
            Assertions.assertEquals(Direction.EAST, deployRoverResult.getFinalDirection());
            Assertions.assertEquals(18, deployRoverResult.getxCoordinate());
            Assertions.assertEquals(18, deployRoverResult.getyCoordinate());
        }

        @Test
        public void deployMarsRover_success_TurnSouthToNorth() throws DeployRoverException {
            // Given a rover with valid params
            // When a rover facing south turns left twice
            DeployRoverParams deployRoverParams = new DeployRoverParams("19,19,S", "l,l");
            DeployRoverResult deployRoverResult = marsRoverService.deployRover(deployRoverParams);

            // Then it should be successfully deployed in the correct coordinates and face north
            Assertions.assertEquals(Direction.NORTH, deployRoverResult.getFinalDirection());
            Assertions.assertEquals(19, deployRoverResult.getxCoordinate());
            Assertions.assertEquals(19, deployRoverResult.getyCoordinate());
        }

        @Test
        public void deployMarsRover_success_TurnSouthToWest() throws DeployRoverException {
            // Given a rover with valid params
            // When a rover facing south turns right
            DeployRoverParams deployRoverParams = new DeployRoverParams("20,20,S", "r");
            DeployRoverResult deployRoverResult = marsRoverService.deployRover(deployRoverParams);

            // Then it should be successfully deployed in the correct coordinates and face west
            Assertions.assertEquals(Direction.WEST, deployRoverResult.getFinalDirection());
            Assertions.assertEquals(20, deployRoverResult.getxCoordinate());
            Assertions.assertEquals(20, deployRoverResult.getyCoordinate());
        }

        @Test
        public void deployMarsRover_success_TurnSouthToSouth() throws DeployRoverException {
            // Given a rover with valid params
            // When a rover facing south turns right 4 times
            DeployRoverParams deployRoverParams = new DeployRoverParams("21,21,S", "r,r,r,r");
            DeployRoverResult deployRoverResult = marsRoverService.deployRover(deployRoverParams);

            // Then it should be successfully deployed in the correct coordinates and still face south
            Assertions.assertEquals(Direction.SOUTH, deployRoverResult.getFinalDirection());
            Assertions.assertEquals(21, deployRoverResult.getxCoordinate());
            Assertions.assertEquals(21, deployRoverResult.getyCoordinate());
        }

        @Test
        public void deployMarsRover_success_TurnWestToSouth() throws DeployRoverException {
            // Given a rover with valid params
            // When a rover facing west turns left
            DeployRoverParams deployRoverParams = new DeployRoverParams("22,22,W", "l");
            DeployRoverResult deployRoverResult = marsRoverService.deployRover(deployRoverParams);

            // Then it should be successfully deployed in the correct coordinates and face south
            Assertions.assertEquals(Direction.SOUTH, deployRoverResult.getFinalDirection());
            Assertions.assertEquals(22, deployRoverResult.getxCoordinate());
            Assertions.assertEquals(22, deployRoverResult.getyCoordinate());
        }

        @Test
        public void deployMarsRover_success_TurnWestToEast() throws DeployRoverException {
            // Given a rover with valid params
            // When a rover facing west turns left twice
            DeployRoverParams deployRoverParams = new DeployRoverParams("23,23,W", "l,l");
            DeployRoverResult deployRoverResult = marsRoverService.deployRover(deployRoverParams);

            // Then it should be successfully deployed in the correct coordinates and face east
            Assertions.assertEquals(Direction.EAST, deployRoverResult.getFinalDirection());
            Assertions.assertEquals(23, deployRoverResult.getxCoordinate());
            Assertions.assertEquals(23, deployRoverResult.getyCoordinate());
        }

        @Test
        public void deployMarsRover_success_TurnWestToNorth() throws DeployRoverException {
            // Given a rover with valid params
            // When a rover facing west turns right
            DeployRoverParams deployRoverParams = new DeployRoverParams("24,24,W", "r");
            DeployRoverResult deployRoverResult = marsRoverService.deployRover(deployRoverParams);

            // Then it should be successfully deployed in the correct coordinates and face north
            Assertions.assertEquals(Direction.NORTH, deployRoverResult.getFinalDirection());
            Assertions.assertEquals(24, deployRoverResult.getxCoordinate());
            Assertions.assertEquals(24, deployRoverResult.getyCoordinate());
        }

        @Test
        public void deployMarsRover_success_TurnWestToWest() throws DeployRoverException {
            // Given a rover with valid params
            // When a rover facing west turns right 4 times
            DeployRoverParams deployRoverParams = new DeployRoverParams("25,25,W", "r,r,r,r");
            DeployRoverResult deployRoverResult = marsRoverService.deployRover(deployRoverParams);

            // Then it should be successfully deployed in the correct coordinates and still face west
            Assertions.assertEquals(Direction.WEST, deployRoverResult.getFinalDirection());
            Assertions.assertEquals(25, deployRoverResult.getxCoordinate());
            Assertions.assertEquals(25, deployRoverResult.getyCoordinate());
        }
    }

    @Nested
    class RoverMovingTests {
        @Test
        public void deployMarsRover_success_moveForward() throws DeployRoverException {
            // Given a rover with valid params
            DeployRoverParams deployRoverParams = new DeployRoverParams("30,30,N", "f,f,f,f");

            // When the rover is deployed
            DeployRoverResult deployRoverResult = marsRoverService.deployRover(deployRoverParams);

            // Then it should be successfully deployed in the correct coordinates and face the right direction
            Assertions.assertEquals(Direction.NORTH, deployRoverResult.getFinalDirection());
            Assertions.assertEquals(34, deployRoverResult.getxCoordinate());
            Assertions.assertEquals(30, deployRoverResult.getyCoordinate());
        }

        @Test
        public void deployMarsRover_success_moveBackward() throws DeployRoverException {
            // Given a rover with valid params
            DeployRoverParams deployRoverParams = new DeployRoverParams("31,31,N", "b,b,b,b");

            // When the rover is deployed
            DeployRoverResult deployRoverResult = marsRoverService.deployRover(deployRoverParams);

            // Then it should be successfully deployed in the correct coordinates and face the right direction
            Assertions.assertEquals(Direction.NORTH, deployRoverResult.getFinalDirection());
            Assertions.assertEquals(27, deployRoverResult.getxCoordinate());
            Assertions.assertEquals(31, deployRoverResult.getyCoordinate());
        }
    }

    @Nested
    class RoverTurningAndMovingTests {
        @Test
        public void deployMarsRover_success_TurnRightAndMoveForward() throws DeployRoverException {
            // Given a rover with valid params
            DeployRoverParams deployRoverParams = new DeployRoverParams("42,42,N", "r,f,f,f,f");

            // When the rover is deployed
            DeployRoverResult deployRoverResult = marsRoverService.deployRover(deployRoverParams);

            // Then it should be successfully deployed in the correct coordinates and face the right direction
            Assertions.assertEquals(Direction.EAST, deployRoverResult.getFinalDirection());
            Assertions.assertEquals(42, deployRoverResult.getxCoordinate());
            Assertions.assertEquals(46, deployRoverResult.getyCoordinate());
        }

        @Test
        public void deployMarsRover_success_TurnRightAndMoveBackward() throws DeployRoverException {
            // Given a rover with valid params
            DeployRoverParams deployRoverParams = new DeployRoverParams("43,43,N", "r,b,b,b,b");

            // When the rover is deployed
            DeployRoverResult deployRoverResult = marsRoverService.deployRover(deployRoverParams);

            // Then it should be successfully deployed in the correct coordinates and face the right direction
            Assertions.assertEquals(Direction.EAST, deployRoverResult.getFinalDirection());
            Assertions.assertEquals(43, deployRoverResult.getxCoordinate());
            Assertions.assertEquals(39, deployRoverResult.getyCoordinate());
        }

        @Test
        public void deployMarsRover_success_TurnLeftAndMoveForward() throws DeployRoverException {
            // Given a rover with valid params
            DeployRoverParams deployRoverParams = new DeployRoverParams("44,44,N", "l,f,f,f,f");

            // When the rover is deployed
            DeployRoverResult deployRoverResult = marsRoverService.deployRover(deployRoverParams);

            // Then it should be successfully deployed in the correct coordinates and face the right direction
            Assertions.assertEquals(Direction.WEST, deployRoverResult.getFinalDirection());
            Assertions.assertEquals(44, deployRoverResult.getxCoordinate());
            Assertions.assertEquals(40, deployRoverResult.getyCoordinate());
        }

        @Test
        public void deployMarsRover_success_TurnLeftAndMoveBackward() throws DeployRoverException {
            // Given a rover with valid params
            DeployRoverParams deployRoverParams = new DeployRoverParams("45,45,N", "l,b,b,b,b");

            // When the rover is deployed
            DeployRoverResult deployRoverResult = marsRoverService.deployRover(deployRoverParams);

            // Then it should be successfully deployed in the correct coordinates and face the right direction
            Assertions.assertEquals(Direction.WEST, deployRoverResult.getFinalDirection());
            Assertions.assertEquals(45, deployRoverResult.getxCoordinate());
            Assertions.assertEquals(49, deployRoverResult.getyCoordinate());
        }

        @Test
        public void deployMarsRover_success_roverCollision() throws DeployRoverException {
            // Given an existing deployed rover
            marsRoverService.deployRover(new DeployRoverParams("70,70,N", "r"));

            // When deploy rover is called with commands to collide onto the above deployed rover
            DeployRoverParams deployRoverParams = new DeployRoverParams("68,70,N", "f,f");
            DeployRoverResult deployRoverResult = marsRoverService.deployRover(deployRoverParams);

            // Then the rover should stop before the collision and have the right coordinates and face the right direction
            Assertions.assertEquals(Direction.NORTH, deployRoverResult.getFinalDirection());
            Assertions.assertEquals(69, deployRoverResult.getxCoordinate());
            Assertions.assertEquals(70, deployRoverResult.getyCoordinate());
        }
    }
}
