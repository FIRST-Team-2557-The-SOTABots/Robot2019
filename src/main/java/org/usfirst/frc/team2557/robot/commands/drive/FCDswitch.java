/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2557.robot.commands.drive;

import org.usfirst.frc.team2557.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class FCDswitch extends Command {
  boolean prev;
  boolean startValSet;
  public FCDswitch() {
    startValSet = false;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(!startValSet){
      prev = Robot.gyroSwerveDrive.fcd;
      startValSet = true;
    }
    Robot.gyroSwerveDrive.fcd = !prev;
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    startValSet = false;
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
