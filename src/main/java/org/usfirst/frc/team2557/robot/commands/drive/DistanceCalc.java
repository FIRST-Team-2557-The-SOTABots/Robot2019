/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2557.robot.commands.drive;

import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DistanceCalc extends Command {
  double pixels_height = 240;
  double pixels_width = 320;
  double u,v;
  double cx = (pixels_width/2)-.05;
  double cy = (pixels_height/2)-.05;
  double h = -724;//mm 
  double f = pixels_width/(2*Math.tan(59.6/2)); //279 
  double a;
  double valid;
  double angleSP = 0;
  NetworkTable table;
  NetworkTableEntry ta;
  NetworkTableEntry tx;
  NetworkTableEntry ty;
  NetworkTableEntry tv;
  public DistanceCalc() {
    // requires(Robot.gyroSwerveDrive);
    table = NetworkTableInstance.getDefault().getTable("limelight");
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    // double angleDiff = -RobotMap.gyro.getAngle();
    getCamData();
    // double z = Math.abs(x/angleDiff);

    double z = (h*f)/(v-cy)-38;
    SmartDashboard.putNumber("z", z);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  public void getCamData() {
	  tx = table.getEntry("tx");
    ta = table.getEntry("ta");
    tv = table.getEntry("tv");
    ty = table.getEntry("ty");

		//read values periodically
    u = tx.getDouble(0.0);
    v = ty.getDouble(0.0);
    a = ta.getDouble(0.0);
    valid = tv.getDouble(0.0);
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
