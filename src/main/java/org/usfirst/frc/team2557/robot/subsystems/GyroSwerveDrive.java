package org.usfirst.frc.team2557.robot.subsystems;

import org.usfirst.frc.team2557.robot.RobotMap;
import org.usfirst.frc.team2557.robot.commands.GyroSwerveDriveCommand;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GyroSwerveDrive extends Subsystem {
  //for turn around when it's faster
  // SwerveModule[] swerveModules = new SwerveModule[4];
  // double[] wheelAngle = new double[4];
  // double[] wheelSpeed = new double[4];
  // double[] oldAngle = {0,0,0,0};
  // double[] joystickDiff = new double[4];
  // double[] angleError = new double[4];
  public double[] speed = new double[4];
  public double[] angle = new double[4];
  public double[] dirs = new double[4];

  public void gyroDrive (double str, double fwd, double rot) {
    computeInputs(str, fwd, rot);
    setSetpoints();
    getDirections();
    if(rot < 0.05){
      coordinateDirections();
    }

    for(int i = 0; i < 4; i++){
      RobotMap.swerveMod[i].drive(speed[i], angle[i]);
    }
  }

  public void coordinateDirections(){
    double sum = 0;
    for(int i = 0; i < 4; i++){
      sum += dirs[i];
    }
    if(sum > -0.01 && sum < 0.01) { sum = 0.0; }

    SmartDashboard.putNumber("sum", sum);
    if(sum >= 0){
      SmartDashboard.putBoolean("sum dir", true);
    }else{
      SmartDashboard.putBoolean("sum dir", false);
    }
    
    for(int i = 0; i < 4; i++){
      if(sum > 0 && dirs[i] == -1){
        dirs[i] = 1;
        angle[i] = getOppositeAngle(i);
        speed[i] *= -1;
      }else if(sum < 0 && dirs[i] == 1){
        dirs[i] = -1;
        angle[i] = getOppositeAngle(i);
        speed[i] *= -1;
      }
    }
  }

  public double getOppositeAngle(int index){
    double opp = angle[index];
    if(opp < RobotMap.SWERVE_ENC_CIRC/2){
      opp += RobotMap.SWERVE_ENC_CIRC/2;
    }else{
      opp -= RobotMap.SWERVE_ENC_CIRC/2;
    }
    return opp;
  }

  public void computeInputs (double str, double fwd, double rot){
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
    
    speed[1] = Math.sqrt ((a * a) + (d * d));
    speed[2] = Math.sqrt ((a * a) + (c * c));
    speed[0] = Math.sqrt ((b * b) + (d * d));
    speed[3] = Math.sqrt ((b * b) + (c * c));

    angle[1] = Math.atan2 (a, d) / Math.PI;
    angle[2] = Math.atan2 (a, c) / Math.PI;
    angle[0] = Math.atan2 (b, d) / Math.PI;
    angle[3] = Math.atan2 (b, c) / Math.PI;
    
    double max = speed[1];
    if (speed[2] > max) { speed[2] = max; } 
    if (speed[0] > max) { speed[0] = max; } 
    if (speed[3] > max) { speed[3] = max; }
    if (max > 1) { speed[3] /= max; speed[0] /= max; speed[2] /= max; }

        //for turn around when it's faster

    // swerveModules[0] = RobotMap.swerveModFR;
    // swerveModules[1] = RobotMap.swerveModBR;
    // swerveModules[2] = RobotMap.swerveModBL;
    // swerveModules[3] = RobotMap.swerveModFL;

    // double rads[] = new double[4];
    // for(int i=0;i<4;i++){
    //   rads[i] = swerveModules[i].encCount*2*Math.PI/RobotMap.SWERVE_ENC_CIRC;
    //   joystickDiff[i] = wheelAngle[i] - oldAngle[i];
    //   angleError[i] = wheelAngle[i] - rads[i];

    // // if(Math.abs(joystickDiff[i]) == Math.PI + Math.PI/36 || Math.abs(joystickDiff[i]) == Math.PI - Math.PI/36){ //I think this 
    // // //makes it so that if it's less than a 5 degree change then it does the normal drive
    //   if(Math.abs(joystickDiff[i]) > Math.PI/4){ //new angle is greater than a 90degree turn, so find shortest path
    //     //reverse speed motors 
    //     swerveModules[i].drive(wheelSpeed[i], wheelAngle[i]);
        
    //     //find new angle
    //     wheelAngle[i] -= Math.PI; //subtract 180 degrees
    //     // if(wheelAngle[i] < 0){ //wrap to new angle between 0-360
    //     //   wheelAngle[i] += 2*Math.PI;
    //     // }

    //     wheelAngle[i] %= 2*Math.PI;

    //     //now the angle is set to move to the shortest path, which is just 180 degrees 
    //     //from the current heading
        
    //   }else{
    //     // swerveModules[i].drive(-1*wheelSpeed[i]/**4800*4096/600*/, wheelAngle[i]);
    //     swerveModules[i].drive(wheelSpeed[i], wheelAngle[i]);
    //   } 

    //   if(wheelSpeed[i]>0.1){
    //     // pidTurnController[i].setSetpoint(wheelAngles[i]);
    //     oldAngle[i] = wheelAngle[i];
    //   }
    // }
  }

  public void setSetpoints(){
    for(int i = 0; i < 4; i++){
      double encCount = RobotMap.swerveMod[i].encoder.pidGet();
      angle[i] = (angle[i] + 1.0) * RobotMap.SWERVE_ENC_CIRC / 2.0 + RobotMap.SWERVE_SETPOINT_OFFSET[i]; 
      if(angle[i] > RobotMap.SWERVE_ENC_CIRC) { angle[i] -= RobotMap.SWERVE_ENC_CIRC; }

      double adjSetpoint = getOppositeAngle(i);

      if(Math.abs(encCount - angle[i]) > Math.abs(encCount - adjSetpoint)){
        angle[i] = adjSetpoint;
        speed[i] *= -1;
      }
    }
  }

  public void getDirections(){
    for(int i = 0; i < 4; i++){
      double ang = angle[i];
      double enc = RobotMap.swerveMod[i].encoder.pidGet();
      double diff = ang - enc;

      if(diff < 0 && Math.abs(diff) < RobotMap.SWERVE_ENC_CIRC/2){
        dirs[i] = Math.abs(diff);
      }else if(diff < 0 && Math.abs(diff) >= RobotMap.SWERVE_ENC_CIRC/2){
        dirs[i] = -Math.abs(diff);
      }else if(diff >= 0 && Math.abs(diff) < RobotMap.SWERVE_ENC_CIRC/2){
        dirs[i] = -Math.abs(diff);
      }else{
        dirs[i] = Math.abs(diff);
      }
    }
  }
  
  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new GyroSwerveDriveCommand());
  }
}