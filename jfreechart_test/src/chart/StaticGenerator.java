//2D noise image
// f (image == null) 구간 주석 처리
package chart;

import java.awt.Graphics;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.IndexColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.Random;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class StaticGenerator extends JComponent implements Runnable {
	byte[] data;

	BufferedImage image;

	Random random;

	public void initialize() {
		int w = getSize().width, h = getSize().height;
		int length = ((w + 7) * h) / 8;
		data = new byte[length];
		DataBuffer db = new DataBufferByte(data, length);
		WritableRaster wr = Raster.createPackedRaster(db, w, h, 1, null);
		ColorModel cm = new IndexColorModel(1, 2, new byte[] { (byte) 0, (byte) 255 },
				new byte[] { (byte) 0, (byte) 255 }, new byte[] { (byte) 0, (byte) 255 });
		image = new BufferedImage(cm, wr, false, null);
		random = new Random();
		new Thread(this).start();
	}

	public void run() {
		while (true) {
			random.nextBytes(data);
			repaint();
			try {
				Thread.sleep(1000 / 24);
			} catch (InterruptedException e) { /* die */
			}
		}
	}

	// 이미지 처리가 안되어 해당 53 라인 주석처리
	public void paint(Graphics g) {
		// f (image == null)
		initialize();
		g.drawImage(image, 0, 0, this);
	}

	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.add(new StaticGenerator());
		f.setSize(300, 300);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
}
