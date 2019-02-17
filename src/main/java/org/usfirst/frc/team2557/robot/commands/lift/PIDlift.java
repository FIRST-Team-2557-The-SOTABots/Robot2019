/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2557.robot.commands.lift;

import org.usfirst.frc.team2557.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class PIDlift extends CommandGroup {
  double target;

  public PIDlift(double target) {
    this.target = target;

    if(RobotMap.lift1.getSensorCollection().getQuadraturePosition() > target){
      addSequential(new PIDdown(target));
    }else if(RobotMap.lift1.getSensorCollection().getQuadraturePosition() < target){
      addSequential(new PIDup(target));
    }

  }
}
