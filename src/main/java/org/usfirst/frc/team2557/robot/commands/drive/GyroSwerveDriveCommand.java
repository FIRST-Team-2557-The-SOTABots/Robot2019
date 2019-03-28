package org.usfirst.frc.team2557.robot.commands.drive;

import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Command;

public class GyroSwerveDriveCommand extends Command {
  public GyroSwerveDriveCommand () {
    requires(Robot.gyroSwerveDrive);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    if(Robot.m_oi.joystick1.getPOV() == 0){
      RobotMap.gyro.reset();
    }

    double axis0 = RobotMap.driveMult * Robot.m_oi.joystick1.getRawAxis(0);
    double axis1 = RobotMap.driveMult * Robot.m_oi.joystick1.getRawAxis(1);
    double axis4 = RobotMap.driveMult * Robot.m_oi.joystick1.getRawAxis(4);
    double axis5 = RobotMap.driveMult * Robot.m_oi.joystick1.getRawAxis(5);
    // double triggerLeft = Robot.m_oi.joystick1.getRawAxis(2);
    // double triggerRight = Robot.m_oi.joystick1.getRawAxis(3);

    if (axis0 < RobotMap.JOYSTICK_DEADBAND && axis0 > -RobotMap.JOYSTICK_DEADBAND) axis0 = 0.0;
		if (axis1 < RobotMap.JOYSTICK_DEADBAND && axis1 > -RobotMap.JOYSTICK_DEADBAND) axis1 = 0.0;
		if (axis4 < RobotMap.JOYSTICK_DEADBAND && axis4 > -RobotMap.JOYSTICK_DEADBAND) axis4 = 0.0;
    if (axis5 < RobotMap.JOYSTICK_DEADBAND && axis5 > -RobotMap.JOYSTICK_DEADBAND) axis5 = 0.0;
    // if (triggerLeft < RobotMap.TRIGGER_DEADBAND && triggerLeft > -RobotMap.TRIGGER_DEADBAND) triggerLeft = 0;
    // if (triggerRight < RobotMap.TRIGGER_DEADBAND && triggerRight > -RobotMap.TRIGGER_DEADBAND) triggerRight = 0;

    double mult = 0.7;
    double rotMult = mult;
    if(Robot.m_oi.db.get()) {
      mult = 0.9;
    }else if(Robot.m_oi.bumperLeft.get()) {
      mult = 0.2;
      rotMult *= 0.5;
    }

    // if(triggerRight > 0.2) axis4 = -triggerRight;
    // else if(triggerLeft > 0.2) axis4 = triggerLeft;

    if(axis0 != 0 || axis1 != 0 || axis4 != 0 /* || triggerLeft != 0 || triggerRight != 0*/) Robot.gyroSwerveDrive.gyroDrive(axis0*mult, axis1*mult, axis4*rotMult);
    else Robot.gyroSwerveDrive.gyroDriveAngle();
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
