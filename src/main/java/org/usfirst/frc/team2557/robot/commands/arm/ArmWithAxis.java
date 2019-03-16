package org.usfirst.frc.team2557.robot.commands.arm;

import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ArmWithAxis extends Command {
  public ArmWithAxis() {
    requires(Robot.arm);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.arm.arm(0);
    if(Robot.defaultUnlockArm){ RobotMap.dsArmLock.set(Value.kForward); }
  }

  @Override
  protected void execute() {
    double power = Robot.m_oi.joystick2.getRawAxis(1);
    SmartDashboard.putNumber("arm raw axis", power);
    if(power > -RobotMap.JOYSTICK_DEADBAND && power < RobotMap.JOYSTICK_DEADBAND){
      power = 0.0;
    }
    SmartDashboard.putNumber("arm power", power);
    Robot.arm.arm(power * 0.8); // make arm slower for user to not break things
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