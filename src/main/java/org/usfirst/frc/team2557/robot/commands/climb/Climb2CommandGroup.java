/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2557.robot.commands.climb;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Climb2CommandGroup extends CommandGroup {
  /**
   * Add your docs here.
   */
  public Climb2CommandGroup() {
    addParallel(new LiftClimb(-212000));
    System.out.println("Here in Climb 2");
    addParallel(new AutoIntake(-0.1));
    addSequential(new Climb(6250, -0.75));

    System.out.println("CLIMBEDDD");

    addParallel(new LiftClimb(-212000));
    addParallel(new AutoIntake(-1));

    addSequential(new TimedDrive(2, 0, 0.5, 0));
    addSequential(new TimedDrive(10, 0, 0.5, 0));

    // Add Commands here:
    // e.g. addSequential(new Command1());
    // addSequential(new Command2());
    // these will run in order.

    // To run multiple commands at the same time,
    // use addParallel()
    // e.g. addParallel(new Command1());
    // addSequential(new Command2());
    // Command1 and Command2 will run in parallel.

    // A command group will require all of the subsystems that each member
    // would require.
    // e.g. if Command1 requires chassis, and Command2 requires arm,
    // a CommandGroup containing them would require both the chassis and the
    // arm.
  }
}
