package org.usfirst.frc.team2557.robot.commands.drive;

import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TofDrive extends Command {
  double fwd = 0.05;
  double fwdCmp = 0;
  double angle = 0;
  // double tofr = 0;
  // double tofl = 0;

  PIDController pidcontrollerrot;
  double midrot;
  double kProt;
  double kIrot;
  double kDrot;
  double tolerancerot;
  double outputrot;

  public TofDrive() {
    requires(Robot.gyroSwerveDrive);

    outputrot = 0;
    kProt = 0.002;
		kIrot = 0.0000;
    kDrot = 0.00;
    tolerancerot = 0.1;
		pidcontrollerrot = new PIDController(kProt, kIrot, kDrot, new PIDSource(){
			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
			}

			@Override
			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kDisplacement;
			}

			@Override
			public double pidGet() {
				return midrot;
			}
		}, new PIDOutput(){
			@Override
			public void pidWrite(double output) {
          outputrot = output;
			}
		});
		pidcontrollerrot.setOutputRange(-1, 1);
		pidcontrollerrot.setAbsoluteTolerance(tolerancerot);
    
    Robot.gyroSwerveDrive.fcd = false;
  }

  @Override
  protected void initialize() {
    Robot.gyroSwerveDrive.gyroDrive(0, 0, 0);
    Robot.gyroSwerveDrive.fcd = false;
    pidcontrollerrot.reset();
    pidcontrollerrot.setSetpoint(0.0);
    pidcontrollerrot.enable();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(Robot.gyroSwerveDrive.fcd) Robot.gyroSwerveDrive.fcd = false;
    getForward();
    getAngle();
    midrot = angle;

    SmartDashboard.putNumber("climb rot input", midrot);
    SmartDashboard.putNumber("climb rot output", pidcontrollerrot.get());
    // SmartDashboard.putNumber("climb angle", angle);
    // SmartDashboard.putNumber("climb tofr", tofr);
    // SmartDashboard.putNumber("climb tofl", tofl);

    Robot.gyroSwerveDrive.gyroDrive(0, fwdCmp, -outputrot);
  }

  private void getAngle() {
    // tofr = RobotMap.arduino.get("ToFR");
    // tofl = RobotMap.arduino.get("ToFL");
    angle = RobotMap.tofAngle;
  }


  private void getForward() {
    fwdCmp = fwd;
    if(RobotMap.arduino.get("ToFR") < 300 || RobotMap.arduino.get("ToFL") < 300){
      fwdCmp = 0;
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return pidcontrollerrot.onTarget();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.gyroSwerveDrive.fcd = true;
    pidcontrollerrot.disable();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.gyroSwerveDrive.fcd = true;
    pidcontrollerrot.disable();
		this.end();
  }
}
