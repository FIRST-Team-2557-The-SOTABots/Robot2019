package org.usfirst.frc.team2557.robot.subsystems;

import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;
import org.usfirst.frc.team2557.robot.commands.lift.*;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Lift extends Subsystem {

  boolean gear;

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new LiftWithAxis());
  }

  //low gear is when you move. High gear is kReverse and when it wants to go slower
  public void lift (double power){

    //practice bot has the third one being negative
    //real bot has the second one


    if(Robot.m_oi.mstart.get()){
      RobotMap.dsLift.set(Value.kForward);
      RobotMap.lift1.set(power);
      RobotMap.lift2.set(-power);
      RobotMap.lift3.set(power);
    }else{
      RobotMap.dsLift.set(Value.kReverse);
      RobotMap.lift1.set(power);
      RobotMap.lift2.set(-power);
      RobotMap.lift3.set(power);
    }
  }
}
