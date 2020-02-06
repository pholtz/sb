package org.butternut.sb.audio;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class Audio {
	public static Clip initAudio(String path) {
		Clip clip = null;
		try {
			File yourFile = new File(path);
			AudioInputStream stream = AudioSystem.getAudioInputStream(yourFile);
			AudioFormat format = stream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(stream);
			clip.start(); // how do you end this?
		} catch (Exception e) {
			// whatevers
		}
		return clip;
	}
}
