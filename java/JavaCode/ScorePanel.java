import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {
    private int score = 0;
    private int lives = 3;
    private JLabel scoreLabel;
    private JLabel livesLabel;

    public ScorePanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(Color.WHITE);

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 25));
        scoreLabel.setForeground(Color.RED);
        add(scoreLabel);

        livesLabel = new JLabel("Lives: 3");
        livesLabel.setFont(new Font("Arial", Font.BOLD, 25));
        livesLabel.setForeground(Color.RED);
        add(livesLabel);
    }

    public void increaseScore() {
        score += 10;
        scoreLabel.setText("Score: " + score);  // 스코어 레이블 업데이트
        repaint();  // 화면 갱신
    }

    public void decreaseLife() {
        if (lives > 0) {
            lives--;
            livesLabel.setText("Lives: " + lives);  // 목숨 레이블 업데이트
            repaint();  // 화면 갱신
        }
    }

    public int getLives() {
        return lives;
    }

    public int getScore() {
        return score;
    }

    public void reset() {
        score = 0;
        lives = 3;
        scoreLabel.setText("Score : 0");
        livesLabel.setText("Lives : 3");
        repaint();  // 화면 갱신
    }
}
