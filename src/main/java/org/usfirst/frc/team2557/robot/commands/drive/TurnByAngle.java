/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2557.robot.commands.drive;

import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Command;

public class TurnByAngle extends Command {
  double angle;

  public TurnByAngle(double angle) {
    requires(Robot.gyroSwerveDrive);
    this.angle = angle;

    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    // TAKE THIS OUT, INSTEAD SUBTRACT GYRO READING, NO RESET!!
    RobotMap.gyro.reset();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(RobotMap.gyro.getAngle() > angle){
      Robot.gyroSwerveDrive.gyroDrive(0, 0, 0.5);
    }else if(RobotMap.gyro.getAngle() < angle){
      Robot.gyroSwerveDrive.gyroDrive(0, 0, -0.5);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if(Math.abs(RobotMap.gyro.getAngle() - angle) < 2.0){
      return true;
    }
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
