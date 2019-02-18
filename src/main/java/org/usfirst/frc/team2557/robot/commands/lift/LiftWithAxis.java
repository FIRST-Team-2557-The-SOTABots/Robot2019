package org.usfirst.frc.team2557.robot.commands.lift;

import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

public class LiftWithAxis extends Command {
  public LiftWithAxis() {
    requires(Robot.lift);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.lift.lift(0);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    if(Robot.m_oi.mback.get()){
      Robot.lift.lift(Robot.m_oi.joystick2.getRawAxis(1));
    }else{
      // if(RobotMap.lift2.getSensorCollection().getQuadraturePosition() < 564000){
        Robot.lift.lift(Robot.m_oi.joystick2.getRawAxis(1));
      // }else{
        // Robot.lift.lift(0);
      // }
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.lift.lift(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.lift.lift(0);
  }
}
