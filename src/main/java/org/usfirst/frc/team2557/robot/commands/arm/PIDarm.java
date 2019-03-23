package org.usfirst.frc.team2557.robot.commands.arm;

import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PIDarm extends Command {
	PIDController pidcontroller;
	boolean done = false;
	double factor = 0.000001;
	double multp = 0;
	double multi = 0;
	double multd = 0;
	boolean didntEnd;

	public void armPositions(){
		if (Robot.m_oi.joystick2.getPOV() == 0) {
			RobotMap.armTarget = RobotMap.armForCargo;
			multp = RobotMap.multparmHigh;
			multi = RobotMap.multiarmHigh;
			multd = RobotMap.multdarmHigh;
		} else if (Robot.m_oi.() == 90) {
			RobotMap.armTarget = RobotMap.armIntake;
			multp = RobotMap.multparm;
			multi = RobotMap.multiarm;
			multd = RobotMap.multdarm;
		}else if(Robot.m_oi.joystick2.getPOV() == 270){
			RobotMap.armTarget = RobotMap.armClimb;
			multp = RobotMap.multparm;
			multi = RobotMap.multiarm;
			multd = RobotMap.multdarm;
		}
	}
	public PIDarm() {
		requires(Robot.arm);
		didntEnd = false;

		// SmartDashboard.putNumber("PArm", RobotMap.multparm);
		// SmartDashboard.putNumber("IArm", RobotMap.multiarm);
		// SmartDashboard.putNumber("DArm", RobotMap.multdarm);

		// double kp = 25;
		// double ki = 0.02;
		// double kd = 0.0005;
		//make sure go any direction and see how much wiggle.
		pidcontroller = new PIDController(RobotMap.multparm * factor, RobotMap.multiarm * factor, RobotMap.multdarm * factor, new PIDSource(){
			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
			}

			@Override
			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kDisplacement;
			}

			@Override
			public double pidGet() {
		
				return -RobotMap.armRight.getSensorCollection().getQuadraturePosition();
			}
		}, new PIDOutput(){
			@Override
			public void pidWrite(double output) {
				double enc = RobotMap.armRight.getSensorCollection().getQuadraturePosition()*Math.PI/10000;
				Robot.arm.arm(-output + RobotMap.pidarmStall*Math.sin(enc));
			}
		});
		pidcontroller.setOutputRange(-1, 1);
		pidcontroller.setAbsoluteTolerance(60);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	  RobotMap.dsArmLock.set(Value.kForward);
      pidcontroller.reset();
      pidcontroller.setSetpoint(RobotMap.armTarget);
	  pidcontroller.enable();
	  didntEnd = false;
	}
	
	protected void execute(){
		if(Robot.m_oi.joystick2.getPOV() == 180){
			didntEnd = true;
		}

		if(RobotMap.dsArmLock.get() != Value.kForward && !didntEnd){
			RobotMap.dsArmLock.set(Value.kForward);
		}else if(didntEnd){
			pidcontroller.disable();
			if(RobotMap.dsArmLock.get() != Value.kReverse){
				RobotMap.dsArmLock.set(Value.kReverse);
			}
		}

		armPositions();
		pidcontroller.setSetpoint(RobotMap.armTarget);
		SmartDashboard.putNumber("arm target", RobotMap.armTarget * -1);

		pidcontroller.setP(multp* factor);
		pidcontroller.setI( multi* factor);
		pidcontroller.setD( multd * factor);

		// pidcontroller.setP(SmartDashboard.getNumber("ArmP", 0.001));
		// pidcontroller.setI(SmartDashboard.getNumber("ArmI", 0));
		// pidcontroller.setD(SmartDashboard.getNumber("ArmD", 0));
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return pidcontroller.onTarget();
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.arm.arm(0);
		RobotMap.dsArmLock.set(Value.kReverse);
		pidcontroller.disable();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		pidcontroller.disable();
		this.end();
	}
}