package org.butternut.sb.audio;

import java.util.Optional;

import javax.sound.sampled.Clip;

public class AudioController
{
	private Clip clip;
	
	public void initializeMenuAudio() {
		Optional.ofNullable(this.clip).ifPresent(Clip::stop);
		this.clip = Audio.initAudio("src/main/resources/files/blinkwhats.WAV");
	}
	
	public void initializeGameAudio() {
		Optional.ofNullable(this.clip).ifPresent(Clip::stop);
		this.clip = Audio.initAudio("src/main/resources/files/blinkwhats.WAV");		
	}
}
