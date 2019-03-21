package org.usfirst.frc.team2557.robot.commands.drive;

import org.usfirst.frc.team2557.robot.Robot;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionDriveStraightOn extends Command {
  // double[] height = new double[2];
  double[] centerX = new double[2];
  // double[] distance = new double[2];
  // double[] elevation = new double[2];
  double[] dist = new double[2];
  double angle = 0;

  double pixels_height = 240;
  double pixels_width = 416;
  // double degView = 36.87;
  double fwd = 0.08;
  // double fwd = 0.05;
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
    requires(Robot.gyroSwerveDrive);

    outputstr = 0;
    outputrot = 0;
    
    kProt = 0.0031;
		kIrot = 0.00001;
    kDrot = 0.00;
    tolerancerot = 1.5;
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
    kPstr = 0.004;
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
    // pidcontrollerrot.setSetpoint(1);
    pidcontrollerrot.setSetpoint(0.0);
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
    getStrafe();
    getForward();
    getAngle();

      midrot = angle;

    SmartDashboard.putNumber("vision rot input", midrot);
    SmartDashboard.putNumber("vision rot output", pidcontrollerrot.get());
    SmartDashboard.putNumber("vision str input", midstr);
    SmartDashboard.putNumber("Vision str output", pidcontrollerstr.get());
    SmartDashboard.putNumber("vision angle", angle);

    if(centerX[0] != -1 && centerX[1] != -1){
      Robot.gyroSwerveDrive.gyroDrive(outputstr, fwdCmp, outputrot);
      // Robot.gyroSwerveDrive.gyroDrive(0, 0, outputrot);
    }
  }

  // private void getDistance() {
  //   // for(int i = 0; i < 2; i++){
  //   //   dist[i] = elevation[i]*0.000636796+13.369;
  //   // }
  // }

  private void getAngle() {
    if(centerX[0] > centerX[1]){
      angle = Math.toDegrees(Math.asin((dist[1] - dist[0])/12));
      SmartDashboard.putBoolean("vision angle dir", true);
    }else{
      angle = Math.toDegrees(Math.asin((dist[0] - dist[1])/12));
      SmartDashboard.putBoolean("vision angle dir", false);
    }
  }

  private void getForward() {
    fwdCmp = fwd;
    if(Math.abs(centerX[0] - centerX[1]) > 120){
      fwdCmp = 0;
    }
  }

  // private void getElevation() {
  //   for(int i = 0; i < 2; i++){
  //     elevation[i] = distance[i] + height[i]/2;
  //   }
  // }

  private void getStrafe() {
    if(centerX[0] != -1 && centerX[1] != -1){
      midstr = (Math.toDegrees(Math.atan(((centerX[0] + centerX[1]) - pixels_width)/2/277.34)));
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

    String distanceString = SmartDashboard.getString("distance", "");
    if(distanceString.length() > 0){ distanceString = distanceString.substring(1, distanceString.length()-1); }
    String[] strdistance = distanceString.split(", ");
    double kmax = Math.min(2, strdistance.length);
    dist[0] = -1; dist[1] = -1;
    for(int i = 0; i < kmax; i++){
      if(strdistance[i].length() > 0){
        dist[i] = Double.parseDouble(strdistance[i]);
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
