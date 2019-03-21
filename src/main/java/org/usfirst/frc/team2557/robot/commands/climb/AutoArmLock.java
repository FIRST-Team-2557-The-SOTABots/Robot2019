/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2557.robot.commands.climb;

import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

public class AutoArmLock extends Command {
  boolean lock;
  public AutoArmLock(boolean lock) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.arm);
    this.lock = lock;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.arm.arm(0);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(lock){
      RobotMap.dsArmLock.set(Value.kReverse);
    }else{
      RobotMap.dsArmLock.set(Value.kForward);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if(lock){
      return RobotMap.dsArmLock.get() == Value.kReverse;
    }else{
      return RobotMap.dsArmLock.get() == Value.kForward;
    }
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.arm.arm(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.arm.arm(0);
  }
}
