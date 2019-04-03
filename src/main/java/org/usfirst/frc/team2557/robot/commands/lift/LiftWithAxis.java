package org.usfirst.frc.team2557.robot.commands.lift;

import org.usfirst.frc.team2557.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class LiftWithAxis extends Command {
  public LiftWithAxis() {
    requires(Robot.lift);
  }

  @Override
  protected void initialize() {
    Robot.lift.lift(0);
  }

  @Override
  protected void execute() {
      Robot.lift.lift(Robot.m_oi.joystick2.getRawAxis(5));
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    Robot.lift.lift(0);
  }

  @Override
  protected void interrupted() {
    Robot.lift.lift(0);
  }
}
