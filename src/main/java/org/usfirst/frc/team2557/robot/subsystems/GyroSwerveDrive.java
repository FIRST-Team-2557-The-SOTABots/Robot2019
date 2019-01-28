package org.usfirst.frc.team2557.robot.subsystems;

import org.usfirst.frc.team2557.robot.RobotMap;
import org.usfirst.frc.team2557.robot.commands.GyroSwerveDriveCommand;
import edu.wpi.first.wpilibj.command.Subsystem;

public class GyroSwerveDrive extends Subsystem {
  //for turn around when it's faster
  // SwerveModule[] swerveModules = new SwerveModule[4];
  // double[] wheelAngle = new double[4];
  // double[] wheelSpeed = new double[4];
  // double[] oldAngle = {0,0,0,0};
  // double[] joystickDiff = new double[4];
  // double[] angleError = new double[4];

  public void gyroDrive (double str, double fwd, double rot) {
    double baseAngle = RobotMap.gyro.getAngle() % 360;
    baseAngle = Math.toRadians(baseAngle);
    baseAngle *= -1;

    double intermediary = fwd * Math.cos(baseAngle) + str * Math.sin(baseAngle);
    str = -fwd * Math.sin(baseAngle) + str * Math.cos(baseAngle);
    fwd = intermediary;

    double a = str - rot * (RobotMap.SWERVE_LENGTH / RobotMap.SWERVE_RADIUS);
		double b = str + rot * (RobotMap.SWERVE_LENGTH / RobotMap.SWERVE_RADIUS);
		double c = fwd - rot * (RobotMap.SWERVE_WIDTH / RobotMap.SWERVE_RADIUS);
    double d = fwd + rot * (RobotMap.SWERVE_WIDTH / RobotMap.SWERVE_RADIUS);
    
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

    //for turn around when it's faster

    // double[] wheelSpeed = {speedBR, speedBL, speedFR, speedFL};
    // double[] wheelAngle = {angleBR, angleBL, angleFR, angleFL};

    // swerveModules[0] = RobotMap.swerveModFR;
    // swerveModules[1] = RobotMap.swerveModBR;
    // swerveModules[2] = RobotMap.swerveModBL;
    // swerveModules[3] = RobotMap.swerveModFL;

    // double degs[] = new double[4];
    // for(int i=0;i<4;i++){
    //   degs[i] = swerveModules[i].encCount*2*Math.PI/RobotMap.SWERVE_ENC_CIRC;
    //   joystickDiff[i] = wheelAngle[i] - oldAngle[i];
    //   angleError[i] = wheelAngle[i] - degs[i];

    // if(Math.abs(joysickDiff[i]) == Math.PI + Math.PI/36 || Math.abs(joysickDiff[i]) == Math.PI - Math.PI/36){ //I think this 
    // makes it so that if it's less than a 5 degree change then it doesn normal drive
    //   if(Math.abs(joystickDiff[i]) > Math.PI/4){ //new angle is greater than a 90degree turn, so find shortest path
    //     //reverse speed motors 
    //     swerveModules[i].drive(wheelSpeed[i]/**4800*4096/600*/, wheelAngle[i]);
        
    //     //find new angle
    //     wheelAngle[i] -= Math.PI; //subtract 180 degrees
    //     if(wheelAngle[i] < 0){ //wrap to new angle between 0-360
    //       wheelAngle[i] += 2*Math.PI;
    //     }
    //     //now the angle is set to move to the shortest path, which is just 180 degrees 
    //     //from the current heading
        
    //   }else{
    //     swerveModules[i].drive(-1*wheelSpeed[i]/**4800*4096/600*/, wheelAngle[i]);
    //   } 

    //   if(wheelSpeed[i]>0.1){
    //     // pidTurnController[i].setSetpoint(wheelAngles[i]);
    //     oldAngle[i] = wheelAngle[i];
    //   }
    // }
    //}else{
    //RobotMap.swerveModBR.drive (speedBR, angleBR);
    //RobotMap.swerveModBL.drive (speedBL, angleBL);
    //RobotMap.swerveModFR.drive (speedFR, angleFR);
    //RobotMap.swerveModFL.drive (speedFL, angleFL); 
    // }
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