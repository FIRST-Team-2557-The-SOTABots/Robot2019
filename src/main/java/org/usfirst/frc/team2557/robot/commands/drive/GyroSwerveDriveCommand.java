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
    RobotMap.gyro.reset();
  }

  @Override
  protected void execute() {
    for(int i = 0; i < 4; i++){
      RobotMap.swerveMod[i].drive(Robot.m_oi.joystick1.getRawAxis(1), Robot.m_oi.joystick1.getRawAxis(4));
    }

    // double axis0 = -Robot.m_oi.joystick1.getRawAxis(0);
    // double axis1 = -Robot.m_oi.joystick1.getRawAxis(1);
    // double axis4 = -Robot.m_oi.joystick1.getRawAxis(4);
    // double axis5 = Robot.m_oi.joystick1.getRawAxis(5);
    // double triggerLeft = Robot.m_oi.joystick1.getRawAxis(2);
    // double triggerRight = Robot.m_oi.joystick1.getRawAxis(3);

    // if (axis0 < RobotMap.JOYSTICK_DEADBAND && axis0 > -RobotMap.JOYSTICK_DEADBAND) axis0 = 0.0;
		// if (axis1 < RobotMap.JOYSTICK_DEADBAND && axis1 > -RobotMap.JOYSTICK_DEADBAND) axis1 = 0.0;
		// if (axis4 < RobotMap.JOYSTICK_DEADBAND && axis4 > -RobotMap.JOYSTICK_DEADBAND) axis4 = 0.0;
    // if (axis5 < RobotMap.JOYSTICK_DEADBAND && axis5 > -RobotMap.JOYSTICK_DEADBAND) axis5 = 0.0;
    // if (triggerLeft < RobotMap.TRIGGER_DEADBAND && triggerLeft > -RobotMap.TRIGGER_DEADBAND) triggerLeft = 0;
    // if (triggerRight < RobotMap.TRIGGER_DEADBAND && triggerRight > -RobotMap.TRIGGER_DEADBAND) triggerRight = 0;

    // if(Robot.m_oi.dx.get()) RobotMap.gyro.reset();

    // if(Robot.m_oi.da.get()){
    //   if(triggerRight > 0.2){
    //     Robot.gyroSwerveDrive.gyroDrive(axis0*0.95, axis1*0.95, triggerRight*0.5);
    //   }else if(triggerLeft > 0.2){
    //     Robot.gyroSwerveDrive.gyroDrive(axis0*0.95, axis1*0.95, -triggerLeft*0.5);
    //   }else{
    //     Robot.gyroSwerveDrive.gyroDrive(axis0*0.95, axis1*0.95, axis4*0.5);
    //   }
    // }else if(Robot.m_oi.db.get()){
    //   if(triggerRight > 0.2){
    //     Robot.gyroSwerveDrive.gyroDrive(axis0*0.2, axis1*0.2, triggerRight*0.2);
    //   }else if(triggerLeft > 0.2){
    //     Robot.gyroSwerveDrive.gyroDrive(axis0*0.2, axis1*0.2, -triggerLeft*0.2);
    //   }else{
    //     Robot.gyroSwerveDrive.gyroDrive(axis0*0.2, axis1*0.2, axis4*0.2);
    //   }
    // }else{
    //   if(triggerRight > 0.2){
    //     Robot.gyroSwerveDrive.gyroDrive(axis0*0.5, axis1*0.5, triggerRight*0.25);
    //   }else if(triggerLeft > 0.2){
    //     Robot.gyroSwerveDrive.gyroDrive(axis0*0.5, axis1*0.5, -triggerLeft*0.25);
    //   }else{
    //     Robot.gyroSwerveDrive.gyroDrive(axis0*0.5, axis1*0.5, axis4*0.25);
    //   }
    // }


    // double mult = 0.95;
    // if(Robot.m_oi.db.get()) mult = 1;
    // else if(Robot.m_oi.da.get()) mult = 0.2;
    // if(triggerRight > 0.2) axis4 = -triggerRight;
    // else if(triggerLeft > 0.2) axis4 = triggerLeft;
    // Robot.gyroSwerveDrive.gyroDrive(axis0*mult, axis1*mult, axis4*mult);
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
