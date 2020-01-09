/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class Intake extends SubsystemBase {
  /**
   * Creates a new Intake.
   */
  public Intake() {

  }

  public void speed (double leftTrigger, double rightTrigger) {
    Constants.intake.set(leftTrigger>rightTrigger ? leftTrigger : -rightTrigger);

    // Constants.intake.set(power);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
