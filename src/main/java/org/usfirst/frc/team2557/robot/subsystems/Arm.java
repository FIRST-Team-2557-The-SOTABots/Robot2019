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

  public void initialize(){
    RobotMap.armRight.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Coast);
    RobotMap.armLeft.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Coast);
    RobotMap.armLeft.getSensorCollection().setQuadraturePosition(0, 1);
		RobotMap.armRight.getSensorCollection().setQuadraturePosition(0, 10);
  }

  // the arm does lock. Reverse is locked. Foward is unlocked
  public void arm(double power) {
    // power *= 0.75;
    if (Robot.m_oi.mbumperRight.get()) {
      RobotMap.dsArmLock.set(Value.kReverse);
    } else if (Robot.m_oi.mbumperLeft.get()) {
      RobotMap.dsArmLock.set(Value.kForward);
    }
    RobotMap.armLeft.set(power);
    RobotMap.armRight.set(-power);

    SmartDashboard.putNumber("armPow", power);
  }
}
