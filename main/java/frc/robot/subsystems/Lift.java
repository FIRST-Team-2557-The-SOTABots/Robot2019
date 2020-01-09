package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class Lift extends SubsystemBase {

  boolean gear;

  public void init(){
    Constants.lift1.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
		Constants.lift2.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
    Constants.lift3.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
  }

  // low gear is when you move. High gear is kReverse and when it wants to go slower
  public void lift(double power) {
      if (RobotContainer.mstart.get()) {
        Constants.dsLift.set(Value.kForward);
      } else {
        Constants.dsLift.set(Value.kReverse);
      }

      if (RobotContainer.mbumperLeft.get()) {
        power *= 0.4;
      } else {
        power *= 0.8;
      }

      Constants.lift1.set(power);
      Constants.lift2.set(power);
      Constants.lift3.set(power);
  }
}
