package org.usfirst.frc.team2557.robot.commands.climb;

import org.usfirst.frc.team2557.robot.commands.arm.UsefulPIDarm;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class RetractClimb extends CommandGroup {
  public RetractClimb() {
    addParallel(new TimedDrive(10, 0, 0.2, 0));
    addParallel(new AutoIntake(0));
    addSequential(new AutoLift(0));

    addParallel(new TimedDrive(10, 0, 0.2, 0));
    addSequential(new AutoArmLock(false));

    addParallel(new TimedDrive(10, 0, 0.2, 0));
    addParallel(new UsefulPIDarm(750));
    addSequential(new Climb(0, .75));

    addSequential(new TimedDrive(10, 0, 0.2, 0));
  }
}
