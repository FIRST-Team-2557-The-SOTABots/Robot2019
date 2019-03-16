package org.usfirst.frc.team2557.robot.commands.intake;

import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IntakeTrigger extends Command {
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
    SmartDashboard.putBoolean("lastGaamepiecewasdisc", RobotMap.lastGamepieceWasDisc);
    double mtrr = Robot.m_oi.joystick2.getRawAxis(3);
    double mtrl = Robot.m_oi.joystick2.getRawAxis(2);
    // double dtrr = Robot.m_oi.joystick1.getRawAxis(3);
    // double dtrl = Robot.m_oi.joystick1.getRawAxis(2);

    double mtr = Math.max(mtrr, mtrl);
    // double dtr = Math.max(dtrr, dtrl);

    if(mtr > 0.2){
      if(mtrr > 0.2){
        Robot.intake.speed(mtrr);
        RobotMap.dsIntake.set(Value.kReverse);
      }else if(mtrl > 0.2){
        Robot.intake.speed(-mtrl);
        if(RobotMap.cargo.get()){
          RobotMap.lastGamepieceWasDisc = false;
        }else if(RobotMap.disc.get()){
          RobotMap.lastGamepieceWasDisc = true;
        }
        if(RobotMap.lastGamepieceWasDisc){
          RobotMap.dsIntake.set(Value.kForward);
        }
      }
    // }else if(dtr > 0.2){
    //   if(dtrr > 0.2){
    //     Robot.intake.speed(dtrr);
    //     RobotMap.dsIntake.set(Value.kReverse);
    //   }else if(dtrl > 0.2){
    //     Robot.intake.speed(-dtrl);
    //     if(RobotMap.cargo.get()){
    //       RobotMap.lastGamepieceWasDisc = false;
    //     }else if(RobotMap.disc.get()){
    //       RobotMap.lastGamepieceWasDisc = true;
    //     }
    //     if(RobotMap.lastGamepieceWasDisc){
    //       RobotMap.dsIntake.set(Value.kForward);
    //     }
    //   }
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
