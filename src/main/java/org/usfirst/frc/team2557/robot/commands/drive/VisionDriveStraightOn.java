package org.usfirst.frc.team2557.robot.commands.drive;

import org.usfirst.frc.team2557.robot.Robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionDriveStraightOn extends Command {
  // double[] centerX = new double[2];
  // double[] dist = new double[2];

  double angle = 0;

  double pixels_height = 240;
  double pixels_width = 320;
  double fwd = 0.15;
  double fwdCmp = 0;

  PIDController pidcontrollerrot;
  double kProt;
  double kIrot;
  double kDrot;
  double tolerance;
  double outputr;

  PIDController pidcontrollerstr;
  double kPstr;
  double kIstr;
  double kDstr;
  double tolerancestr;
  double outputs;

  double x;
  double a0;
  double a1;
  double valid;
  NetworkTable table;
  NetworkTableEntry ta0;
  NetworkTableEntry ta1;
  NetworkTableEntry tx;
  NetworkTableEntry tv;

  public VisionDriveStraightOn() {
    requires(Robot.gyroSwerveDrive);

    table = NetworkTableInstance.getDefault().getTable("limelight");

    outputs = 0;
    outputr = 0;
    valid = 0;
    
    kProt = 0.1525;
		kIrot = 0.00003;
    kDrot = 0.00;
    tolerance = 0.01;
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
				return angle;
			}
		}, new PIDOutput(){
			@Override
			public void pidWrite(double output) {
          outputr = output;
			}
		});
		pidcontrollerrot.setOutputRange(-1, 1);
		pidcontrollerrot.setAbsoluteTolerance(tolerance);
    
    kPstr = 0.010;
		kIstr = 0.000005;
    kDstr = 0.0000;
    tolerancestr = 0.01;
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
				return x;
			}
		}, new PIDOutput(){
			@Override
			public void pidWrite(double output) {
        outputs = output;
			}
		});
		pidcontrollerstr.setOutputRange(-1, 1);
    pidcontrollerstr.setAbsoluteTolerance(tolerancestr);
  }

  @Override
  protected void initialize() {
    Robot.gyroSwerveDrive.gyroDrive(0, 0, 0);
    pidcontrollerrot.reset();
    pidcontrollerrot.setSetpoint(0);
    pidcontrollerrot.enable();
    pidcontrollerstr.reset();
    pidcontrollerstr.setSetpoint(0);
    pidcontrollerstr.enable();
  }

  @Override
  protected void execute() {
    getCamData();
    getForward();
    getAngle();

    SmartDashboard.putNumber("vision rot output", pidcontrollerrot.get());
    SmartDashboard.putNumber("ANGLES", angle);
    SmartDashboard.putNumber("Vision str output", pidcontrollerstr.get());
    // SmartDashboard.putNumber("Vision str output", pidcontrollerstr.get());

    if(valid == 1) Robot.gyroSwerveDrive.drive(outputs, fwdCmp, outputr);
    else Robot.gyroSwerveDrive.drive(0, fwdCmp, 0);
  }

  private void getAngle() {
    if(a0 > a1){
      angle = -1* a1/a0 + 1;
    }else{
      angle = 1* a0/a1 - 1;
    }
  }

  private void getForward() {
    if(Robot.m_oi.joystick1.getRawAxis(2) > 0.5 && a0 < 9 && a1 < 9) fwdCmp = fwd;
    else fwdCmp = 0;
  }

  public void getCamData() {
	  tx = table.getEntry("tx");
    ta0 = table.getEntry("ta0");
    ta1 = table.getEntry("ta1");
    tv = table.getEntry("tv");

		//read values periodically
		x = tx.getDouble(0.0);
    a0 = ta0.getDouble(0.0);
    a1 = ta1.getDouble(0.0);
    valid = tv.getDouble(0.0);
    
    // String centerXString = SmartDashboard.getString("centerX", "");
    // if(centerXString.length() > 0){ centerXString = centerXString.substring(1, centerXString.length()-1); }
    // String[] strCenterX = centerXString.split(", ");
    // double imax = Math.min(2, strCenterX.length);
    // centerX[0] = -1; centerX[1] = -1;
    // for(int i = 0; i < imax; i++){
    //   if(strCenterX[i].length() > 0){
    //     centerX[i] = Double.parseDouble(strCenterX[i]);
    //   }
    // }

    // String distanceString = SmartDashboard.getString("distance", "");
    // if(distanceString.length() > 0){ distanceString = distanceString.substring(1, distanceString.length()-1); }
    // String[] strdistance = distanceString.split(", ");
    // double kmax = Math.min(2, strdistance.length);
    // dist[0] = -1; dist[1] = -1;
    // for(int i = 0; i < kmax; i++){
    //   if(strdistance[i].length() > 0){
    //     dist[i] = Double.parseDouble(strdistance[i]);
    //   }
    // }
  }

  @Override
  protected boolean isFinished() {
    return pidcontrollerrot.onTarget() && pidcontrollerstr.onTarget();
    // return false;
  }

  @Override
  protected void end() {
    pidcontrollerrot.disable();
    pidcontrollerstr.disable();
  }

  @Override
  protected void interrupted() {
    pidcontrollerrot.disable();
    pidcontrollerstr.disable();
		this.end();
  }
}
