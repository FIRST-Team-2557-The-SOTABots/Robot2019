package org.usfirst.frc.team2557.robot.commands.climb;

import org.usfirst.frc.team2557.robot.RobotMap;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class ForwardClimb extends Command {
  double time;
  Timer timer = new Timer();
  public ForwardClimb(double time) {
  }

  @Override
  protected void initialize() {
    timer.start();
  }

  @Override
  protected void execute() {
    RobotMap.intake.set(-0.5);
  }

  @Override
  protected boolean isFinished() {
    if(timer.get() > time) return true;
    return false;
  }
  
  @Override
  protected void end() {
    RobotMap.intake.set(0.0);
  }
  
  @Override
  protected void interrupted() {
    RobotMap.intake.set(0.0);
  }
}