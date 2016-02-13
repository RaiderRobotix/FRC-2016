package org.usfirst.frc.team25.robot;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;

public class Pickup {

	private static Pickup m_instance;
	private final Talon m_rollers;
	private final Talon m_arm;
	private final AnalogPotentiometer m_pot;
	private final DigitalInput m_lineBreaker;

	private Pickup() {
		m_rollers = new Talon(Constants.PICKUP_ROLLERS_PWM);
		m_arm = new Talon(Constants.PICKUP_ARM_PWM);
		m_lineBreaker = new DigitalInput(Constants.PICKUP_LINE_BREAKER_PWM);
		m_pot = new AnalogPotentiometer(Constants.PICKUP_POT_PWM);
	}

	public static Pickup getInstance() {
		if (m_instance == null) {
			m_instance = new Pickup();
		}
		return m_instance;
	}

	public void intake() {
		if (m_lineBreaker.get()) {
			m_rollers.set(1.0);
		} else {
			m_rollers.set(0.0);
		}
	}

	public void eject() {
		m_rollers.set(-1.0);
	}

	public void stopRollers() {
		m_rollers.set(0.0);
	}

	/**
	 * Move the pickup to a certain value via the potentiometer.
	 * 
	 * @param Value
	 *            the pot value you wish to go to.
	 * 
	 * @return False, if complete.
	 */
	public boolean goTo(double value) {
		double difference = m_pot.get() - value;
		if (Math.abs(difference) <= 0.0005) {
			// If very close, stop
			m_arm.set(0.0);
			return false;
		}
		boolean down = difference > 0.0;
		double slowSpeed = 0.02;
		if (value == Constants.PICKUP_ARM_UP) {
			slowSpeed = 0.04;
		}
		if (Math.abs(difference) <= slowSpeed) {
			// Slow down if in close zone
			setArmSpeed((down ? 0.5 : -0.5), false);
		} else {
			setArmSpeed((down ? 1.0 : -1.0), false);
		}
		return true;
	}

	public void setArmSpeed(double speed, boolean override) {
		if (override) {
			m_arm.set(speed / 3.0);
			return;
		}
		if ((m_pot.get() <= Constants.PICKUP_ARM_DOWN && speed > 0.0)
				|| (m_pot.get() >= Constants.PICKUP_ARM_UP && speed < 0.0)) {
			m_arm.set(0.0);
		} else {
			if ((m_pot.get() > Constants.PICKUP_ARM_UP - 0.04 && speed < 0.0)
					|| (m_pot.get() < Constants.PICKUP_ARM_DOWN + 0.02 && speed > 0.0)) {
				m_arm.set(speed / 2.0);
			} else {
				m_arm.set(speed);
			}
		}
	}

	public double getPot() {
		return m_pot.get();
	}

}
