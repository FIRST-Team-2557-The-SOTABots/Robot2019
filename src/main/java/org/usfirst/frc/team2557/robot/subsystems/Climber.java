package org.usfirst.frc.team2557.robot.subsystems;

import org.usfirst.frc.team2557.robot.commands.climb.*;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Climber extends Subsystem {

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new Climb());
  }

  public void climb(){

  }

  public void cancel() {

  }
}
