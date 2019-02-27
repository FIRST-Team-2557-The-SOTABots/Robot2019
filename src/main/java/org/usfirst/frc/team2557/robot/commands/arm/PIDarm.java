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
	// double target;

	public void armPositions(){
		SmartDashboard.putString("armPos", "ran");
		if(Robot.m_oi.joystick2.getPOV() == 315){
		  RobotMap.armTarget = -5450;
		}else if(Robot.m_oi.joystick2.getPOV() == 270){
		  RobotMap.armTarget = -4500;
		}else if(Robot.m_oi.joystick2.getPOV() == 180){
		  RobotMap.armTarget = 2340;
		}else if(Robot.m_oi.joystick2.getPOV() == 90){
		  RobotMap.armTarget = 5500;
		}
	  }

	public PIDarm() {
		requires(Robot.arm);
    
		//make sure go any direction and see how much wiggle.
		//pidcontroller = new PIDController(1.00, 0.10, 0.9, new PIDSource(){ //this works for sure. Practice bot
		pidcontroller = new PIDController(1.00, 0.10, 0.9, new PIDSource(){
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
				Robot.arm.arm(-output*0.7);
			}
		});
    	// this.target = target;
		pidcontroller.setOutputRange(-1, 1);
		pidcontroller.setAbsoluteTolerance(250);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	  RobotMap.dsArmLock.set(Value.kForward);
      pidcontroller.reset();
      pidcontroller.setSetpoint(RobotMap.armTarget);
      pidcontroller.enable();
	}
	
	protected void execute(){
		SmartDashboard.putString("pidarm exec", "ran");
		// if()
		armPositions();
		pidcontroller.setSetpoint(RobotMap.armTarget);
		// SmartDashboard.putNumber("Arm encoder position", RobotMap.armRight.getSensorCollection().getQuadraturePosition());
		SmartDashboard.putNumber("arm target", RobotMap.armTarget);
		// SmartDashboard.putNumber("arm output", output);
	}

	boolean done = false;

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