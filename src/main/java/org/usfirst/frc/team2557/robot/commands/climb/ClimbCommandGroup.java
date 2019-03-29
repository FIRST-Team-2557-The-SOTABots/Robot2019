package org.usfirst.frc.team2557.robot.commands.climb;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ClimbCommandGroup extends CommandGroup {
  public ClimbCommandGroup(double liftOffset, double climbTarget) {
    addParallel(new LiftClimb(liftOffset));
    addParallel(new AutoIntake(-0.1));
    addSequential(new Climb(climbTarget, -0.75));

    addParallel(new LiftClimb(liftOffset));
    addParallel(new AutoIntake(-1));

    addSequential(new TimedDrive(2, 0, 0.5, 0));
    addSequential(new TimedDrive(10, 0, 0.5, 0));
  }
}
