package org.usfirst.frc.team2557.robot.subsystems;

import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;
import org.usfirst.frc.team2557.robot.commands.climb.Climb;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Climber extends Subsystem {
  @Override
  public void initDefaultCommand() {
  }

  public void lock(boolean yes){
    if(yes){
      if(RobotMap.dsClimbLock.get() != Value.kForward){
        RobotMap.dsClimbLock.set(Value.kForward);
      }
    }else{
      if(RobotMap.dsClimbLock.get() != Value.kReverse){
        RobotMap.dsClimbLock.set(Value.kReverse);
      }
    }
  }

  public void climb(double power){
    RobotMap.climber.set (power);
  }

  public void cancel() {
    RobotMap.climber.set (0);
  }
}
