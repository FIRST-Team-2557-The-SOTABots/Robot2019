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

  public void arm (double power) {
    if(Robot.m_oi.ma.get()){
      RobotMap.dsArmLock.set(Value.kReverse);
    }else{
      RobotMap.dsArmLock.set(Value.kForward);
      RobotMap.armLeft.set(power * 0.2);
      RobotMap.armRight.set(-power * 0.2);
    }
  }
}
