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
    if(Robot.m_oi.joystick1.getPOV() == 180) RobotMap.gyro.reset();

    double axis0 = RobotMap.driveMult * Robot.m_oi.joystick1.getRawAxis(0);
    double axis1 = RobotMap.driveMult * Robot.m_oi.joystick1.getRawAxis(1);
    double axis4 = RobotMap.driveMult * Robot.m_oi.joystick1.getRawAxis(4);
    double axis5 = RobotMap.driveMult * Robot.m_oi.joystick1.getRawAxis(5);
    double Rad1 = Math.sqrt(Math.pow(axis0, 2) + Math.pow(axis1, 2));
    double Rad2 = Math.sqrt(Math.pow(axis4, 2) + Math.pow(axis5, 2));
    if (Rad1 < RobotMap.JOYSTICK_DEADBAND) { axis0 = 0.0; axis1 = 0.0; }
    if (Rad2 < RobotMap.JOYSTICK_DEADBAND) { axis4 = 0.0; axis5 = 0.0; }

    double mult = 0.8;
    double rotMult = 0.45;

    if (Robot.m_oi.dterribleRight.get()) rotMult = 0.8;
    if (Robot.m_oi.dterribleLeft.get()) mult = 1.0;

    if(Robot.m_oi.dbumperLeft.get()) {
      mult = 0.2;
      rotMult = 0.2;
    }

    if(axis0 != 0 || axis1 != 0 || axis4 != 0) Robot.gyroSwerveDrive.gyroDrive(axis0*mult, axis1*mult, axis4*rotMult);
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
