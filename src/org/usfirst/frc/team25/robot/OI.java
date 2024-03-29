package org.usfirst.frc.team25.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

public class OI {

	private static OI m_instance;

	// ===== Robot Mechanisms =====
	private final Pickup m_pickup;
	private final Drivebase m_drives;
	private final Hanger m_hanger;
	private final LEDs m_leds;

	// ===== Joysticks =====
	private final Joystick m_rightStick;
	private final Joystick m_leftStick;
	private final Joystick m_operatorStick;

	// ===== Auto-move Mechanisms =====
	private boolean m_pickupSequenceRunning;
	private double m_pickupSequenceValue;
	private boolean m_hangerHasRan;
	private Timer m_hangTimer;
	private boolean m_autoHang;
	private double m_pickupAutoSpeed;
	private int m_ledSequence;

	private OI() {
		m_drives = Drivebase.getInstance();
		m_pickup = Pickup.getInstance();
		m_hanger = Hanger.getInstance();
		m_leds = LEDs.getInstance();

		m_rightStick = new Joystick(Constants.RIGHT_JOYSTICK_PORT);
		m_leftStick = new Joystick(Constants.LEFT_JOYSTICK_PORT);
		m_operatorStick = new Joystick(Constants.OPERATOR_JOYSTICK_PORT);

		m_pickupSequenceRunning = false;
		m_pickupAutoSpeed = 0.0;
		m_hangerHasRan = false;
		m_autoHang = false;
		m_ledSequence = 0;
		m_hangTimer = new Timer();
	}

	public static OI getInstance() {
		if (m_instance == null) {
			m_instance = new OI();
		}
		return m_instance;
	}

	public void enableTeleopControls() {
		// =========== RESET ===========
		if (getRightButton(7)) {
			m_drives.resetEncoders();
			m_drives.resetNavX();
		}

		// =========== DRIVES ===========
		if (getLeftTrigger()) {
			m_drives.brakesOn();
		} else if (getLeftButton(2)) {
			m_drives.brakesOff();
		}

		if (!m_drives.brakesAreOn()) {
			m_drives.setSpeed(getLeftY(), getRightY());
		} else {
			m_drives.setSpeed(0.0);
		}

		// =========== PICKUP ROLLERS ===========
		if (getOperatorTrigger()) {
			// If, POV down, override is true.
			m_pickup.intake(getOperatorButton(2));
		} else if (getRightTrigger()) {
			m_pickup.eject();
		} else {
			m_pickup.stopRollers();
		}

		// =========== PICKUP ARM ===========
		if (getOperatorButton(6) && !getOperatorButton(4) && !getOperatorButton(5)) {
			// Button 6, arm up.
			m_pickupSequenceRunning = true;
			m_pickupAutoSpeed = 1.0;
			m_pickupSequenceValue = Constants.PICKUP_ARM_UP;
		} else if (getOperatorButton(4) && !getOperatorButton(6) && !getOperatorButton(5)) {
			// Button 4, arm down.
			m_pickupSequenceRunning = true;
			m_pickupAutoSpeed = 0.8;
			m_pickupSequenceValue = Constants.PICKUP_ARM_DOWN - 0.003;
		} else if (getOperatorButton(5) && !getOperatorButton(6) && !getOperatorButton(4)) {
			// Button 5, port cullis height.
			m_pickupSequenceRunning = true;
			m_pickupAutoSpeed = 0.5;
			m_pickupSequenceValue = Constants.PICKUP_PORT_CULLIS;
		}

		if (getOperatorButton(3) || getOperatorButton(2)) {
			// Run normally
			m_pickupSequenceRunning = false;
			m_pickup.setArmSpeed(getOperatorY(), getOperatorButton(2));
		} else if (!m_pickupSequenceRunning) {
			// If let go of button, return to 0%
			m_pickup.setArmSpeed(0.0, true);
		} else {
			// Pickup Sequence Running

			m_pickupSequenceRunning = m_pickup.goTo(m_pickupSequenceValue, m_pickupAutoSpeed);
		}

		// =========== HANGER ===========
		if (m_hangerHasRan || m_pickup.getPot() <= (Constants.PICKUP_ARM_DOWN + 0.008) || getOperatorButton(2)) {
			if (m_autoHang && !getOperatorButton(10) && !getOperatorButton(8) && !getOperatorButton(9)
					&& !getOperatorButton(7)) {
				// If you are hanging and not trying to move manually
				if (m_hangTimer.get() >= 3.5) {
					m_hanger.setSpeed(0.0);
					m_autoHang = false;
					m_hangTimer.stop();
					m_pickupSequenceRunning = true;
					m_pickupSequenceValue = Constants.PICKUP_BACK_LIMIT;
					m_pickupAutoSpeed = 1.0;
				} else {
					m_hanger.setSpeed(1.0);
				}
			} else if (getOperatorButton(11)) {
				// Turn on Auto hang
				m_hangerHasRan = true;
				m_hangTimer.start();
				m_hangTimer.reset();
				m_autoHang = true;
			} else if (getOperatorButton(9)) {
				// Slow down
				m_hangerHasRan = true;
				m_hanger.setSpeed(-Constants.HANGER_SLOW_SPEED);
				m_autoHang = false;
			} else if (getOperatorButton(8)) {
				// Manual up
				m_hangerHasRan = true;
				m_hanger.setSpeed(1.0);
				m_autoHang = false;
			} else if (getOperatorButton(7)) {
				// Slow up
				m_hangerHasRan = true;
				m_hanger.setSpeed(Constants.HANGER_SLOW_SPEED);
				m_autoHang = false;
			} else if (getOperatorButton(10)) {
				// Manual down
				m_hangerHasRan = true;
				m_hanger.setSpeed(-1.0);
				m_autoHang = false;
			} else {
				m_hanger.setSpeed(0.0);
			}
		} else {
			m_hanger.setSpeed(0.0);
		}
		
		// =========== LEDS ===========
		if(m_hangerHasRan) {
			m_ledSequence = 3;
		} else if (m_pickup.lineBroken()) {
			m_ledSequence = 2;
		} else {
			m_ledSequence = 0;
		}
		m_leds.update(m_ledSequence);
	}

	private double getLeftY() {
		double yval = m_leftStick.getY();
		if (Math.abs(yval) < Constants.DEADBAND) {
			return 0.0;
		} else {
			return -yval;
		}
	}

	private double getRightY() {
		double yval = m_rightStick.getY();
		if (Math.abs(yval) < Constants.DEADBAND) {
			return 0.0;
		} else {
			return -yval;
		}
	}

	private boolean getLeftTrigger() {
		return m_leftStick.getTrigger();
	}

	private boolean getLeftButton(int b) {
		return m_leftStick.getRawButton(b);
	}

	private boolean getRightTrigger() {
		return m_rightStick.getTrigger();
	}

	private boolean getRightButton(int b) {
		return m_rightStick.getRawButton(b);
	}

	private double getOperatorY() {
		double yval = m_operatorStick.getY();
		if (Math.abs(yval) < Constants.DEADBAND) {
			return 0.0;
		} else {
			return -yval;
		}
	}

	private boolean getOperatorTrigger() {
		return m_operatorStick.getTrigger();
	}

	private boolean getOperatorButton(int b) {
		return m_operatorStick.getRawButton(b);
	}

}
