package org.usfirst.frc.team2557.robot.commands.climb;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ClimbCommandGroup extends CommandGroup {
  public ClimbCommandGroup(int climbTarget) {
    addParallel(new ClimbByGyro(climbTarget));
    addParallel(new AutoIntake(-0.1));

    addSequential(new Climb(climbTarget, -1));

    addParallel(new AutoIntake(-1));
    addSequential(new TimedDrive(4, 0.3)); // 2

    addSequential(new TimedDrive(10, 0.3));
  }
}
