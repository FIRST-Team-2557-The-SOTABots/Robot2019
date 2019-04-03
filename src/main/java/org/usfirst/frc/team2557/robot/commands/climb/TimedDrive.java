package org.usfirst.frc.team2557.robot.commands.climb;

import org.usfirst.frc.team2557.robot.Robot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class TimedDrive extends Command {
  Timer t;
  double time;
  double fwd;
  public TimedDrive(double time, double fwd) {
    requires(Robot.swerve);
    t = new Timer();
    this.time = time;
    this.fwd = fwd;
  }

  @Override
  protected void initialize() {
    Robot.swerve.gyroDrive(0, 0, 0);
    t.reset();
    t.start();
  }

  @Override
  protected void execute() {
    Robot.swerve.driveStraight(fwd);
  }

  @Override
  protected boolean isFinished() {
    return t.get() > time;
    // return false;
  }

  @Override
  protected void end() {
    Robot.swerve.driveStraight(0);
  }

  @Override
  protected void interrupted() {
    Robot.swerve.gyroDrive(0, 0, 0);
  }
}
