package org.usfirst.frc.team25.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OI {

	private static OI m_instance;

	private final Drivebase m_drives;
	private final Joystick m_rightStick;
	private final Joystick m_leftStick;
	private final Joystick m_operatorStick;
	
	public OI() {
		m_drives = Drivebase.getInstance();
		m_rightStick = new Joystick(Constants.RIGHT_JOYSTICK_PORT);
		m_leftStick = new Joystick(Constants.LEFT_JOYSTICK_PORT);
		m_operatorStick = new Joystick(Constants.OPERATOR_JOYSTICK_PORT);
	}

	public static OI getInstance() {
		if (m_instance == null) {
			m_instance = new OI();
		}
		return m_instance;
	}

	public void enableTeleopControls() {
		if(getLeftTrigger()) {
			m_drives.brakesOn();
		} else if(getLeftButton(2)) {
			m_drives.brakesOff();
		}
		
		if(!m_drives.brakesAreOn()) {
			m_drives.setSpeed(getLeftY(), getRightY());
		} else {
			m_drives.setSpeed(0.0);
		}
		
	}
	
	private double getLeftY() {
		double yval = m_leftStick.getY();
		if(Math.abs(yval) < Constants.DEADBAND) {
			return 0.0;
		} else {
			return -yval;
		}
	}
	
	private double getRightY() {
		double yval = m_rightStick.getY();
		if(Math.abs(yval) < Constants.DEADBAND) {
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
	
}
