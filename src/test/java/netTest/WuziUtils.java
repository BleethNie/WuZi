package netTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

@SuppressWarnings("restriction")
public class WuziUtils {
	//public static final int  MAX_SIZE =169;

	public static Boolean getWin(List<Integer> list) {
		if (list == null || list.size() < 5) {
			return false;
		}

		int tmp = list.get(list.size() - 1);
		int h = 0;
		for (int i = 0; i <= 10; i++) {
			if (list.contains(tmp - 500 + i * 100)) {
				++h;
				if (h == 5) {
					return true;
				}
			} else {
				h = 0;
			}
		}

		h = 0;
		for (int i = 0; i <= 10; i++) {
			if (list.contains(tmp - 5 + i)) {
				++h;
				if (h == 5) {
					return true;
				}
			} else {
				h = 0;
			}
		}

		 h = 0;
		for (int i = 0; i <= 10; i++) {
			if (list.contains(tmp - 505 + 101 * i)) {
				++h;
				if (h == 5) {
					return true;
				}
			} else {
				h = 0;
			}
		}

		h = 0;
		for (int i = 0; i <= 10; i++) {
			if (list.contains(tmp - 495 + 99 * i)) {
				++h;
				if (h == 5) {
					return true;
				}
			} else {
				h = 0;
			}
		}
		return false;
	}

	public static void sound(int i){
		String filenaem ="";
		if(i==1){
			filenaem = System.getProperty("user.dir")+IOUtils.DIR_SEPARATOR+
			"SoundResource"+IOUtils.DIR_SEPARATOR+"Windows Ding.wav";
		}else if(i==2){
			filenaem = System.getProperty("user.dir")+IOUtils.DIR_SEPARATOR+
					"SoundResource"+IOUtils.DIR_SEPARATOR+"Windows Foreground.wav";
		}else{
			filenaem = System.getProperty("user.dir")+IOUtils.DIR_SEPARATOR+
					"SoundResource"+IOUtils.DIR_SEPARATOR+"Windows User Account Control.wav";
		}
		InputStream in=null;
		try {
			in = new FileInputStream(filenaem);
			AudioStream as = new AudioStream(in);
			AudioPlayer.player.start(as);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
