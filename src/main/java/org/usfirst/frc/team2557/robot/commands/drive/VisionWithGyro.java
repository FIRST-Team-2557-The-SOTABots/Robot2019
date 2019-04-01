package org.usfirst.frc.team2557.robot.commands.drive;

import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionWithGyro extends Command {
  double angleTarget = 0;

  double pixels_height = 240;
  double pixels_width = 320;
  double fwd = 0.165;
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

  public VisionWithGyro() {
    requires(Robot.gyroSwerveDrive);

    table = NetworkTableInstance.getDefault().getTable("limelight");

    outputs = 0;
    outputr = 0;
    valid = 0;
    
    kProt = 0.00235;
		kIrot = 0.00000;
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
				return (RobotMap.gyro.getAngle()+360)%360.0;
			}
		}, new PIDOutput(){
			@Override
			public void pidWrite(double output) {
          outputr = -1*output;
			}
		});
		pidcontrollerrot.setOutputRange(-1, 1);
    pidcontrollerrot.setAbsoluteTolerance(tolerance);
    pidcontrollerrot.setInputRange(0, 360);
    pidcontrollerrot.setContinuous();
    

    kPstr = 0.007105;
		kIstr = 0.0000;
    kDstr = 0.00001;
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
    // Robot.gyroSwerveDrive.gyroDrive(0, 0, 0);
    pidcontrollerrot.reset();
    // pidcontrollerrot.setSetpoint(0);
    pidcontrollerrot.enable();
    pidcontrollerstr.reset();
    pidcontrollerstr.setSetpoint(0);
    pidcontrollerstr.enable();
  }

  @Override
  protected void execute() {
    if(Robot.m_oi.joystick1.getRawAxis(3) > 0.5 || Robot.m_oi.joystick1.getRawAxis(2) > 0.5){
      getCamData();
      getForward();
      SmartDashboard.putNumber("vision rot output", pidcontrollerrot.get());
      SmartDashboard.putNumber("Vision str output", pidcontrollerstr.get());
      if(valid == 1 && (Robot.m_oi.joystick1.getRawAxis(3) > 0.5 || Robot.m_oi.joystick1.getRawAxis(2) > 0.5)) Robot.gyroSwerveDrive.drive(outputs, fwdCmp, outputr); //Robot.gyroSwerveDrive.drive(outputs*-1*(-1*Math.abs(pidcontrollerrot.getError() + 0.1)/0.1), fwdCmp, outputr);
      else Robot.gyroSwerveDrive.drive(0, 0, outputr);
    }
    if(Robot.m_oi.da.get()){
      angleTarget = 28;
    }else if(Robot.m_oi.db.get()){
      angleTarget = 152;
    }else if(Robot.m_oi.dx.get()){
      angleTarget = 332;
    }else if(Robot.m_oi.dy.get()){
      angleTarget = 208;
    }else if(Robot.m_oi.joystick1.getRawAxis(2) > 0.5){
      angleTarget = 180;
    }
    pidcontrollerrot.setSetpoint(angleTarget);
    SmartDashboard.putNumber("angleTarget vision", angleTarget);
    SmartDashboard.putNumber("angle setpoint vision", pidcontrollerrot.getSetpoint());
    SmartDashboard.putNumber("angle error vision", pidcontrollerrot.getError());
    SmartDashboard.putNumber("outputr vision", outputr);
    SmartDashboard.putNumber("strafe errorrrr", pidcontrollerstr.getError());
  }

  private void getForward() {
    if ((valid == 1 && (Robot.m_oi.joystick1.getRawAxis(3) > 0.5 || Robot.m_oi.joystick1.getRawAxis(2) > 0.5))) fwdCmp = (((a0+a1)/-2.0)/3.5 + 1) * fwd; //*(1/Math.abs(pidcontrollerstr.getError()*10.0 + 1))/2.0; //(((a0+a1)/-2.0)/2 + 1) * fwd;
    else if (Robot.m_oi.joystick1.getRawAxis(3) > 0.5 || Robot.m_oi.joystick1.getRawAxis(2) > 0.5) fwdCmp = fwd*.25;
    // else fwdCmp = fwd*.25;
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
  }

  @Override
  protected boolean isFinished() {
    return pidcontrollerrot.onTarget() && pidcontrollerstr.onTarget();
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
