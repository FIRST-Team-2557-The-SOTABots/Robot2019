package org.usfirst.frc.team2557.robot.subsystems;

import org.usfirst.frc.team2557.robot.RobotMap;
import org.usfirst.frc.team2557.robot.commands.intake.IntakeTrigger;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem {

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new IntakeTrigger());
  }

  public void speed (double power) {
    // if( ((RobotMap.cargo.get() ||  RobotMap.cargo2.get()) && Math.signum(power) == 1 ) && (Robot.m_oi.joystick2.getPOV() != 135))RobotMap.intake.set(0);
    // else RobotMap.intake.set(power);

    RobotMap.intake.set(power);
  }
}
