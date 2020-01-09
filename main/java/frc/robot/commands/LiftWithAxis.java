package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotContainer;

public class LiftWithAxis extends CommandBase {
  public LiftWithAxis() {
    addRequirements(Robot.lift);
  }

  @Override
  public void initialize() {
    Robot.lift.lift(0);
  }

  @Override
  public void execute() {
    Robot.lift.lift(RobotContainer.manipulator.getRawAxis(5));
  }
  
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
