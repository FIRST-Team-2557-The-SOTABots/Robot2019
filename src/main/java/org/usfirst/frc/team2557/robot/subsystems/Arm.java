package org.usfirst.frc.team2557.robot.subsystems;

import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;
import org.usfirst.frc.team2557.robot.commands.arm.ArmWithAxis;
import org.usfirst.frc.team2557.robot.commands.arm.PIDarm;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Arm extends Subsystem {

  @Override
  public void initDefaultCommand() {
    // setDefaultCommand(new ArmWithAxis());
  }

  // the arm does lock. Reverse is locked. Foward is unlocked
  public void arm(double power) {
    double mult = 0.75;
    // if(power > 0){
    //   mult =.75;
    // }else if(power < 0){
    //   mult = .95;
    // }

    if (Robot.m_oi.mbumperRight.get()) {
      RobotMap.dsArmLock.set(Value.kReverse);
      SmartDashboard.putString("I'm in the arm subsystem. R", "it is locked");
    } else if (Robot.m_oi.mbumperLeft.get()) {
      RobotMap.dsArmLock.set(Value.kForward);
      SmartDashboard.putString("I'm in the arm subsystem. R", "it is unlocked");
    }
    RobotMap.armLeft.set(power * mult);
    RobotMap.armRight.set(-power * mult);

    // if (front) {
    //   if (RobotMap.armRight.getSensorCollection().getQuadraturePosition() < -5500) {
    //     RobotMap.armRight.set(0);
    //     RobotMap.armLeft.set(0);
    //   } else {
    //     if (Robot.m_oi.mbumperRight.get()) {
    //       RobotMap.dsArmLock.set(Value.kReverse);
    //     } else if (Robot.m_oi.mbumperLeft.get()) {
    //       RobotMap.dsArmLock.set(Value.kForward);
    //     }
    //     RobotMap.armLeft.set(power * 0.4);
    //     RobotMap.armRight.set(-power * 0.4);
    //   }
    // } else {
    //   if (RobotMap.armRight.getSensorCollection().getQuadraturePosition() > 7000) {
    //     RobotMap.armRight.set(0);
    //     RobotMap.armLeft.set(0);
    //   } else {
    //     if (Robot.m_oi.mbumperRight.get()) {
    //       RobotMap.dsArmLock.set(Value.kReverse);
    //     } else if (Robot.m_oi.mbumperLeft.get()) {
    //       RobotMap.dsArmLock.set(Value.kForward);
    //     }
    //     RobotMap.armLeft.set(power * 0.4);
    //     RobotMap.armRight.set(-power * 0.4);
    //   }
    // }
  }
}
