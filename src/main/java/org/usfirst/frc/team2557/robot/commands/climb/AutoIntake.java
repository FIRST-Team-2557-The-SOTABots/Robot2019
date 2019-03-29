package org.usfirst.frc.team2557.robot.commands.climb;

import org.usfirst.frc.team2557.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class AutoIntake extends Command {
  double speed;
  public AutoIntake(double speed) {
    requires(Robot.intake);
    this.speed = speed;
  }

  @Override
  protected void initialize() {
    Robot.intake.speed(0);
  }

  @Override
  protected void execute() {
    Robot.intake.speed(speed);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    Robot.intake.speed(0);
  }

  @Override
  protected void interrupted() {
    Robot.intake.speed(0);
  }
}
