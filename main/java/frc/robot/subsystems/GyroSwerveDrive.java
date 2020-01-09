package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.Constants;

public class GyroSwerveDrive extends SubsystemBase {
  public double[] speed = new double[4];
  public double[] angle = new double[4];
  public boolean fcd = true;

  public void gyroDrive (double str, double fwd, double rot) {
    boolean bumper = RobotContainer.dbumperRight.get();
    if(!bumper){
      fcd = true;
    }else{
      fcd = false;
    }
    SmartDashboard.putBoolean("FCD", fcd);

    computeSwerveInputs(str, fwd, rot);
    setSetpoints(rot); 

    for(int i = 0; i < 4; i++) {
        Constants.swerveMod[i].drive(speed[i], angle[i]);
    }
  }

  public void drive(double str, double fwd, double rot) {
    double a = str - rot * 1.5 * (Constants.SWERVE_LENGTH / Constants.SWERVE_RADIUS);
    double b = str + rot * 1.5 * (Constants.SWERVE_LENGTH / Constants.SWERVE_RADIUS);
    double c = fwd - rot * 1.5 * (Constants.SWERVE_WIDTH / Constants.SWERVE_RADIUS);
    double d = fwd + rot * 1.5 * (Constants.SWERVE_WIDTH / Constants.SWERVE_RADIUS);
    
    speed[1] = Math.sqrt ((a * a) + (d * d));
    speed[2] = Math.sqrt ((a * a) + (c * c));
    speed[0] = Math.sqrt ((b * b) + (d * d));
    speed[3] = Math.sqrt ((b * b) + (c * c));

    angle[1] = Math.atan2 (a, d) / Math.PI;
    angle[2] = Math.atan2 (a, c) / Math.PI;
    angle[0] = Math.atan2 (b, d) / Math.PI;
    angle[3] = Math.atan2 (b, c) / Math.PI;
    setSetpoints(rot); 
    for(int i = 0; i < 4; i++) {
        Constants.swerveMod[i].drive(speed[i], angle[i]);
    }
  }

  public void autoDrive(double[] angle, double[] speed){
    for(int i = 0; i < 4; i++){
      Constants.swerveMod[i].drive(0, 0);
    }
  }

  public void gyroDriveAngle(){
    // if(RobotContainer.dterribleRight.get()){
    //   angle[0] = Constants.SWERVE_SETPOINT_OFFSET[0] - (Constants.SWERVE_ENC_CIRC/8); 
    //   angle[1] = Constants.SWERVE_SETPOINT_OFFSET[1] + (Constants.SWERVE_ENC_CIRC/8);
    //   angle[2] = Constants.SWERVE_SETPOINT_OFFSET[2] - (Constants.SWERVE_ENC_CIRC/8);
    //   angle[3] = Constants.SWERVE_SETPOINT_OFFSET[3] + (Constants.SWERVE_ENC_CIRC/8);
    // }
    for(int i = 0; i < 4; i++) {
      // if(RobotContainer.dterribleRight.get()){
      //   // speed[i] = 0.0;
      // }
      Constants.swerveMod[i].drive(0, angle[i]);
    }
  }

  public double getOppositeAngle(int index){
    double opp = angle[index];
    if(opp < Constants.SWERVE_ENC_CIRC/2) opp += Constants.SWERVE_ENC_CIRC/2;
    else opp -= Constants.SWERVE_ENC_CIRC/2;
    return opp;
  }

  public void computeSwerveInputs (double str, double fwd, double rot){
    double gyroAngle = -1 * Math.toRadians(Constants.gyro.getAngle() % 360);

    if(fcd){
      double intermediary = fwd * Math.cos(gyroAngle) + str * Math.sin(gyroAngle);
      str = -fwd * Math.sin(gyroAngle) + str * Math.cos(gyroAngle);
      fwd = intermediary;
    }

    double a = str - rot * (Constants.SWERVE_LENGTH / Constants.SWERVE_RADIUS);
    double b = str + rot * (Constants.SWERVE_LENGTH / Constants.SWERVE_RADIUS);
    double c = fwd - rot * (Constants.SWERVE_WIDTH / Constants.SWERVE_RADIUS);
    double d = fwd + rot * (Constants.SWERVE_WIDTH / Constants.SWERVE_RADIUS);
    
    speed[1] = Math.sqrt ((a * a) + (d * d));
    speed[2] = Math.sqrt ((a * a) + (c * c));
    speed[0] = Math.sqrt ((b * b) + (d * d));
    speed[3] = Math.sqrt ((b * b) + (c * c));

    angle[1] = Math.atan2 (a, d) / Math.PI;
    angle[2] = Math.atan2 (a, c) / Math.PI;
    angle[0] = Math.atan2 (b, d) / Math.PI;
    angle[3] = Math.atan2 (b, c) / Math.PI;
  }

  public void setSetpoints(double rot){
    for(int i = 0; i < 4; i++){
      // SmartDashboard.putNumber("angle: " + i, angle[i]);
      // SmartDashboard.putNumber("speed: " + i, speed[i]);

      double encCount = Constants.swerveMod[i].encoder.pidGet();
      angle[i] = (angle[i] + 1) * Constants.SWERVE_ENC_CIRC / 2 + Constants.SWERVE_SETPOINT_OFFSET[i]; 
      if(angle[i] > Constants.SWERVE_ENC_CIRC) angle[i] -= Constants.SWERVE_ENC_CIRC;

      double degreesBeforeFlip = 90.0;
      if(Math.abs(encCount - angle[i]) > Constants.SWERVE_ENC_CIRC / 360 * degreesBeforeFlip) {
        angle[i] = getOppositeAngle(i);
        speed[i] *= -1;
      }
    }
  }

  public void driveStraight(double fwd){
    double a = 0;
    double b = 0;
    double c = Constants.driveDirection * -fwd;
    double d = Constants.driveDirection * -fwd;
    
    speed[1] = Math.sqrt (d * d);
    speed[2] = Math.sqrt (c * c);
    speed[0] = Math.sqrt (d * d);
    speed[3] = Math.sqrt (c * c);

    angle[1] = Math.atan2 (a, d) / Math.PI;
    angle[2] = Math.atan2 (a, c) / Math.PI;
    angle[0] = Math.atan2 (b, d) / Math.PI;
    angle[3] = Math.atan2 (b, c) / Math.PI;

    for(int i = 0; i < 4; i++){
      double encCount = Constants.swerveMod[i].encoder.pidGet();
      angle[i] = (angle[i] + 1) * Constants.SWERVE_ENC_CIRC / 2 + Constants.SWERVE_SETPOINT_OFFSET[i]; 
      if(angle[i] > Constants.SWERVE_ENC_CIRC) angle[i] -= Constants.SWERVE_ENC_CIRC;

      double degreesBeforeFlip = 90.0;
      if(Math.abs(encCount - angle[i]) > Constants.SWERVE_ENC_CIRC / 360 * degreesBeforeFlip) {
        angle[i] = getOppositeAngle(i);
        speed[i] *= -1;
      }

      Constants.swerveMod[i].drive(speed[i], angle[i]);
    }
  }
  
}