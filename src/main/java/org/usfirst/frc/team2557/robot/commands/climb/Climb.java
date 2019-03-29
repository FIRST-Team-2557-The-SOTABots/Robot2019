package org.usfirst.frc.team2557.robot.commands.climb;

import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Command;

public class Climb extends Command {
	double enc;
	double power;

	public Climb(double enc, double power) {
		requires(Robot.climber);
		this.enc = enc;
		this.power = power;
	}

	protected void initialize() {
		RobotMap.climber.set (0);
		Robot.climber.lock(true); // default lock
		// Robot.climber.lock(false); // unlocked CAREFUL
	}
	
	protected void execute(){
		// System.out.println("button: " + (Robot.m_oi.start.get() || Robot.m_oi.back.get() || Robot.m_oi.dy.get()) + " - " + "power : " + power + " - enc: " + enc);
		if((Robot.m_oi.dstart.get() || Robot.m_oi.dback.get() || Robot.m_oi.dy.get()) && power < 0 && RobotMap.climberEncoderDirection * RobotMap.climber.getSensorCollection().getQuadraturePosition() < enc){
			Robot.climber.lock(false);
			Robot.climber.climb(power);
		}else if((Robot.m_oi.dstart.get() || Robot.m_oi.dback.get() || Robot.m_oi.dy.get()) && power > 0 && RobotMap.climberEncoderDirection * RobotMap.climber.getSensorCollection().getQuadraturePosition() > enc){
			Robot.climber.lock(false);
			Robot.climber.climb(power);
		}else{
			Robot.climber.climb (0);
			Robot.climber.lock(true);
		}
	}

	protected boolean isFinished() {
		if(power < 0 && RobotMap.climber.getSensorCollection().getQuadraturePosition() > enc){
			return true;
		}else if(power > 0 && RobotMap.climber.getSensorCollection().getQuadraturePosition() < enc){
			return true;
		}
		return false;
	}

	protected void end() {
		RobotMap.climber.set (0);
		Robot.climber.lock(true);
	}

	protected void interrupted() {
		RobotMap.climber.set (0);
		Robot.climber.lock(true);
		this.end();
	}
}
