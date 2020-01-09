/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class BasicArmSub extends SubsystemBase {
  boolean prevlock, prevOff;
  public BasicArmSub() {
  }
  public static int clamp(int value, int low, int high) {
    return Math.max(low, Math.min(value, high));
  }

  public void arm(double pow){
    // boolean on = RobotContainer.mbumperRight.get();
    // if(on && !prevlock && prevOff){
    //   Constants.dsArmLock.set(Value.kReverse);
    // }else if(RobotContainer.mbumperRight.get() && prevlock && prevOff){
    //   Constants.dsArmLock.set(Value.kForward);
    // }

    // if(Constants.dsArmLock.get() == Value.kReverse){
    //   prevlock = true;
    // }else{
    //   prevlock = false;
    // }
    // prevOff = !on;

    //why did we never make it so if it's locked the motors won't go?
    // if((Constants.armRight.getSensorCollection().getQuadraturePosition() > -1000) && pow > 0){
      // Constants.armLeft.set(0);
      // Constants.armRight.set(0);
    // }else{
      Constants.armLeft.set(pow);
      Constants.armRight.set(-pow);
    // }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
