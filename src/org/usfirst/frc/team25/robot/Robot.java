package org.usfirst.frc.team25.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	private SendableChooser m_autonChooser;
	private AutonController m_autonController;
	private OI m_OI;
	private Drivebase m_drives;
	private int m_autonPicked;

	public void robotInit() {
		m_autonController = AutonController.getInstance();
		m_OI = OI.getInstance();
		m_drives = Drivebase.getInstance();
		setupDashboard();
	}

	private void setupDashboard() {
		m_autonChooser = new SendableChooser();
		m_autonChooser.addDefault("Do Nothing (default)", 0);
		SmartDashboard.putData("Choose Auton Mode: ", m_autonChooser);
	}

	public void disabledInit() {
	}

	public void disabledPeriodic() {
	}

	public void autonomousInit() {
		m_autonPicked = (int) m_autonChooser.getSelected();
		m_drives.brakesOff();
		m_autonController.resetStep();
	}

	public void autonomousPeriodic() {
	}

	public void teleopInit() {
		m_drives.brakesOff();
	}

	public void teleopPeriodic() {
		m_OI.enableTeleopControls();
	}

	public void testInit() {
		m_drives.brakesOff();
		setupDashboard();
	}

	public void testPeriodic() {
		m_drives.brakesOff();
	}

}
