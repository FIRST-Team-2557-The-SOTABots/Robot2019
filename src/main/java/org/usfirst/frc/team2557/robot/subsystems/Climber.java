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

    // right bumper makes go rectract
    if(Robot.m_oi.bumperRight.get()){
      RobotMap.ds8inch.set(Value.kForward);
    }else if(Robot.m_oi.bumperLeft.get() && Robot.m_oi.joystick1.getPOV() >= 180){
      RobotMap.ds8inch.set(Value.kReverse);
    }
    // x does the retract
    if(Robot.m_oi.dx.get()){
      RobotMap.ds12inch.set(Value.kForward);
    }else if(Robot.m_oi.dy.get() && Robot.m_oi.joystick1.getPOV() <= 135){
      RobotMap.ds12inch.set(Value.kReverse);
    }
    //right bumber does retract, left does extrude

    // if(Robot.m_oi.bumperRight.get() && Robot.m_oi.mstart.get()){
    //   RobotMap.ds8inch.set(Value.kForward);
    //   RobotMap.ds12inch.set(Value.kForward);
    // }else if(Robot.m_oi.bumperLeft.get() && Robot.m_oi.mback.get()){
    //   RobotMap.ds8inch.set(Value.kReverse);
    //   RobotMap.ds12inch.set(Value.kReverse);
    // }
  }

  public void cancel() {

  }
}
