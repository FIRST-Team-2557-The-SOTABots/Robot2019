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
	// double target;

	public void armPositions(){
		SmartDashboard.putString("armPos", "ran");
		if(Robot.m_oi.joystick2.getPOV() == 315){
		  RobotMap.armTarget = -5800;
		}else if(Robot.m_oi.joystick2.getPOV() == 270){
		  RobotMap.armTarget = -4800;
		}else if(Robot.m_oi.joystick2.getPOV() == 180){
		  RobotMap.armTarget = 2250;
		}else if(Robot.m_oi.joystick2.getPOV() == 90){
		  RobotMap.armTarget = 5000;
		}else if(Robot.m_oi.joystick2.getPOV() == 0){
		  RobotMap.armTarget = 0;
		}
	}

	public PIDarm() {
		requires(Robot.arm);
		double kp = 0.00025;
		double ki = 0.000002;
		double kd = 0.00000005;
		//make sure go any direction and see how much wiggle.
		pidcontroller = new PIDController(kp, ki, kd, new PIDSource(){
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
				Robot.arm.arm(-output);

				// double enc = RobotMap.armRight.getSensorCollection().getQuadraturePosition();
				// enc *= Math.PI/10000;
				// double angle = Math.abs(Math.sin(enc));
				// SmartDashboard.putNumber("angleForceCounter", angle);
				// Robot.arm.arm(output * Math.cos(angle));
			}
		});
    	// this.target = target;
		pidcontroller.setOutputRange(-1, 1);
		pidcontroller.setAbsoluteTolerance(150);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	  RobotMap.dsArmLock.set(Value.kForward);
      pidcontroller.reset();
      pidcontroller.setSetpoint(RobotMap.armTarget);
      pidcontroller.enable();
	}
	
	protected void execute(){
		if(RobotMap.dsArmLock.get() == Value.kReverse){
			RobotMap.dsArmLock.set(Value.kForward);
		}
		SmartDashboard.putString("pidarm exec", "ran");
		// if()
		armPositions();
		pidcontroller.setSetpoint(RobotMap.armTarget);
		SmartDashboard.putNumber("arm target", RobotMap.armTarget);

		// SmartDashboard
		// pidcontroller.setP(SmartDashboard.getNumber("ArmP", 0.001));
		// pidcontroller.setI(SmartDashboard.getNumber("ArmI", 0));
		// pidcontroller.setD(SmartDashboard.getNumber("ArmD", 0));
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		SmartDashboard.putBoolean("arm pidarm done", done);
		// SmartDashboard.putBoolean("pidarM is finished", Robot.m_oi.joystick2.getRawAxis(1) >= -RobotMap.JOYSTICK_DEADBAND 
		// && Robot.m_oi.joystick2.getRawAxis(1) <= RobotMap.JOYSTICK_DEADBAND);
		return pidcontroller.onTarget();
		// return false;
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