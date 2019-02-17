package org.usfirst.frc.team2557.robot.subsystems;

import org.usfirst.frc.team2557.robot.RobotMap;
import org.usfirst.frc.team2557.robot.commands.lift.*;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Lift extends Subsystem {

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new LiftWithAxis());
  }

  public void lift (double power){
    RobotMap.lift1.set(power);
    RobotMap.lift2.set(power);
    RobotMap.lift3.set(-power);
  }
}
