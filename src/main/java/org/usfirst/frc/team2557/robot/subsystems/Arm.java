package org.usfirst.frc.team2557.robot.subsystems;

import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Arm extends Subsystem {
  boolean prevlock;
  boolean prevOff;

  @Override
  public void initDefaultCommand() {
    // setDefaultCommand(new ArmWithAxis());
  }

  public void abc(){
    prevlock = false;
    prevOff = false;
    RobotMap.armRight.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Coast);
    RobotMap.armLeft.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Coast);
    // RobotMap.armLeft.getSensorCollection().setQuadraturePosition(0, 1);
		// RobotMap.armRight.getSensorCollection().setQuadraturePosition(0, 10);
  }

  // the arm does lock. Reverse is locked. Foward is unlocked
  // do not mess with lock code bc it is very breakable. switch to the commented out if else for bumpers instead of mx
  public void arm(double power) {
    boolean on = Robot.m_oi.mbumperRight.get();
    if(on && !prevlock && prevOff){
      RobotMap.dsArmLock.set(Value.kReverse);
    }else if(Robot.m_oi.mbumperRight.get() && prevlock && prevOff){
      RobotMap.dsArmLock.set(Value.kForward);
    }

    if(RobotMap.dsArmLock.get() == Value.kReverse){
      prevlock = true;
    }else{
      prevlock = false;
    }
    prevOff = !on;

    // IMPORTANT DO NOT DELETE !!! BACKUP CODE FOR BUMPER LOCK !!!
    // if (Robot.m_oi.mbumperRight.get()) {
    //   RobotMap.dsArmLock.set(Value.kReverse);
    // } else if (Robot.m_oi.mbumperLeft.get()) {
    //   RobotMap.dsArmLock.set(Value.kForward);
    // }

    // if(RobotMap.lift2.getSensorCollection().getQuadraturePosition() < -40000){
    //   if(RobotMap.armRight.getSensorCollection().getQuadraturePosition() < -200 && power < 0){
    //     RobotMap.armLeft.set(power);
    //     RobotMap.armRight.set(-power);
    //   }else if(RobotMap.armRight.getSensorCollection().getQuadraturePosition() > 200 && power > 0){
    //     RobotMap.armLeft.set(power);
    //     RobotMap.armRight.set(-power);
    //   }else{
    //     RobotMap.armLeft.set(0);
    //     RobotMap.armRight.set(0);
    //     // RobotMap.dsArmLock.set(Value.kReverse);
      // }
    // }else if(RobotMap.armRight.getSensorCollection().getQuadraturePosition() > -5500 || (RobotMap.armRight.getSensorCollection().getQuadraturePosition() < 5500)){
      RobotMap.armLeft.set(power);
      RobotMap.armRight.set(-power);
    // }else{
      // RobotMap.armLeft.set(0);
      // RobotMap.armRight.set(0);
    // }
  }
}
