package org.usfirst.frc.team2557.robot.commands.drive;

import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Command;

public class SpinToWin extends Command {
  double angle;

  public SpinToWin(double angle) {
    requires(Robot.gyroSwerveDrive);
    this.angle = angle;
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    if(RobotMap.gyro.getAngle() > angle){
      Robot.gyroSwerveDrive.gyroDrive(0, 0, 1.0);
    }else if(RobotMap.gyro.getAngle() < angle){
      Robot.gyroSwerveDrive.gyroDrive(0, 0, -1.0);
    }
  }

  @Override
  protected boolean isFinished() {
    if(Math.abs(RobotMap.gyro.getAngle() - angle) < 2.0){
      return true;
    }
    return false;
  }

  @Override
  protected void end() {
  }

  @Override
  protected void interrupted() {
  }
}
