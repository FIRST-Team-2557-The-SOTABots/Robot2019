package org.usfirst.frc.team2557.robot.commands.climb;

import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

public class AutoArmLock extends Command {
  boolean lock;
  public AutoArmLock(boolean lock) {
    requires(Robot.arm);
    this.lock = lock;
  }

  @Override
  protected void initialize() {
    Robot.arm.arm(0);
  }

  @Override
  protected void execute() {
    if(lock) RobotMap.dsArmLock.set(Value.kReverse); 
    else RobotMap.dsArmLock.set(Value.kForward);
  }

  @Override
  protected boolean isFinished() {
    if(lock) return RobotMap.dsArmLock.get() == Value.kReverse;
    else return RobotMap.dsArmLock.get() == Value.kForward;
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
