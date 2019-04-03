package org.usfirst.frc.team2557.robot.commands.climb;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RetractClimbDrive extends CommandGroup {
  public RetractClimbDrive() {
    addParallel(new TimedDrive(10, 0.1));
    addSequential(new Climb(2000, 1));
    addSequential(new TimedDrive(10, 0.2));
  }
}
