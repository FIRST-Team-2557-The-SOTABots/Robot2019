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
				// double power = 0;
				if(power <= 0){
					power *= 0.9;
				}
				if(RobotMap.lift2.getSensorCollection().getQuadraturePosition() > 0){
					power += RobotMap.pidliftStall;
				}
				Robot.lift.lift(power);
			}
		});
		this.target = target;
		pidcontroller.setOutputRange(-1, 1);
		pidcontroller.setAbsoluteTolerance(3000);

		SmartDashboard.putNumber("Plift", RobotMap.multplift);
		SmartDashboard.putNumber("Ilift", RobotMap.multilift);
		SmartDashboard.putNumber("Dlift", RobotMap.multdlift);
	}

	protected void initialize() {
		pidcontroller.reset();
		pidcontroller.setSetpoint(target);
		pidcontroller.enable();
	}

	public void setSetpoint(double target){
		this.target = target;
	}
	
	protected void execute(){
		// double p = SmartDashboard.getNumber("Plift", RobotMap.multplift);
		// double i = SmartDashboard.getNumber("Ilift", RobotMap.multilift);
		// double d = SmartDashboard.getNumber("Dlift", RobotMap.multdlift);
		pidcontroller.setP(SmartDashboard.getNumber("Plift", RobotMap.multplift) * factor);
		pidcontroller.setI(SmartDashboard.getNumber("Ilift", RobotMap.multilift) * factor);
		pidcontroller.setD(SmartDashboard.getNumber("Dlift", RobotMap.multdlift) * factor);
		SmartDashboard.putNumber("LiftUpTarget", target);
	}

	protected boolean isFinished() {
		return pidcontroller.onTarget();
	}

	protected void end() {
		Robot.lift.lift(0);
		pidcontroller.disable();
	}

	protected void interrupted() {
		pidcontroller.disable();
		this.end();
	}
}