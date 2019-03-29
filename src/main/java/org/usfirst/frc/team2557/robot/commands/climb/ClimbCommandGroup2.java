/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2557.robot.commands.climb;

import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;
import org.usfirst.frc.team2557.robot.commands.arm.UsefulPIDarm;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ClimbCommandGroup2 extends CommandGroup {
  public ClimbCommandGroup2() {
    addParallel(new TimedDrive(10, 0, 0.2, 0));
    addParallel(new AutoIntake(0));
    addSequential(new AutoLift(0));

    addParallel(new TimedDrive(10, 0, 0.2, 0));
    // System.out.println("not lock");
    addSequential(new AutoArmLock(false));
    
    // System.out.println("Yes lock");

    addParallel(new TimedDrive(10, 0, 0.2, 0));
    addParallel(new UsefulPIDarm(750));
    addSequential(new Climb(0, .75));

    // System.out.println("After climb retract");

    addSequential(new TimedDrive(10, 0, 0.2, 0));





    // addSequential(new TimedDrive(1, 0, 0.5, 0));

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
