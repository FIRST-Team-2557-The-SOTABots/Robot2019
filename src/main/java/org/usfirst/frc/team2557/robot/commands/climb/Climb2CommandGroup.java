package org.usfirst.frc.team2557.robot.commands.climb;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Climb2CommandGroup extends CommandGroup {
  public Climb2CommandGroup() {
    addParallel(new LiftClimb(-212000));
    addParallel(new AutoIntake(-0.1));
    addSequential(new Climb(6250, -0.75));

    addParallel(new LiftClimb(-212000));
    addParallel(new AutoIntake(-1));

    addSequential(new TimedDrive(2, 0, 0.5, 0));
    addSequential(new TimedDrive(10, 0, 0.5, 0));
  }
}
