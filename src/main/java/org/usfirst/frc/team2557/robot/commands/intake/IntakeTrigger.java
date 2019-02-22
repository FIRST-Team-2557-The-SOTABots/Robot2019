package org.usfirst.frc.team2557.robot.commands.intake;

import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

public class IntakeTrigger extends Command {
  public IntakeTrigger() {
    requires(Robot.intake);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.intake.speed(0);
  }

 //forward shoots in out. Reverse retracts
  @Override
  protected void execute() {
    double trr = Robot.m_oi.joystick2.getRawAxis(3);
    double trl = Robot.m_oi.joystick2.getRawAxis(2);

    if(trr > 0.2){
      Robot.intake.speed(trr);
      RobotMap.dsIntake.set(Value.kReverse);
    }else if(trl > 0.2){
      // Robot.intake.speed(-trl);
      Robot.intake.speed(-1);
      RobotMap.dsIntake.set(Value.kForward);
    }else if(Robot.m_oi.joystick2.getPOV() == 0){
      Robot.intake.speed(1.0);
    }else if(Robot.m_oi.joystick2.getPOV() == 45){
      Robot.intake.speed(0.5);
    }else{
      Robot.intake.speed(0.0);
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
    Robot.intake.speed(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.intake.speed(0);
  }
}
