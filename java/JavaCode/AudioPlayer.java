import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer {
    private Clip clip;

    public void playAudio(String filePath) {
        try {
            // WAV 파일을 불러오기
            File audioFile = new File(filePath);
            
            // 오디오 스트림을 가져온다
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            
            // 클립 객체를 가져온다
            clip = AudioSystem.getClip();
            
            // 클립에 오디오 스트림을 연결한다
            clip.open(audioStream);
            
            // 오디오 재생
            clip.loop(Clip.LOOP_CONTINUOUSLY); // 무한 반복 재생
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    public void stopAudio() {
        if (clip != null && clip.isRunning()) {
            clip.stop(); // 오디오 멈추기
            clip.setFramePosition(0); // 음악을 처음부터 다시 재생할 수 있도록 위치 초기화
        }
    }
}
