package org.usfirst.frc.team2557.robot.commands.lift;

import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PIDdown extends Command {
	PIDController pidcontroller;
	double target;

	public PIDdown(double target) {
		requires(Robot.lift);
		
		pidcontroller = new PIDController(0.001, 0.00005, 0.001, new PIDSource(){
			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
			}

			@Override
			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kDisplacement;
			}

			@Override
			public double pidGet() {
				return -RobotMap.lift1.getSensorCollection().getQuadraturePosition();
			}
		}, new PIDOutput(){
			@Override
			public void pidWrite(double output) {
				Robot.lift.lift(output*0.5);
			}
		});
		this.target = target;
		pidcontroller.setOutputRange(-1, 1);
		pidcontroller.setAbsoluteTolerance(500);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		pidcontroller.reset();
		pidcontroller.setSetpoint(target);
		pidcontroller.enable();
	}
	
	protected void execute(){
		SmartDashboard.putNumber("LiftCommandAuto encoder position", -RobotMap.lift1.getSensorCollection().getQuadraturePosition());
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return pidcontroller.onTarget();
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.lift.lift(0);
		pidcontroller.disable();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		pidcontroller.disable();
		this.end();
//		this.
	}
}