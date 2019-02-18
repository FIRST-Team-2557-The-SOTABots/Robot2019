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

    if(Robot.m_oi.bumperRight.get()){
      RobotMap.ds8inch.set(Value.kForward);
    }else if(Robot.m_oi.bumperLeft.get()){
      RobotMap.ds8inch.set(Value.kReverse);
    }

    if(Robot.m_oi.dx.get()){
      RobotMap.ds12inch.set(Value.kForward);
    }else if(Robot.m_oi.dy.get()){
      RobotMap.ds12inch.set(Value.kReverse);
    }
    // if(Robot.m_oi.bumperRight.get()){
    //   RobotMap.ds8inch.set(Value.kForward);
    //   RobotMap.ds12inch.set(Value.kForward);
    // }else if(Robot.m_oi.bumperLeft.get()){
    //   RobotMap.ds8inch.set(Value.kReverse);
    //   RobotMap.ds12inch.set(Value.kReverse);
    // }
  }

  public void cancel() {

  }
}
