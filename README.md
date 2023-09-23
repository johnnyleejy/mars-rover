# mars-rover

# About this application

This is a simple springboot application that allows you to deploy and command your rover on mars. You can use it via REST POST request or via the CLI itself. Examples are provided below.

## Sample command:

`xCoordinate,yCoordinate,DIRECTION <SPACE> f,f,f`

### Commands:
1. **f** - Moves the rover forward
2. **b** - Moves the rover backward
3. **l** - Turns the rover left
4. **r** - Turns the rover right

## How to use this application
1. Start spring boot application by running `MarsRoverApplication.java`
2. Enter your command into the console. Commands must be comma seperated.  Format must follow the standard above.

   **Example**: `3,4,N f,f,f`

3. Alternatively, you can fire a POST request to deploy your rover on mars.

   **Endpoint**: `http://localhost:8080/deployRover`
   
   **Sample POST request body**: `{
      "currentCoordinates": "3,4,N",
      "commands": "f,f,f,r"
    }`


## Test cases: 

Refer to `MarsRoverServiceTests` for all the written test cases
