package org.usfirst.frc.team2557.robot.commands.climb;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RetractClimb extends CommandGroup {
  public RetractClimb() {
    addParallel(new RetractLiftArm());
    addSequential(new RetractClimbDrive());
  }
}
