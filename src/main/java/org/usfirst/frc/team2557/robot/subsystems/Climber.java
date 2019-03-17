package org.usfirst.frc.team2557.robot.subsystems;

import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;
import org.usfirst.frc.team2557.robot.commands.climb.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climber extends Subsystem {

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new Climb());
  }

  public void climb(){
    
    if(Robot.m_oi.start.get() && RobotMap.climber.getSensorCollection().getQuadraturePosition() > -16250){
      RobotMap.climber.set (.75); //down
    }else if(Robot.m_oi.back.get() && RobotMap.climber.getSensorCollection().getQuadraturePosition() < 0){
      RobotMap.climber.set (-.75); //up
    }else{
      RobotMap.climber.set (0);
    }
  }

  public void cancel() {
  }
}
