package org.usfirst.frc.team2557.robot.subsystems;

import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;
import org.usfirst.frc.team2557.robot.commands.arm.ArmWithAxis;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Arm extends Subsystem {

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ArmWithAxis());
  }

  // the arm does lock. Reverse is locked. Foward is unlocked
  public void arm(double power) {

    boolean front = true;

    if (RobotMap.armRight.getSensorCollection().getQuadraturePosition() < 0) {
      front = true;
    } else {
      front = false;
    }

    // if(front){
    // if(RobotMap.armRight.getSensorCollection().getQuadraturePosition() < -5500){
    // RobotMap.armRight.set(0);
    // RobotMap.armLeft.set(0);
    // }else{
    // if(Robot.m_oi.mbumperRight.get()){
    // RobotMap.dsArmLock.set(Value.kReverse);
    // }else if(Robot.m_oi.mbumperLeft.get()){
    // RobotMap.dsArmLock.set(Value.kForward);
    // }
    // RobotMap.armLeft.set(power * 0.4);
    // RobotMap.armRight.set(-power * 0.4);
    // }
    // }else{
    // if(RobotMap.armRight.getSensorCollection().getQuadraturePosition() > 7000){
    // RobotMap.armRight.set(0);
    // RobotMap.armLeft.set(0);
    // }else{
    // if(Robot.m_oi.mbumperRight.get()){
    // RobotMap.dsArmLock.set(Value.kReverse);
    // }else if(Robot.m_oi.mbumperLeft.get()){
    // RobotMap.dsArmLock.set(Value.kForward);
    // }
    // RobotMap.armLeft.set(power * 0.4);
    // RobotMap.armRight.set(-power * 0.4);
    // }
    // }
    if (Robot.m_oi.mbumperRight.get()) {
      RobotMap.dsArmLock.set(Value.kReverse);
    } else if (Robot.m_oi.mbumperLeft.get()) {
      RobotMap.dsArmLock.set(Value.kForward);
    }
    RobotMap.armLeft.set(power * 0.4);
    RobotMap.armRight.set(-power * 0.4);
  }
}
