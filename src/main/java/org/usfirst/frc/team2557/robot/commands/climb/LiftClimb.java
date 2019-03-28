package org.usfirst.frc.team2557.robot.commands.climb;

import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class LiftClimb extends Command {
  PIDController pidcontroller;
  double offset = 0;

	public LiftClimb(double offset) {
		this.offset = offset;
				requires(Robot.lift);
		pidcontroller = new PIDController(0.00002,0.000002,0, new PIDSource(){
			@Override
			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kDisplacement;
			}

			@Override
			public double pidGet() {
				return RobotMap.lift2.getSensorCollection().getQuadraturePosition();
			}

      @Override
      public void setPIDSourceType(PIDSourceType pidSource) {

      }
		}, new PIDOutput(){
			@Override
			public void pidWrite(double output) {
        		output *= -1;
				Robot.lift.lift(output);
			}
		});
		pidcontroller.setOutputRange(-1, 1);
		pidcontroller.setAbsoluteTolerance(10000);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		pidcontroller.reset();
		pidcontroller.setSetpoint(0);
	}
	
	protected void execute(){
		if(Robot.m_oi.start.get() || Robot.m_oi.dy.get()){
			pidcontroller.enable();
		}else{
			pidcontroller.disable();
			Robot.lift.lift(0);
		}
		pidcontroller.setSetpoint(-RobotMap.climberEncoderDirection * RobotMap.climber.getSensorCollection().getQuadraturePosition()/825*16000 + offset);
		SmartDashboard.putNumber("lift setpoint", pidcontroller.getSetpoint());
		SmartDashboard.putNumber("lift error", pidcontroller.getError());
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
    	return false;
		// return pidcontroller.onTarget();
	}

	// Called once after isFinished returns true
	protected void end() {
    	Robot.lift.lift(0);
		pidcontroller.disable();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.lift.lift(0);
		pidcontroller.disable();
		this.end();
	}
}
