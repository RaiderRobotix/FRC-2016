package org.usfirst.frc.team25.robot;

import edu.wpi.first.wpilibj.I2C;

public class LEDs {

	private static LEDs m_instance;

	private final I2C m_i2c;
	private byte[] m_dataToSend;

	private LEDs() {
		m_i2c = new I2C(I2C.Port.kOnboard, Constants.I2C_DEVICE_ADDRESS);
		m_dataToSend = new byte[] { 0, -128, -128, -128 };
	}

	public static LEDs getInstance() {
		if (m_instance == null) {
			m_instance = new LEDs();
		}
		return m_instance;
	}

	public void setRed(int r) {
		m_dataToSend[1] = (byte) (r < 128 ? r : -(r - 127));
	}

	public void setGreen(int g) {
		m_dataToSend[2] = (byte) (g < 128 ? g : -(g - 127));
	}

	public void setBlue(int b) {
		m_dataToSend[3] = (byte) (b < 128 ? b : -(b - 127));
	}

	public void setRGB(int r, int g, int b) {
		setRed(r);
		setGreen(g);
		setBlue(b);
	}

	public void update() {
		m_i2c.writeBulk(m_dataToSend);
	}

	public void setOn(boolean b) {
		m_dataToSend[0] = (byte) (b ? 0 : 1);
	}

	public void reset() {
		m_dataToSend[0] = 0;
		update();
		m_dataToSend[0] = 1;
	}

}
