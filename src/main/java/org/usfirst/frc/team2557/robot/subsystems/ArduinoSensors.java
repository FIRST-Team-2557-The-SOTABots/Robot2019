// package org.usfirst.frc.team2557.robot.subsystems;

// import java.util.ArrayList;

// import org.usfirst.frc.team2557.robot.RobotMap;

// import edu.wpi.first.wpilibj.command.Subsystem;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// public class ArduinoSensors extends Subsystem {
//   public static String lastRead = null;
//   public static String str = "";
// 	public static ArrayList<String> list = new ArrayList<String>();

//   public class MyPair{
//     public char id;
//     public int value;
//   }

//   public MyPair parseInput(String input){
//     MyPair pair = new MyPair();

//     if(input.length() >= 2){
//       pair.id = input.charAt(0);

//       if(pair.id > 'z' || pair.id < 'a'){
//         return null;
//       }

//       pair.value = Integer.parseInt(input.substring(1, input.length() - 1));
//       return pair;
//     }else{
//       return null;
//     }
//   }

//   public void parseNumber(String str, double num, String key){
// 		if(str.length() != 0){
// 		  num *= 10;
// 		  parseNumber(str.substring(1), num + str.charAt(0) - 48, key);
// 		}else{
// 			RobotMap.arduino.put(key, num);
// 		  System.out.println("put: " + key + " + " + num);
//     }
//   }
    
//     public void sensors(){
//       if (RobotMap.serial.getBytesReceived() == 0)
// 			return;

// 		str += RobotMap.serial.readString();
// 		while (str.indexOf('\n') != -1) {
// 			list.add(str.substring(0, str.indexOf('\n')));
// 			str = str.substring(str.indexOf('\n') + 1);
// 		}

// 		for (int i = 0; i < list.size(); i++) {
// 			String temp = list.get(i);
// 			for (String key : RobotMap.arduino.keySet()) {
// 				if (temp.contains(key)) {
// 					String[] arr = temp.split(key);
// 					parseNumber(arr[1], 0, key);
// 				}
// 			}
// 			list.remove(i);
// 		}
// 		SmartDashboard.putString("tof values", RobotMap.arduino.values().toString());
//     }

//   // public void sensors(){
    
//   //   try{
//   //     if(RobotMap.serial.getBytesReceived() <= 0){
//   //       return;
//   //     }

//   //     String str = RobotMap.serial.readString();

//   //     if(lastRead != null){
//   //       str = lastRead + str;
//   //       lastRead = null;
//   //     }

//   //     int nextLN = str.indexOf('\n', 0);

//   //     if(nextLN < str.length() - 1){
//   //       lastRead = str.substring(nextLN);
//   //     }

//   //     MyPair result = parseInput(str);

//   //     if(result == null){
//   //       return;
//   //     }

//   //     if(result.id == 'a'){
//   //       RobotMap.tof1 = result.value;
//   //       SmartDashboard.putNumber("Tof1", RobotMap.tof1);
//   //     }else if(result.id == 'b'){
//   //       RobotMap.tof2 = result.value;
//   //       SmartDashboard.putNumber("Tof2", RobotMap.tof2);
//   //     }else if(result.id == 'c'){
//   //       RobotMap.IR1 = result.value;
//   //       SmartDashboard.putNumber("IR1", RobotMap.IR1);
//   //     }else if(result.id == 'd'){
//   //       RobotMap.tof1 = result.value;
//   //       SmartDashboard.putNumber("IR2", RobotMap.IR2);
//   //     }else if(result.id == 'e'){
//   //       RobotMap.tof1 = result.value;
//   //       SmartDashboard.putNumber("IR3", RobotMap.IR3);
//   //     }

//   //   }catch(Exception e){
//   //     SmartDashboard.putString("Ard Error", "Errrr");
//   //   }

//   // }

//   @Override
//   public void initDefaultCommand() {
//     // Set the default command for a subsystem here.
//     // setDefaultCommand(new MySpecialCommand());
//   }
// }
