package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;

public class GyroSwerveDriveCommand extends CommandBase {

  public GyroSwerveDriveCommand() {
    addRequirements(Robot.gyroSwerveDrive);
  }

  
  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    if (RobotContainer.driver.getPOV() == 180)
      Constants.gyro.reset();

    double axis0 = Constants.driveMult * RobotContainer.driver.getRawAxis(0);
    double axis1 = Constants.driveMult * RobotContainer.driver.getRawAxis(1);
    double axis4 = Constants.driveMult * RobotContainer.driver.getRawAxis(4);
    double axis5 = Constants.driveMult * RobotContainer.driver.getRawAxis(5);
    double Rad1 = Math.sqrt(Math.pow(axis0, 2) + Math.pow(axis1, 2));
    double Rad2 = Math.sqrt(Math.pow(axis4, 2) + Math.pow(axis5, 2));
    if (Rad1 < Constants.JOYSTICK_DEADBAND) {
      axis0 = 0.0;
      axis1 = 0.0;
    }
    if (Rad2 < Constants.JOYSTICK_DEADBAND) {
      axis4 = 0.0;
      axis5 = 0.0;
    }

    double mult = 0.8;
    double rotMult = 0.45;

    if (RobotContainer.dterribleRight.get())
      rotMult = 0.8;
    if (RobotContainer.dterribleLeft.get())
      mult = 1.0;

    if (RobotContainer.dbumperLeft.get()) {
      mult = 0.2;
      rotMult = 0.2;
    }

    if (axis0 != 0 || axis1 != 0 || axis4 != 0)
      Robot.gyroSwerveDrive.gyroDrive(axis0 * mult, axis1 * mult, axis4 * rotMult);
    else
      Robot.gyroSwerveDrive.gyroDriveAngle();
  }

  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
  
}
