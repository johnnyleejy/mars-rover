package com.project.marsrover.controller;

import com.project.marsrover.exceptions.DeployRoverException;
import com.project.marsrover.objects.DeployRoverParams;
import com.project.marsrover.objects.DeployRoverResult;
import com.project.marsrover.service.MarsRoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MarsRoverController {

    private final MarsRoverService marsRoverService;

    @Autowired
    public MarsRoverController(MarsRoverService marsRoverService) {
        this.marsRoverService = marsRoverService;
    }

    @ResponseBody
    @PostMapping("/deployRover")
    public DeployRoverResult deployRover(@RequestBody DeployRoverParams deployRoverParams) throws DeployRoverException {
        return marsRoverService.deployRover(deployRoverParams);
    }
}
