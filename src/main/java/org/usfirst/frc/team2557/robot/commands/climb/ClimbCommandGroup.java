/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2557.robot.commands.climb;

import org.usfirst.frc.team2557.robot.commands.arm.PIDarm;
import org.usfirst.frc.team2557.robot.commands.arm.UsefulPIDarm;
import org.usfirst.frc.team2557.robot.commands.drive.DistanceDriveCommand;
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
    addSequential(new PIDlift(0));

    addSequential(new UsefulPIDarm(-3536));
    addSequential(new PIDlift(-153000));

    // addParallel(new Piston8());
    // addSequential(new PIDlift(-244874));

    // addSequential(new DistanceDriveCommand(0.09, 0.66));
    // addSequential(new Piston8Retract());

    
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
