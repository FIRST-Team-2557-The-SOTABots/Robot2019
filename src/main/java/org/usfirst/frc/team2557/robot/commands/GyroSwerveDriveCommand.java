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
    double axis0 = -Robot.m_oi.joystick.getRawAxis(0);
    double axis1 = -Robot.m_oi.joystick.getRawAxis(1);
    double axis4 = -Robot.m_oi.joystick.getRawAxis(4);
    double axis5 = Robot.m_oi.joystick.getRawAxis(5);
    double triggerLeft = Robot.m_oi.joystick.getRawAxis(2);
    double triggerRight = Robot.m_oi.joystick.getRawAxis(3);

    if (axis0 < RobotMap.JOYSTICK_DEADBAND && axis0 > -RobotMap.JOYSTICK_DEADBAND) axis0 = 0.0;
		if (axis1 < RobotMap.JOYSTICK_DEADBAND && axis1 > -RobotMap.JOYSTICK_DEADBAND) axis1 = 0.0;
		if (axis4 < RobotMap.JOYSTICK_DEADBAND && axis4 > -RobotMap.JOYSTICK_DEADBAND) axis4 = 0.0;
    if (axis5 < RobotMap.JOYSTICK_DEADBAND && axis5 > -RobotMap.JOYSTICK_DEADBAND) axis5 = 0.0;
    if (triggerLeft < RobotMap.TRIGGER_DEADBAND && triggerLeft > -RobotMap.TRIGGER_DEADBAND) triggerLeft = 0;
    if (triggerRight < RobotMap.TRIGGER_DEADBAND && triggerRight > -RobotMap.TRIGGER_DEADBAND) triggerRight = 0;

    if(Robot.m_oi.dx.get()) RobotMap.gyro.reset();

    double mult = 0.5;
    if(Robot.m_oi.db.get()) mult = 0.95;
    else if(Robot.m_oi.da.get()) mult = 0.2;
    if(triggerRight > 0.2) axis4 = -triggerRight;
    else if(triggerLeft > 0.2) axis4 = triggerLeft;
    Robot.gyroSwerveDrive.gyroDrive(axis0*mult, axis1*mult, axis4*mult);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
  }

  @Override
  protected void interrupted() {
  }
}