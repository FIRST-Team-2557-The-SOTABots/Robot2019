/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2557.robot.commands.climb;

import org.usfirst.frc.team2557.robot.commands.arm.UsefulPIDarm;
import org.usfirst.frc.team2557.robot.commands.lift.PIDlift;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ClimbCommandGroup extends CommandGroup {
  /**
   * Add your docs here.
   */
  public ClimbCommandGroup() {
    // Add Commands here:
    // e.g. addSequential(new Command1());
    // addSequential(new Command2());
    // these will run in order.

    //go to top and angle arm
    addSequential(new PIDlift(0));
    addSequential(new UsefulPIDarm(5000));

    //lower lift to 6" above ground (touching level 2)
    addSequential(new PIDlift(-223000));

    //lower arm and climber simultaneously
    addParallel(new Climb(-6500));
    addSequential(new PIDlift(-233000));
    
    // run outake to go forward
    addSequential(new ForwardClimb(5));

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
