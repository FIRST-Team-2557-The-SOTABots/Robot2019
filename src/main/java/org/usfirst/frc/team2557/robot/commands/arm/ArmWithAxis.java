package org.usfirst.frc.team2557.robot.commands.arm;

import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

public class ArmWithAxis extends Command {
  public ArmWithAxis() {
    requires(Robot.arm);
  }

  @Override
  protected void initialize() {
    Robot.arm.arm(0);
    if(Robot.defaultUnlockArm){ RobotMap.dsArmLock.set(Value.kForward); }
  }

  @Override
  protected void execute() {
    double power = Robot.m_oi.joystick2.getRawAxis(1);
    if(power > -RobotMap.JOYSTICK_DEADBAND && power < RobotMap.JOYSTICK_DEADBAND){
      power = 0.0;
    }
    Robot.arm.arm(power * 0.8); // make arm slower for user to not break things
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    Robot.arm.arm(0);
  }

  @Override
  protected void interrupted() {
    Robot.arm.arm(0);
  }
}