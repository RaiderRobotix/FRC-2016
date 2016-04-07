package org.usfirst.frc.team25.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	// ====== Robot Classes ======
	private AutonController m_autonController;
	private OI m_OI;
	private Drivebase m_drives;
	private Pickup m_pickup;
	private Timer m_timer;

	// ====== Auton Logic ======
	private SendableChooser m_autonChooser;
	private int m_autonChosen;

	public void robotInit() {
		// ===== ROBOT MECHANISMS =====
		m_autonController = AutonController.getInstance();
		m_OI = OI.getInstance();
		m_drives = Drivebase.getInstance();
		m_pickup = Pickup.getInstance();
		m_timer = new Timer();

		// ===== RESETS =====
		m_drives.resetNavX();
		m_drives.resetEncoders();

		// ===== AUTON STUFF ===== TODO: Fix Autons
		m_autonChooser = new SendableChooser();
		m_autonChooser.addObject("-1: Do Nothing", -1);
		m_autonChooser.addObject("0: Low Bar And Score (Default)", 0);
		m_autonChooser.addObject("1: Teeter Totter Model", 1);
		m_autonChooser.addObject("2: Slot Two Path", 2);
		m_autonChooser.addObject("3: Go Over Obstacle (Special)", 3);
		m_autonChooser.addObject("4: Special Low Bar", 4);
		m_autonChooser.addObject("5: Go Over Obstacle- No Score", 5);
		SmartDashboard.putData("Auton Key", m_autonChooser);
		SmartDashboard.putNumber("Choose Auton", 0);
	}

	private void update() {
		SmartDashboard.putNumber("Left Encoder", m_drives.getLeftEncoderDistance());
		SmartDashboard.putNumber("Right Encoder", m_drives.getRightEncoderDistance());
		SmartDashboard.putNumber("Pickup Pot", m_pickup.getPot());
		SmartDashboard.putNumber("Gyro", m_drives.getGyroAngle());
		SmartDashboard.putNumber("Roll", m_drives.getGyroRoll());
		SmartDashboard.putNumber("Auton Chosen", SmartDashboard.getNumber("Choose Auton"));
		SmartDashboard.putNumber("NavX Compass Heading", m_drives.getNavXCompass());
		SmartDashboard.putNumber("Ultrasonic", m_drives.getSonicDistance());
		if (m_timer.get() > 115.0) {
			SmartDashboard.putString("Time To Hang",
					((m_timer.get() > 115.0 && (m_timer.get() % 1.0) < 0.5) ? "**TIME TO HANG**" : ""));
		} else {
			SmartDashboard.putString("Time To Hang", Double.toString(115.0 - m_timer.get()));
		}
	}

	public void disabledInit() {
		m_timer.stop();
		m_timer.reset();
	}

	public void disabledPeriodic() {
		update();
	}

	public void autonomousInit() {
		m_drives.brakesOff();
		m_autonController.resetStep();
		m_drives.resetStep();
		m_drives.resetNavX();
		m_autonChosen = (int) SmartDashboard.getNumber("Choose Auton");
	}

	@SuppressWarnings("deprecation")
	public void autonomousPeriodic() {
		if (m_autonChosen == 0) {
			m_autonController.lowBarAndScore();
		} else if (m_autonChosen == 1) {
			m_autonController.teeterTotterSlotTwoAndScore();
		} else if (m_autonChosen == 2) {
			m_autonController.slotTwoPath();
		} else if (m_autonChosen == 3) {
			m_autonController.goOverObstacle(0.9);
		} else if (m_autonChosen == 4) {
			m_autonController.lowBarAndScoreSpecial();
		} else if (m_autonChosen == 5) {
			m_autonController.goOverObstacleNoScore();
		}
		update();
	}

	public void teleopInit() {
		m_drives.brakesOff();
		m_timer.start();
		m_timer.reset();
	}

	public void teleopPeriodic() {
		m_OI.enableTeleopControls();
		update();
	}

	public void testInit() {
		m_drives.brakesOff();
	}

	public void testPeriodic() {
		m_drives.brakesOff();
	}

}