
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

  public void init(){
    RobotMap.lift1.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
		RobotMap.lift2.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
    RobotMap.lift3.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
  }

  // low gear is when you move. High gear is kReverse and when it wants to go slower
  public void lift(double power) {
      if (Robot.m_oi.mstart.get()) {
        RobotMap.dsLift.set(Value.kForward);
      } else {
        RobotMap.dsLift.set(Value.kReverse);
      }

      if (Robot.m_oi.mbumperLeft.get()) {
        power *= 0.3;
      } else {
        power *= 0.8;
      }

      RobotMap.lift1.set(power);
      RobotMap.lift2.set(power);
      RobotMap.lift3.set(power);
  }
}
