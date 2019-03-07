package org.usfirst.frc.team2557.robot.commands.drive;

import org.usfirst.frc.team2557.robot.Robot;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionDriveStraightOn extends Command {
  double[] height = new double[2];
  double[] centerX = new double[2];
  double[] centerY = new double[2];
  double[] elevation = new double[2];

  double pixels_height = 240;
  double pixels_width = 416;
  double degView = 36.87;
  double fwd = 0.075;
  double fwdCmp = 0;

  PIDController pidcontrollerrot;
  double midrot;
  double kProt;
  double kIrot;
  double kDrot;
  double tolerancerot;
  double outputrot;

  PIDController pidcontrollerstr;
  double midstr;
  double kPstr;
  double kIstr;
  double kDstr;
  double tolerancestr;
  double outputstr;

  public VisionDriveStraightOn() {
    // requires(Robot.gyroSwerveDrive);

    outputstr = 0;
    outputrot = 0;
    
    kProt = 0.01;
		kIrot = 0.00;
    kDrot = 0.00;
    tolerancerot = 0.05;
    // kProt = 0.012;
		// kIrot = 0.00;
    // kDrot = 0.000;
    // tolerancerot = 1.5;
    // kProt = 0.19;
		// kIrot = 0.001;
    // kDrot = 0.00;
    // tolerancerot = 0.1;
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
    
    // kPstr = 0.005;
		// kIstr = 0.0000775;
    // kDstr = 0.00006;
    // tolerancestr = 1.5;
    kPstr = 0.0032;
		kIstr = 0.000005;
    kDstr = 0.0000;
    tolerancestr = 1.8;
		pidcontrollerstr = new PIDController(kPstr, kIstr, kDstr, new PIDSource(){
			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
			}

			@Override
			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kDisplacement;
			}

			@Override
			public double pidGet() {
				return midstr;
			}
		}, new PIDOutput(){
			@Override
			public void pidWrite(double output) {
        outputstr = output;
			}
		});
		pidcontrollerstr.setOutputRange(-1, 1);
    pidcontrollerstr.setAbsoluteTolerance(tolerancestr);
    
    Robot.gyroSwerveDrive.fcd = false;
  }

  @Override
  protected void initialize() {
    Robot.gyroSwerveDrive.gyroDrive(0, 0, 0);
    Robot.gyroSwerveDrive.fcd = false;
    pidcontrollerrot.reset();
    pidcontrollerrot.setSetpoint(1);
    // pidcontrollerrot.setSetpoint(180.0);
    pidcontrollerrot.enable();
    pidcontrollerstr.reset();
    pidcontrollerstr.setSetpoint(0);
    pidcontrollerstr.enable();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(Robot.gyroSwerveDrive.fcd) Robot.gyroSwerveDrive.fcd = false;
    getCamData();
    getElevation();
    getStrafe();
    getRotation();
    getForward();

      // midrot = 0;

    SmartDashboard.putNumber("vision rot input", midrot);
    SmartDashboard.putNumber("vision rot output", pidcontrollerrot.get());
    SmartDashboard.putNumber("vision str input", midstr);
    SmartDashboard.putNumber("Vision str output", pidcontrollerstr.get());
    SmartDashboard.putNumberArray("vision elevation", elevation);

    if(height[0] != -1 && height[1] != -1){
      // Robot.gyroSwerveDrive.gyroDrive(outputstr, fwdCmp, outputrot);
    }
  }

  private void getForward() {
    fwdCmp = fwd;
    if(Math.abs(centerX[0] - centerX[1]) > 110){
      fwdCmp = 0;
    }
  }

  private void getRotation() {
  }

  private void getElevation() {
    for(int i = 0; i < 2; i++){
      elevation[i] = centerY[i] + height[i]/2;
    }
  }

  private void getStrafe() {
    if(centerX[0] != -1 && centerX[1] != -1){
      midstr = (Math.toDegrees(Math.atan(((centerX[0] + centerX[1]) - pixels_height)/2/277.34)));
    }else{
      midstr = 0;
    }
  }

  public void getCamData() {
    String centerXString = SmartDashboard.getString("centerX", "");
    if(centerXString.length() > 0){ centerXString = centerXString.substring(1, centerXString.length()-1); }
    String[] strCenterX = centerXString.split(", ");
    double imax = Math.min(2, strCenterX.length);
    centerX[0] = -1; centerX[1] = -1;
    for(int i = 0; i < imax; i++){
      if(strCenterX[i].length() > 0){
        centerX[i] = Double.parseDouble(strCenterX[i]);
      }
    }

    String centerYString = SmartDashboard.getString("centerY", "");
    if(centerYString.length() > 0){ centerYString = centerYString.substring(1, centerYString.length()-1); }
    String[] strCenterY = centerYString.split(", ");
    double kmax = Math.min(2, strCenterY.length);
    centerY[0] = -1; centerY[1] = -1;
    for(int i = 0; i < kmax; i++){
      if(strCenterY[i].length() > 0){
        centerY[i] = Double.parseDouble(strCenterY[i]);
      }
    }

    String heightString = SmartDashboard.getString("height", "");
    if(heightString.length() > 0){ heightString = heightString.substring(1, heightString.length()-1); }
    String[] strheight = heightString.split(", ");
    double jmax = Math.min(2, strheight.length);
    height[0] = -1; height[1] = -1;
    for(int i = 0; i < jmax; i++){
      if(strheight[i].length() > 0){
        height[i] = Double.parseDouble(strheight[i]);
      }
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return pidcontrollerrot.onTarget() && pidcontrollerstr.onTarget();
    // return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.gyroSwerveDrive.fcd = true;
    pidcontrollerrot.disable();
    pidcontrollerstr.disable();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.gyroSwerveDrive.fcd = true;
    pidcontrollerrot.disable();
    pidcontrollerstr.disable();
		this.end();
  }
}
