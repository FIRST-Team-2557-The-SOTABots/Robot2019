package org.usfirst.frc.team2557.robot.commands.drive;

import org.usfirst.frc.team2557.robot.Robot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class DistanceDriveCommand extends Command {
  double time;
  Timer timer = new Timer();
  public DistanceDriveCommand(double time) {
    requires(Robot.gyroSwerveDrive);
  }

  
  @Override
  protected void initialize() {
    timer.start();
  }

  @Override
  protected void execute() {
    Robot.gyroSwerveDrive.gyroDrive(0, 0.08, 0);
  }

  @Override
  protected boolean isFinished() {
    if(timer.get() > time){
      return true;
    }
    return false;
  }

  @Override
  protected void end() {
    Robot.gyroSwerveDrive.gyroDrive(0, 0, 0);
  }

  @Override
  protected void interrupted() {
  }
}
