package org.usfirst.frc.team2557.robot.commands.climb;

import org.usfirst.frc.team2557.robot.commands.arm.UsefulPIDarm;

// import org.usfirst.frc.team2557.robot.commands.arm.UsefulPIDarm;
// import org.usfirst.frc.team2557.robot.commands.lift.LiftEncoder;
// import org.usfirst.frc.team2557.robot.commands.lift.PIDlift;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class ClimbCommandGroup extends CommandGroup {
  /**
   * Add your docs here.
   */
  public ClimbCommandGroup() {
    // addSequential(new UsefulPIDarm(0));

    addParallel(new LiftClimb());
    addParallel(new AutoIntake(-0.1));
    addSequential(new Climb(-16500, 0.75));

    addParallel(new LiftClimb());
    addParallel(new AutoIntake(-1));
    addSequential(new TimedDrive(2, 0, -0.5, 0));
    addSequential(new TimedDrive(10, 0, -0.5, 0));

    // addParallel(new AutoIntake(0));
    // addParallel(new AutoLift(0));
    // addSequential(new TimedDrive(2, 0, 0.5, 0));

    // addSequential(new AutoArmLock(false));
    // // addSequential(new Climb(0, -.75));
    // addSequential(new UsefulPIDarm(0));
    // addSequential(new TimedDrive(1, 0, 0.5, 0));

    // Add Commands here:
    // e.g. addSequential(new Command1());
    // addSequential(new Command2());
    // these will run in order.

    //go to top and angle arm
    // addSequential(new LiftEncoder(0));
    // addSequential(new UsefulPIDarm(5000));

    //lower lift to 6" above ground (touching level 2)
    // addSequential(new LiftEncoder(-223000));

    //lower arm and climber simultaneously
    // addParallel(new Climb(-6500));
    // addSequential(new LiftEncoder(-0));
    
    // run outake to go forward
    // addSequential(new ForwardClimb(5));

    // addSequential(new DistanceDriveCommand(10));

    // To run multiple commands at the same time,
    // use addParallel()
    // e.g. addParallel(new Command1());
    // addSequential(new Command2());
    // Command1 and Command2 will run in parallel.

    // A command group will require all of the subsystems that each member
    // would require.
    // e.g. if Command1 requires chassis, and Command2 requires arm,
    // a CommandGroup csontaining them would require both the chassis and the
    // arm.
  }
}
