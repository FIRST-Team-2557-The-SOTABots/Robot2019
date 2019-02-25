package org.usfirst.frc.team2557.robot.commands.arm;

import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ArmWithAxis extends Command {
  public ArmWithAxis() {
    requires(Robot.arm);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.arm.arm(0);
    RobotMap.dsArmLock.set(Value.kForward);
  }


  @Override
  protected void execute() {

    // if(Robot.m_oi.joystick2.getPOV() > 0){

    // }else{
      if((Robot.m_oi.joystick2.getRawAxis(1) > 0.1 || Robot.m_oi.joystick2.getRawAxis(1) < 0.1)){
        Robot.arm.arm(Robot.m_oi.joystick2.getRawAxis(1));
       }

       SmartDashboard.putString("I'm in the command", "ewhaha");
    // }

    
    // if(Robot.m_oi.joystick1.getPOV() == 315){

    // }

    // if(Robot.m_oi.joystick2.getPOV() == 315){
    //   PIDarm(-6300);
    // }

      // if(Robot.m_oi.joystick2.getPOV() == 315)this.target = -6300;
      // if(Robot.m_oi.joystick2.getPOV() == 270)this.target = -5625;
      // if(Robot.m_oi.joystick2.getPOV() == 225)this.target = -1875;
      // if(Robot.m_oi.joystick2.getPOV() == 180)this.target = 0;
      // if(Robot.m_oi.joystick2.getPOV() == 90) this.target = 5625;
    
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.arm.arm(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.arm.arm(0);
  }
}
