package org.usfirst.frc.team25.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	private AutonController m_autonController;
	private OI m_OI;
	private Drivebase m_drives;
	private Pickup m_pickup;

	public void robotInit() {
		m_autonController = AutonController.getInstance();
		m_OI = OI.getInstance();
		m_drives = Drivebase.getInstance();
		m_pickup = Pickup.getInstance();
		m_drives.resetGyro();
	}

	private void updateDashboard() {
		SmartDashboard.putNumber("Left Encoder", Math.abs(m_drives.getLeftEncoderDistance()));
		SmartDashboard.putNumber("Right Encoder", Math.abs(m_drives.getRightEncoderDistance()));
		SmartDashboard.putNumber("Arm Pot", m_pickup.getPot());
		SmartDashboard.putNumber("Ultrasonic", m_drives.getUltrasonic());
		SmartDashboard.putNumber("Gyro", m_drives.getGyroAngle());
	}

	public void disabledInit() {
	}

	public void disabledPeriodic() {
		updateDashboard();
		if(Utility.getUserButton()) {
			m_drives.resetEncoders();
			
		}
		System.out.println("Gyro: " + m_drives.getGyroAngle());
		System.out.println("Left Encoder: " + m_drives.getLeftEncoderDistance());
	}

	public void autonomousInit() {
		m_drives.brakesOff();
		m_autonController.resetStep();
		m_drives.resetStep();
		m_drives.resetGyro();
	}

	public void autonomousPeriodic() {
		m_autonController.lowBarAndScore();
	}

	public void teleopInit() {
		m_drives.brakesOff();
	}

	public void teleopPeriodic() {
		m_OI.enableTeleopControls();
		updateDashboard();

		System.out.println("Gyro: " + m_drives.getGyroAngle());
	}

	public void testInit() {
		m_drives.brakesOff();
	}

	public void testPeriodic() {
		m_drives.brakesOff();
	}

}
