/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2557.robot.commands.climb;

import org.usfirst.frc.team2557.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class Climb extends Command {
  double setpoint;
  public Climb(double setpoint) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    // requires(climbSub)
    this.setpoint = setpoint;
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    RobotMap.climb.set(0.45);
  }

  @Override
  protected boolean isFinished() {
    if (RobotMap.climb.getSensorCollection().getQuadraturePosition() >= setpoint - 100 || RobotMap.climb.getSensorCollection().getQuadraturePosition() <= setpoint + 100) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  protected void end() {
  }

  @Override
  protected void interrupted() {
  }
}
