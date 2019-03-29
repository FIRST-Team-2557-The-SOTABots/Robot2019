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
  double fwd = 0.1;
  double fwdCmp = 0;
  double angle = 0;

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
    // kProt = 0.0005;
    kProt = 0.005;
    kIrot = 0.000008;
    kDrot = 0.00008;
    tolerancerot = 0.05;
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
    
  }

  @Override
  protected void initialize() {
    Robot.gyroSwerveDrive.driveStraight(0);
    pidcontrollerrot.reset();
    pidcontrollerrot.setSetpoint(0.0);
    pidcontrollerrot.enable();
  }

  @Override
  protected void execute() {
    getForward();
    getAngle();
    midrot = angle;

    SmartDashboard.putNumber("climb rot input", midrot);
    SmartDashboard.putNumber("climb rot output", pidcontrollerrot.get());
    // SmartDashboard.putNumber("climb angle", angle);
    // SmartDashboard.putNumber("climb tofr", tofr);
    // SmartDashboard.putNumber("climb tofl", tofl);

    Robot.gyroSwerveDrive.drive(0, fwdCmp, -outputrot);
  }

  private void getAngle() {
    // tofr = RobotMap.arduino.get("ToFR");
    // tofl = RobotMap.arduino.get("ToFL");
    angle = RobotMap.tofAngle;
  }


  private void getForward() {
    fwdCmp = fwd;
    if(RobotMap.arduino.get("ToFR") < 8000 || RobotMap.arduino.get("ToFL") < 8000){
      fwdCmp = 0;
    }
  }

  @Override
  protected boolean isFinished() {
    return pidcontrollerrot.onTarget();
  }

  @Override
  protected void end() {
    pidcontrollerrot.disable();
  }

  @Override
  protected void interrupted() {
    pidcontrollerrot.disable();
		this.end();
  }
}
