package org.usfirst.frc.team2557.robot;

import java.lang.Math;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class AdjustedEncoder implements PIDSource {
    private PIDSourceType pst;
    private AnalogInput enc;
    private double startEncVal;
    private double setpoint;
    private int dir;

    public AdjustedEncoder(AnalogInput encoder){
        pst = PIDSourceType.kDisplacement;
        enc = encoder;
        setpoint = 0;
    }

    public void setPIDSourceType​ (PIDSourceType pidSourceType) {
        pst = pidSourceType;
    }

    public PIDSourceType getPIDSourceType()	{
        return pst;
    }

    public void setSetpoint(double point){
        setpoint = point;
        startEncVal = enc.pidGet();
        if(Math.abs(startEncVal - setpoint) < Math.abs(RobotMap.circumference - startEncVal + setpoint)){
            dir = -1;
        }else{
            dir = 1;
        }
    }

    public double pidGet(){
        double encVal = enc.pidGet();
        if(dir == -1){
            if(encVal - startEncVal >= 0){
                return encVal - RobotMap.circumference;
            }else{
                return encVal;
            }
        }else if(dir == 1){
            if(encVal - startEncVal <= 0){
                return encVal + RobotMap.circumference;
            }else{
                return encVal;
            }
        }
    }
}

