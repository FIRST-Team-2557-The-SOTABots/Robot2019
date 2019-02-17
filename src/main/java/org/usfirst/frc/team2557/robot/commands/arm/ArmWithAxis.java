package org.usfirst.frc.team2557.robot.commands.arm;
import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

public class ArmWithAxis extends Command {
  public ArmWithAxis() {
    requires(Robot.arm);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.arm.arm(0);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

      if(Robot.m_oi.joystick2.getRawAxis(5) > 0.05 || Robot.m_oi.joystick2.getRawAxis(5) < 0.05){
        Robot.arm.arm(Robot.m_oi.joystick2.getRawAxis(5));
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
    Robot.arm.arm(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.arm.arm(0);
  }
}
