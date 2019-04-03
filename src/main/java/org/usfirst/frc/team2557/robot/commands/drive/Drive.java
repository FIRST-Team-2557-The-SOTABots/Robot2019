package org.usfirst.frc.team2557.robot.commands.drive;

import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Command;

public class Drive extends Command {
  public Drive () {
    requires(Robot.swerve);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    if(Robot.m_oi.joystick1.getPOV() == 180) RobotMap.gyro.reset();

    double axis0 = RobotMap.driveMult * Robot.m_oi.joystick1.getRawAxis(0);
    double axis1 = RobotMap.driveMult * Robot.m_oi.joystick1.getRawAxis(1);
    double axis4 = RobotMap.driveMult * Robot.m_oi.joystick1.getRawAxis(4);
    double axis5 = RobotMap.driveMult * Robot.m_oi.joystick1.getRawAxis(5);

    if (axis0 < RobotMap.JOYSTICK_DEADBAND && axis0 > -RobotMap.JOYSTICK_DEADBAND) axis0 = 0.0;
		if (axis1 < RobotMap.JOYSTICK_DEADBAND && axis1 > -RobotMap.JOYSTICK_DEADBAND) axis1 = 0.0;
		if (axis4 < RobotMap.JOYSTICK_DEADBAND && axis4 > -RobotMap.JOYSTICK_DEADBAND) axis4 = 0.0;
    if (axis5 < RobotMap.JOYSTICK_DEADBAND && axis5 > -RobotMap.JOYSTICK_DEADBAND) axis5 = 0.0;

    double mult = 0.7;
    double rotMult = 0.35;
    if(Robot.m_oi.dbumperLeft.get()) {
      mult = 0.2;
      rotMult = 0.2;
    }

    if(axis0 != 0 || axis1 != 0 || axis4 != 0) Robot.swerve.gyroDrive(axis0*mult, axis1*mult, axis4*rotMult);
    else Robot.swerve.gyroDriveAngle();
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
