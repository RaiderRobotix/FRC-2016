package org.usfirst.frc.team25.robot;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj.Relay.Value;

public class LEDs {

	private static LEDs m_instance;

	private final Relay m_pickupIndicator;
	private final Relay m_allianceIndicator;

	private LEDs() {
		m_pickupIndicator = new Relay(Constants.GREEN_SPIKE_PWM);
		m_allianceIndicator = new Relay(Constants.RED_BLUE_SPIKE_PWM);
	}

	public static LEDs getInstance() {
		if (m_instance == null) {
			m_instance = new LEDs();
		}
		return m_instance;
	}

	public void setGreen(boolean on) {
		if (on) {
			m_pickupIndicator.set(Value.kOn);
		} else {
			m_pickupIndicator.set(Value.kOff);
		}
	}

	/**
	 * Set the color of the red and blue LEDs.
	 * 
	 * @param on
	 *            True, if the spike is on.
	 * @param red
	 *            True, if red. False, if not.
	 */
	public void setAllianceIndicator(boolean on, boolean red) {
		if (on) {
			if (red) { // TODO: Check polarity.
				m_allianceIndicator.setDirection(Direction.kForward);
			} else {
				m_allianceIndicator.setDirection(Direction.kReverse);
			}
			m_allianceIndicator.set(Value.kOn);
		} else {
			m_allianceIndicator.set(Value.kOff);
		}
	}

}
