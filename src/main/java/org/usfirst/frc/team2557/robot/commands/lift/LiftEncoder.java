package org.usfirst.frc.team2557.robot.commands.lift;

import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Command;

public class LiftEncoder extends Command {
  double setpoint;
  public LiftEncoder(double setpoint) {
    this.setpoint = setpoint;
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    if(RobotMap.lift2.getSensorCollection().getQuadraturePosition() < setpoint){
      Robot.lift.lift(0.2);
    }

  }

  @Override
  protected boolean isFinished() {
    if(Math.abs(RobotMap.lift2.getSensorCollection().getQuadraturePosition() - setpoint) > 5000) return true;
    return false;
  }

  @Override
  protected void end() {
    Robot.lift.lift(0.0);
  }

  @Override
  protected void interrupted() {
  }
}
