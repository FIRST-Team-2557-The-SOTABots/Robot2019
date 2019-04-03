package org.usfirst.frc.team2557.robot.commands.arm;

import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;

public class UsefulPIDarm extends Command {
	PIDController pidcontroller;
	boolean done = false;
	double factor = 0.000001;
	double multp = 0;
	double multi = 0;
	double multd = 0;
	double target;

	public UsefulPIDarm(double target) {
		requires(Robot.arm);
		this.target = target;

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
		pidcontroller.setAbsoluteTolerance(250);
	}

	protected void initialize() {
      pidcontroller.reset();
      pidcontroller.setSetpoint(target);
      pidcontroller.enable();
	}
	
	protected void execute(){
		pidcontroller.setSetpoint(target);
	}

	protected boolean isFinished() {
		return pidcontroller.onTarget();
	}

	protected void end() {
		Robot.arm.arm(0);
		pidcontroller.disable();
	}

	protected void interrupted() {
		pidcontroller.disable();
		this.end();
	}
}