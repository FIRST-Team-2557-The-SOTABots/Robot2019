package org.usfirst.frc.team2557.robot.commands.climb;

import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Command;

public class Climb extends Command {
	double enc;
	double power;

	public Climb(int enc, double power) {
		if(enc == 3)this.enc = RobotMap.climbHigh;
		else if(enc == 2)this.enc = RobotMap.climbLow;
		else this.enc= RobotMap.climbRetract;

		requires(Robot.climber);
		this.power = power;
	}

	protected void initialize() {
		RobotMap.climber.set (0);
		Robot.climber.lock(true); // default lock
		// Robot.climber.lock(false); // unlocked CAREFUL
	}
	
	protected void execute(){
		if((Robot.m_oi.dstart.get() || Robot.m_oi.dback.get() || Robot.m_oi.joystick1.getPOV() == 0) && 
				(power < 0 && RobotMap.climberEncoderDirection * RobotMap.climber.getSensorCollection().getQuadraturePosition() < enc) 
						|| (power > 0 && RobotMap.climberEncoderDirection * RobotMap.climber.getSensorCollection().getQuadraturePosition() > enc) ){
			Robot.climber.lock(false);
			if ( power < 0 || RobotMap.climberEncoderDirection * RobotMap.climber.getSensorCollection().getQuadraturePosition() < -5000) Robot.climber.climb(power);
			else if (power > 0) Robot.climber.climb(power*RobotMap.climberEncoderDirection * RobotMap.climber.getSensorCollection().getQuadraturePosition()/5000);
		}else{
			Robot.climber.climb (0);
			Robot.climber.lock(true);
		}
		System.out.println("climber climbing");
	}

	protected boolean isFinished() {
		if(power < 0 && RobotMap.climberEncoderDirection * RobotMap.climber.getSensorCollection().getQuadraturePosition() > enc){
			return true;
		}else if(power > 0 && RobotMap.climberEncoderDirection * RobotMap.climber.getSensorCollection().getQuadraturePosition() < enc){
			return true;
		}
		return false;
	}

	protected void end() {
		System.out.println("climber climbed");
		RobotMap.climber.set (0);
		Robot.climber.lock(true);
	}

	protected void interrupted() {
		RobotMap.climber.set (0);
		Robot.climber.lock(true);
		this.end();
	}
}
