package org.usfirst.frc.team2557.robot.subsystems;

import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;
import org.usfirst.frc.team2557.robot.commands.climb.Climb;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Climber extends Subsystem {
  @Override
  public void initDefaultCommand() {
    // setDefaultCommand(new Climb(0));
  }

  public void lock(boolean yes){
    if(yes){
      RobotMap.dsClimbLock.set(Value.kForward);
    }else{
      RobotMap.dsClimbLock.set(Value.kReverse);
    }
  }

  public void climb(double power){
    // if(Robot.m_oi.start.get() || Robot.m_oi.back.get()){
    //   if(RobotMap.dsClimbLock.get() == Value.kForward){
    //     RobotMap.dsClimbLock.set(Value.kReverse);
    //   }
    // }else{
    //   if(RobotMap.dsClimbLock.get() == Value.kReverse){
    //     RobotMap.dsClimbLock.set(Value.kForward);
    //   }
    // }

    // if(Robot.m_oi.start.get() && RobotMap.climber.getSensorCollection().getQuadraturePosition() > -16250){
    //   RobotMap.climber.set (.75);
    //   RobotMap.dsClimbLock.set(Value.kReverse);
    // // }else if(Robot.m_oi.back.get() && RobotMap.climber.getSensorCollection().getQuadraturePosition() < -1000){
    // //   RobotMap.climber.set (-0.75);
    // }else{
    //   RobotMap.climber.set (0);
    //   RobotMap.dsClimbLock.set(Value.kForward);
    // }

    RobotMap.climber.set (power);
      // RobotMap.dsClimbLock.set(Value.kReverse);
  }

  public void cancel() {
    RobotMap.climber.set (0);
  }
}
