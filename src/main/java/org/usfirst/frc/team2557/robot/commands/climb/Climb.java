package org.usfirst.frc.team2557.robot.commands.climb;

import org.usfirst.frc.team2557.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class Climb extends Command {
  public Climb() {
    requires(Robot.climb);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    Robot.climb.climb();
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
    Robot.climb.cancel();
  }
}
