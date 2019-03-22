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
		Robot.climber.lock(true); // default lock
		// Robot.climber.lock(false); // unlocked CAREFUL
	}
	
	protected void execute(){
		// Robot.climber.climb(power);
		System.out.println("power : " + power + " - enc: " + enc);
		if((Robot.m_oi.start.get() || Robot.m_oi.back.get()) && power < 0 && RobotMap.climber.getSensorCollection().getQuadraturePosition() < enc){
			Robot.climber.lock(false);
			Robot.climber.climb(power);
		}else if((Robot.m_oi.start.get() || Robot.m_oi.back.get()) && power > 0 && RobotMap.climber.getSensorCollection().getQuadraturePosition() > enc){
			Robot.climber.lock(false);
			Robot.climber.climb(power);
		}else{
			Robot.climber.climb (0);
			Robot.climber.lock(true);
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
