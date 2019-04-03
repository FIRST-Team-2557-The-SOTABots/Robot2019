package org.usfirst.frc.team2557.robot.commands.climb;

import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class ClimbByGyro extends Command {
  PIDController pidcontroller;
  double target;

	public ClimbByGyro(double target) {
		this.target = target;
		requires(Robot.lift);
		pidcontroller = new PIDController(0.12,0.0,0, new PIDSource(){
			@Override
			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kDisplacement;
			}

			@Override
			public double pidGet() {
				return RobotMap.gyro.getPitch();
			}

      @Override
      public void setPIDSourceType(PIDSourceType pidSource) {
      }
		}, new PIDOutput(){
			@Override
			public void pidWrite(double output) {
        		// output *= 1;
				Robot.lift.lift(output);
			}
		});
		pidcontroller.setOutputRange(-1, 1);
		pidcontroller.setAbsoluteTolerance(1);
	}

	protected void initialize() {
		pidcontroller.reset();
		pidcontroller.setSetpoint(2);
	}
	
	protected void execute(){
		if(Robot.m_oi.dstart.get() || Robot.m_oi.joystick1.getPOV() == 0){
			pidcontroller.enable();
		}else{
			pidcontroller.disable();
			Robot.lift.lift(0);
		}
		SmartDashboard.putNumber("lift setpoint", pidcontroller.getSetpoint());
		SmartDashboard.putNumber("lift error", pidcontroller.getError());
		SmartDashboard.putNumber("lift power climb", pidcontroller.get());
		System.out.println("lift climbing");
	}

	protected boolean isFinished() {
    	// return false;
		// return pidcontroller.onTarget();
		if(RobotMap.climber.getSensorCollection().getQuadraturePosition() > target) return true;
		return false;
	}

	protected void end() {
		System.out.println("lift climbed");
    	Robot.lift.lift(0);
		pidcontroller.disable();
	}

	protected void interrupted() {
		Robot.lift.lift(0);
		pidcontroller.disable();
		this.end();
	}
}