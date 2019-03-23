/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2557.robot.commands.climb;

import org.usfirst.frc.team2557.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class TimedDrive extends Command {
  Timer t;
  double time;
  double str;
  double fwd;
  double rot;
  public TimedDrive(double time, double str, double fwd, double rot) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.gyroSwerveDrive);
    t = new Timer();
    this.time = time;
    this.str = str;
    this.fwd = fwd;
    this.rot = rot;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.gyroSwerveDrive.gyroDrive(0, 0, 0);
    t.reset();
    t.start();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.gyroSwerveDrive.driveStraight(fwd);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return t.get() > time;
    // return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.gyroSwerveDrive.driveStraight(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.gyroSwerveDrive.gyroDrive(0, 0, 0);
  }
}
