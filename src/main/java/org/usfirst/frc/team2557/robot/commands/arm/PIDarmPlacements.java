package org.usfirst.frc.team2557.robot.commands.arm;

import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PIDarmPlacements extends Command {
	PIDController pidcontroller;
	double target;

	public PIDarmPlacements() {
    requires(Robot.arm);
    
		//make sure go any direction and see how much wiggle.
			pidcontroller = new PIDController(0.00008, -0.00008, 0.00, new PIDSource(){
			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
			}

			@Override
			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kDisplacement;
			}

			@Override
			public double pidGet() {
				return RobotMap.armRight.getSensorCollection().getQuadraturePosition();
			}
		}, new PIDOutput(){
			@Override
			public void pidWrite(double output) {
				Robot.arm.arm(-output*0.5);
			}
		});

		pidcontroller.setOutputRange(-1, 1);
		pidcontroller.setAbsoluteTolerance(200);
	}

	// Called just before this Command runs the first time
	protected void initialize() {

      pidcontroller.reset();
      pidcontroller.setSetpoint(target);
      pidcontroller.enable();
	}
	
	protected void execute(){
      if(Robot.m_oi.joystick2.getPOV() == 315)this.target = -6300;
      if(Robot.m_oi.joystick2.getPOV() == 270)this.target = -5625;
      if(Robot.m_oi.joystick2.getPOV() == 225)this.target = -1875;
      if(Robot.m_oi.joystick2.getPOV() == 180)this.target = 0;
      if(Robot.m_oi.joystick2.getPOV() == 90) this.target = 5625;
		SmartDashboard.putNumber("LiftCommandAuto encoder position", RobotMap.armRight.getSensorCollection().getQuadraturePosition());
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