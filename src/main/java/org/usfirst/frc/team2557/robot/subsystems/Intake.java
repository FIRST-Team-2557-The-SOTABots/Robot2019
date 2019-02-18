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
    RobotMap.intake.set(power);
  }

  // public void sol (boolean fire) {
  //   RobotMap.dsIntake.set(fire);
  // }
}