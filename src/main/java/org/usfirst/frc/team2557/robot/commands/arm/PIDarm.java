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

	public void armPositions(){
		// if(Robot.m_oi.joystick2.getPOV() == 315){
		//   RobotMap.armTarget = RobotMap.armBackCargo;
		// }else if(Robot.m_oi.joystick2.getPOV() == 270){
		//   RobotMap.armTarget = RobotMap.armBack;
		// }else if(Robot.m_oi.joystick2.getPOV() == 180){
		//   RobotMap.armTarget = RobotMap.armIntake;
		// }else if(Robot.m_oi.joystick2.getPOV() == 90){
		//   RobotMap.armTarget = RobotMap.armForCargo;
		// }else if(Robot.m_oi.joystick2.getPOV() == 0){
		//   RobotMap.armTarget = 0;
		// }
		if (Robot.m_oi.joystick2.getPOV() == 0) {
			RobotMap.armTarget = RobotMap.armIntake;
		} else if (Robot.m_oi.joystick2.getPOV() == 90) {
			RobotMap.armTarget = RobotMap.armForward;
		}
	}
	public PIDarm() {
		requires(Robot.arm);

		SmartDashboard.putNumber("PArm", RobotMap.multparm);
		SmartDashboard.putNumber("IArm", RobotMap.multiarm);
		SmartDashboard.putNumber("DArm", RobotMap.multdarm);

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
				Robot.arm.arm(-output);

				// double enc = RobotMap.armRight.getSensorCollection().getQuadraturePosition();
				// enc *= Math.PI/10000;
				// double angle = Math.abs(Math.sin(enc));
				// SmartDashboard.putNumber("angleForceCounter", angle);
				// Robot.arm.arm(output * Math.cos(angle));
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
	}
	
	protected void execute(){
		if(RobotMap.dsArmLock.get() == Value.kReverse){
			RobotMap.dsArmLock.set(Value.kForward);
		}
		armPositions();
		pidcontroller.setSetpoint(RobotMap.armTarget);
		SmartDashboard.putNumber("arm target", RobotMap.armTarget * -1);

		pidcontroller.setP(SmartDashboard.getNumber("PArm", RobotMap.multparm) * factor);
		pidcontroller.setI(SmartDashboard.getNumber("IArm", RobotMap.multiarm) * factor);
		pidcontroller.setD(SmartDashboard.getNumber("DArm", RobotMap.multdarm) * factor);

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