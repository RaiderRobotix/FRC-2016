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

	public void intake(boolean override) {
		if (override || m_lineBreaker.get()) {
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
	public boolean goTo(double value, double speed) {
		double difference = m_pot.get() - value;
		if (Math.abs(difference) <= 0.0005) {
			// If very close, stop
			m_arm.set(0.0);
			return false;
		}
		boolean down = difference > 0.0;
		double slowSpeedRange = 0.025;
		if (Math.abs(difference) <= slowSpeedRange) {
			// Slow down if in close zone
			setArmSpeed((down ? speed / 4.0 : -(speed / 4.0)), false);
		} else {
			setArmSpeed((down ? speed : -speed), false);
		}
		return true;
	}

	/**
	 * Set speed for the pickup using the potentiometer.
	 * 
	 * @param speed
	 *            The speed to run the arm
	 * @param override
	 *            If true, arm can override pot, but slowly.
	 */
	public void setArmSpeed(double speed, boolean override) {
		if (override) {
			// Run at slow speed.
			m_arm.set(speed / 3.0);
			return;
		}
		if ((m_pot.get() <= Constants.PICKUP_ARM_DOWN && speed > 0.0)
				|| (m_pot.get() >= Constants.PICKUP_BACK_LIMIT && speed < 0.0)) {
			// If reached limit, stop.
			m_arm.set(0.0);
		} else {
			if ((m_pot.get() > Constants.PICKUP_BACK_LIMIT - 0.04 && speed < 0.0)
					|| (m_pot.get() < Constants.PICKUP_ARM_DOWN + 0.02 && speed > 0.0)) {
				// If within small range of limit, slow down.
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
