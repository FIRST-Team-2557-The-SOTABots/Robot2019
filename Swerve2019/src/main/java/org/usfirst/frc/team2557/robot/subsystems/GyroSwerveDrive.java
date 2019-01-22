package org.usfirst.frc.team2557.robot.subsystems;

import org.usfirst.frc.team2557.robot.RobotMap;
import org.usfirst.frc.team2557.robot.commands.GyroSwerveDriveCommand;
import edu.wpi.first.wpilibj.command.Subsystem;

public class GyroSwerveDrive extends Subsystem {
  
  public void gyroDrive (double str, double fwd, double rot) {
    double angle = RobotMap.gyro.getAngle();
    double intermediary = fwd * Math.cos(angle) + str * Math.sin(angle);
    str = - fwd * Math.sin(angle) + str * Math.cos(angle);
    fwd = intermediary;

    double a = str - rot * (RobotMap.L / RobotMap.R);
		double b = str + rot * (RobotMap.L / RobotMap.R);
		double c = fwd - rot * (RobotMap.W / RobotMap.R);
    double d = fwd + rot * (RobotMap.W / RobotMap.R);
    
    double speedBR = Math.sqrt ((a * a) + (d * d));
    double speedBL = Math.sqrt ((a * a) + (c * c));
    double speedFR = Math.sqrt ((b * b) + (d * d));
    double speedFL = Math.sqrt ((b * b) + (c * c));
    
    double angleBR = Math.atan2 (a, d) / Math.PI;
    double angleBL = Math.atan2 (a, c) / Math.PI;
    double angleFR = Math.atan2 (b, d) / Math.PI;
    double angleFL = Math.atan2 (b, c) / Math.PI;
    
    double max = speedBR;
    if (speedBL > max) { speedBL = max; } 
    if (speedFR > max) { speedFR = max; } 
    if (speedFL > max) { speedFL = max; }
    if (max > 1) { speedBL /= max; speedFR /= max; speedFL /= max; }
    
    RobotMap.swerveModBR.drive (speedBR, angleBR);
    RobotMap.swerveModBL.drive (speedBL, angleBL);
    RobotMap.swerveModFR.drive (speedFR, angleFR);
    RobotMap.swerveModFL.drive (speedFL, angleFL);
  }
  
  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new GyroSwerveDriveCommand());
  }
}