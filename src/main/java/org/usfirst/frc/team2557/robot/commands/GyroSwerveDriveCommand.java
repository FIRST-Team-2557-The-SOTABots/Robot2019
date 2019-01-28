package org.usfirst.frc.team2557.robot.commands;

import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Command;

public class GyroSwerveDriveCommand extends Command {
  public GyroSwerveDriveCommand () {
    requires(Robot.gyroSwerveDrive);
  }

  @Override
  protected void initialize() {
    RobotMap.gyro.reset();
  }

  @Override
  protected void execute() {
    double axis0 = Robot.m_oi.joystick.getRawAxis(0);
    double axis1 = Robot.m_oi.joystick.getRawAxis(1);
    double axis4 = Robot.m_oi.joystick.getRawAxis(4);
    double axis5 = Robot.m_oi.joystick.getRawAxis(5);

    // double triggerRight = Robot.m_oi.joystick.getRawAxis(3);
    // double triggerLeft = Robot.m_oi.joystick.getRawAxis(2);

    if (axis0 < RobotMap.JOYSTICK_DEADBAND && axis0 > -RobotMap.JOYSTICK_DEADBAND) { axis0 = 0.0; }
		if (axis1 < RobotMap.JOYSTICK_DEADBAND && axis1 > -RobotMap.JOYSTICK_DEADBAND) { axis1 = 0.0; }
		if (axis4 < RobotMap.JOYSTICK_DEADBAND && axis4 > -RobotMap.JOYSTICK_DEADBAND) { axis4 = 0.0; }
    if (axis5 < RobotMap.JOYSTICK_DEADBAND && axis5 > -RobotMap.JOYSTICK_DEADBAND) { axis5 = 0.0; }

    axis0 *= -1;
    axis1 *= -1;
    axis4 *= -1;

    if(Robot.m_oi.dx.get()) RobotMap.gyro.reset();

    if(Robot.m_oi.db.get()){
      Robot.gyroSwerveDrive.gyroDrive(axis0*.95, axis1*.95, axis4*.95);
      // if(triggerRight > 0.2){
      //   Robot.gyroSwerveDrive.gyroDrive(axis0*.95, axis1*.95, -triggerRight*.95);
      // }else if(triggerLeft > 0.2){
      //   Robot.gyroSwerveDrive.gyroDrive(axis0 * RobotMap.SWERVE_MULTIPLIER, axis1 * RobotMap.SWERVE_MULTIPLIER, 
      //     axis4 * RobotMap.SWERVE_MULTIPLIER);
      // }
    }else if(Robot.m_oi.da.get()){
      Robot.gyroSwerveDrive.gyroDrive(axis0*.2, axis1*.2, axis4*.2);
      // if(triggerRight > 0.2){
      //   Robot.gyroSwerveDrive.gyroDrive(axis0*.2, axis1*.2, -triggerRight*.2);
      // }else if(triggerLeft > 0.2){
      //   Robot.gyroSwerveDrive.gyroDrive(axis0 * RobotMap.SWERVE_MULTIPLIER, axis1 * RobotMap.SWERVE_MULTIPLIER, 
      //     axis4 * RobotMap.SWERVE_MULTIPLIER);
      // }
    }else{
      Robot.gyroSwerveDrive.gyroDrive(axis0 * 0.5, axis1 * 0.5, axis4 * 0.5);
    }

  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}