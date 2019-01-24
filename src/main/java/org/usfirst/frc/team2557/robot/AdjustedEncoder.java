package org.usfirst.frc.team2557.robot;

import java.lang.Math;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class AdjustedEncoder implements PIDSource {
    private PIDSourceType pst;
    private AnalogInput enc;
    private double prevEnc;
    private double setpoint;

    public AdjustedEncoder(int index){
        pst = PIDSourceType.kDisplacement;
        enc = new AnalogInput(index);
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
    }

    public double pidGet(){
        double encVal = enc.pidGet();
        // if(Math.abs(prevEnc - setpoint) <= Math.abs(RobotMap.circumference - prevEnc + setpoint) 
        //         && encVal - prevEnc >= 0){
        //     return encVal - RobotMap.circumference;
        // }else if(Math.abs(prevEnc - setpoint) > Math.abs(RobotMap.circumference - prevEnc + setpoint)
        //         && encVal - prevEnc <= 0){
        //     return encVal + RobotMap.circumference;
        // }else{
        //     prevEnc = encVal;
        // }
        return encVal;
    }
}

