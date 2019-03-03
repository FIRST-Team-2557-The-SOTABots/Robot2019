package org.usfirst.frc.team2557.robot.subsystems;

import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;
import org.usfirst.frc.team2557.robot.commands.climb.*;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Climber extends Subsystem {

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new Climb());
  }

  public void climb(){
    //  dydoes all retract
    if(Robot.m_oi.dy.get()){
      RobotMap.ds8inch.set(Value.kForward);
      RobotMap.ds12inch.set(Value.kForward);
    }
    //dstart does 12" extrude
    if(Robot.m_oi.start.get()){
      RobotMap.ds12inch.set(Value.kReverse);
    }
    //dback does  8" extrude
    if(Robot.m_oi.back.get()){
      RobotMap.ds8inch.set(Value.kReverse);
    }
  }

  public void cancel() {
  }
}
