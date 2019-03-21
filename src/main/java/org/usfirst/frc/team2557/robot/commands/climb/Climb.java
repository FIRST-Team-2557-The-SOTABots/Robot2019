package org.usfirst.frc.team2557.robot.commands.climb;

import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

public class Climb extends Command {
	double enc;
	double power;

	public Climb(double enc, double power) {
		requires(Robot.climber);
		this.enc = enc;
		this.power = power;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		RobotMap.climber.set (0);
		// System.out.println("intittted");
		// RobotMap.dsClimbLock.set(Value.kForward); // default lock
		// RobotMap.dsClimbLock.set(Value.kReverse); // unlocked CAREFUL
	}
	
	protected void execute(){
	// Robot.climber.climbAuto();
		System.out.println(power + " - " + (Robot.m_oi.start.get() || Robot.m_oi.start.get()) + " - " + (boolean) (RobotMap.climber.getSensorCollection().getQuadraturePosition() < enc));
		if(power < 0 && (Robot.m_oi.start.get() || Robot.m_oi.back.get()) && RobotMap.climber.getSensorCollection().getQuadraturePosition() < enc){
			Robot.climber.lock(false);
			Robot.climber.climb(power);
			// System.out.println("NEGATIVE POWER");
		}else if(power > 0 && (Robot.m_oi.start.get() || Robot.m_oi.back.get()) && RobotMap.climber.getSensorCollection().getQuadraturePosition() > enc){
			Robot.climber.lock(false);
			  Robot.climber.climb(power);
			//   System.out.println("PSITIVE POWERR");
		}else{
			Robot.climber.climb (0);
			Robot.climber.lock(true);
			// System.out.println("NOOOOO POWER");
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if(power < 0 && RobotMap.climber.getSensorCollection().getQuadraturePosition() > enc){
			return true;
		}else if(power > 0 && RobotMap.climber.getSensorCollection().getQuadraturePosition() < enc){
			return true;
		}
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		RobotMap.climber.set (0);
		Robot.climber.lock(true);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		RobotMap.climber.set (0);
		Robot.climber.lock(true);
		this.end();
	}
}
