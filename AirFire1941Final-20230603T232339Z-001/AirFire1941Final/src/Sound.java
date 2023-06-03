import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;



public class Sound {
	static ArrayList<Clip> audioClip = new ArrayList<Clip>();
	static ArrayList<String> audioClipName = new ArrayList<String>();
	public static void stop(String fileName) {
		try {
			Sound.audioClip.get(Sound.audioClipName.indexOf(fileName)).stop();
			Sound.audioClip.remove(Sound.audioClipName.indexOf(fileName));
			Sound.audioClipName.remove(Sound.audioClipName.indexOf(fileName));
		} catch(Exception e) {}
	}
	public static void reset() {
		for (int i=0;i<audioClip.size();i++) {
			audioClip.get(i).close();
			audioClip.get(i).stop();
		}
		audioClip = new ArrayList<Clip>();
		audioClipName = new ArrayList<String>();
	}
	public static void playSound(String fileName, boolean loop) {
		
		try {
			File url = new File(fileName);
			audioClip.add(AudioSystem.getClip());
			audioClipName.add(fileName);

			AudioInputStream ais = AudioSystem.getAudioInputStream(url);
			audioClip.get(audioClip.size()-1).open(ais);
			if (loop) {
				audioClip.get(audioClip.size()-1);
				audioClip.get(audioClip.size()-1).loop(Clip.LOOP_CONTINUOUSLY);
			} else {
				audioClip.get(audioClip.size()-1).start();
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public static void playSound(String fileName, boolean loop, float gain) {
		try {
			File url = new File(fileName);
			audioClip.add(AudioSystem.getClip());
			audioClipName.add(fileName);

			AudioInputStream ais = AudioSystem.getAudioInputStream(url);
			audioClip.get(audioClip.size()-1).open(ais);
			FloatControl gainControl = (FloatControl) audioClip.get(audioClip.size()-1).getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(gain);
			if (loop) {
				audioClip.get(audioClip.size()-1);
				audioClip.get(audioClip.size()-1).loop(Clip.LOOP_CONTINUOUSLY);
			} else {
				audioClip.get(audioClip.size()-1).start();
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public static void playSoundOnce(String fileName, String id) {
		if (!Task.done(id+"sound")) {
			try {
				File url = new File(fileName);
				Clip clip = AudioSystem.getClip();
	
				AudioInputStream ais = AudioSystem.getAudioInputStream(url);
				clip.open(ais);
				clip.start();
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
			Task.add(id+"sound");
		}
	}
	public static void prime(String id) {
		Task.prime(id+"sound");
	}
}
