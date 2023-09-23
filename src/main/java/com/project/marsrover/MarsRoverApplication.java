package com.project.marsrover;
import com.project.marsrover.objects.DeployRoverParams;
import com.project.marsrover.service.MarsRoverService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class MarsRoverApplication {
    public static void main(String[] args) {
        SpringApplication.run(MarsRoverApplication.class, args);

        // Via CLI way.
        MarsRoverService marsRoverService = new MarsRoverService();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Deploy your rover: ");
        while (scanner.hasNext()) {
            try {
                // Format must follow 3,4,N f,f,r,f,f
                String[] commandLine = scanner.nextLine().split(" ");
                if (commandLine.length != 2) {
                    System.out.println("Invalid params. Params must follow the format of: 3,4,N f,f,r,f,f");
                    continue;
                }
                DeployRoverParams deployRoverParams = new DeployRoverParams(commandLine[0], commandLine[1]);
                marsRoverService.deployRover(deployRoverParams);
            }
            catch (Exception exception) {
                System.out.println(exception);
            }
        }
    }
}
