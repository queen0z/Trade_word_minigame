import javax.swing.*;
import java.awt.*;

public class FallingWord extends JLabel {
    private Timer moveTimer;

    public FallingWord(String text) {
        super(text);
        setFont(new Font("Malgun Gothic", Font.BOLD, 20));
        
        // 여기서 글씨 색상 설정
        setForeground(Color.BLACK); // 원하는 색상으로 변경 (예: 빨간색)
        
        setSize(200, 40);
    }

    public void startFalling(Runnable onReachBottom) {
        moveTimer = new Timer(50, e -> moveDown(onReachBottom));
        moveTimer.start();
    }

    private void moveDown(Runnable onReachBottom) {
        int newY = getY() + 1; // 단어의 이동 속도 설정
        if (newY >= getParent().getHeight()) { // 바닥에 닿으면
            moveTimer.stop();
            onReachBottom.run();
        } else {
            setLocation(getX(), newY); // 단어 이동
        }
    }

    public void stopFalling() {
        if (moveTimer != null) {
            moveTimer.stop();
        }
    }
}
