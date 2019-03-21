package org.usfirst.frc.team2557.robot.commands.lift;

import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PIDlift extends Command {
	PIDController pidcontroller;
	double target;
	double factor = 0.00001;

	public PIDlift(double target) {
		requires(Robot.lift);
		SmartDashboard.putNumber("P lift", RobotMap.multplift);
		SmartDashboard.putNumber("I lift", RobotMap.multilift);
		SmartDashboard.putNumber("D lift", RobotMap.multdlift);
		pidcontroller = new PIDController(factor* RobotMap.multplift, factor * RobotMap.multilift, factor * RobotMap.multdlift, new PIDSource(){
			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
			}

			@Override
			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kDisplacement;
			}

			@Override
			public double pidGet() {
				return RobotMap.lift2.getSensorCollection().getQuadraturePosition();
			}
		}, new PIDOutput(){
			@Override
			public void pidWrite(double output) {
				double power = -output;
				if(power <= 0){
					power *= 0.9;
				}
				if(RobotMap.lift2.getSensorCollection().getQuadraturePosition() > 0){
					power *= RobotMap.pidliftStall;
				}
				Robot.lift.lift(power);
			}
		});
		this.target = target;
		pidcontroller.setOutputRange(-1, 1);
		pidcontroller.setAbsoluteTolerance(3000);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		pidcontroller.reset();
		pidcontroller.setSetpoint(target);
		pidcontroller.enable();
	}

	public void setSetpoint(double target){
		this.target = target;
	}
	
	protected void execute(){
		pidcontroller.setP(SmartDashboard.getNumber("P lift", RobotMap.multplift) * factor);
		pidcontroller.setI(SmartDashboard.getNumber("I lift", RobotMap.multilift) * factor);
		pidcontroller.setD(SmartDashboard.getNumber("D lift", RobotMap.multdlift) * factor);
		SmartDashboard.putNumber("LiftUpTarget", target);
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
	}
}