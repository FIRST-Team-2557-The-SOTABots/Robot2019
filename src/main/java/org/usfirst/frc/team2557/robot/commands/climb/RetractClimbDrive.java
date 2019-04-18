/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2557.robot.commands.climb;

import org.usfirst.frc.team2557.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RetractClimbDrive extends CommandGroup {
  /**
   * Add your docs here.
   */
  public RetractClimbDrive(int climbTarget) {
    addParallel(new TimedDrive(10, 0.1));
    addSequential(new Climb(climbTarget, 1));
    addSequential(new TimedDrive(10, 0.2));
  }
}
