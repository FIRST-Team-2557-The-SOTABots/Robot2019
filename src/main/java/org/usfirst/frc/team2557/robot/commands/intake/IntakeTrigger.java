package org.usfirst.frc.team2557.robot.commands.intake;

import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

public class IntakeTrigger extends Command {

  boolean letGo = false;

  public IntakeTrigger() {
    requires(Robot.intake);
  }

  @Override
  protected void initialize() {
    Robot.intake.speed(0);
  }

 //forward shoots in out. Reverse retracts
  @Override
  protected void execute() {
    double mtrr = Robot.m_oi.joystick2.getRawAxis(3);
    double mtrl = Robot.m_oi.joystick2.getRawAxis(2);
    // double dtrr = Robot.m_oi.joystick1.getRawAxis(3);
    // double dtrl = Robot.m_oi.joystick1.getRawAxis(2);

    double mtr = Math.max(mtrr, mtrl);
    // double dtr = Math.max(dtrr, dtrl);

    // if(Robot.m_oi.mbumperLeft.get()){
    //   if(RobotMap.TIMEOFFLIGHT1 < 5 && RobotMap.TIMEOFFLIGHT2 < 5){
    //     RobotMap.dsIntake.set(Value.kForward);
    //     letGo = true;
    //   }
    // }

    // if(letGo){
    //   RobotMap.dsIntake.set(Value.kForward);
    //   letGo = false;
    // }


    if(mtr > 0.2){
      if(mtrr > 0.2){
        Robot.intake.speed(mtrr);
        RobotMap.dsIntake.set(Value.kReverse);
      }else if(mtrl > 0.2){
        RobotMap.dsIntake.set(Value.kForward);
        Robot.intake.speed(-mtrl);
      }
    }else if(Robot.m_oi.joystick2.getPOV() == 135){
      Robot.intake.speed(0.9);
    }else if(Robot.m_oi.joystick2.getPOV() == 225){
      Robot.intake.speed(-0.5);
    }else{
      Robot.intake.speed(0);
    }
    
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    Robot.intake.speed(0);
  }

  @Override
  protected void interrupted() {
    Robot.intake.speed(0);
  }
}
