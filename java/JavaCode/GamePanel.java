import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    private TextSource textSource;
    private ScorePanel scorePanel;
    private List<FallingWord> fallingWords = new ArrayList<>();
    private Timer spawnTimer;
    private JTextField inputField;
    private boolean isGameOver = false;
    private Runnable onGameOver;
    private String languageMode;

    private Image backgroundImage;
    private AudioPlayer audioPlayer; // AudioPlayer 추가


    public GamePanel(TextSource textSource, ScorePanel scorePanel, Runnable onGameOver, String languageMode) {
        this.textSource = textSource;
        this.scorePanel = scorePanel;
        this.onGameOver = onGameOver;
        this.languageMode = languageMode; // 영어 or 한글 모드
        backgroundImage = new ImageIcon("trade1.jpg").getImage();
        this.audioPlayer = new AudioPlayer(); // AudioPlayer 인스턴스 생성
        
        setLayout(new BorderLayout());
        add(scorePanel, BorderLayout.NORTH);

        // 게임 영역
        GameArea gameArea = new GameArea();
        add(gameArea, BorderLayout.CENTER);

        // 입력창
        inputField = new JTextField(25);
        inputField.setFont(new Font("Malgun Gothic", Font.BOLD, 18));
        inputField.addActionListener(e -> handleInput(gameArea));
        
        // 입력창을 중앙 정렬로 설정한 JPanel에 추가
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // FlowLayout을 사용하여 중앙 정렬
        inputPanel.setOpaque(false); // 배경 투명 설정
        inputPanel.add(inputField); // 입력창 추가

        // 입력창을 하단에 배치
        add(inputPanel, BorderLayout.SOUTH);

        spawnTimer = new Timer(1500, e -> gameArea.addFallingWord());
        
        // 상단바에 음악 제어 버튼 추가
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        
     // 상단바 배경을 투명하게 설정
        controlPanel.setOpaque(false);

     // Play 버튼에 이미지 추가 (크기 조정)
        ImageIcon playIcon = new ImageIcon("start.png");
        Image playImage = playIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH); // 크기 조정
        JButton playButton = new JButton(new ImageIcon(playImage)); // playButton을 선언
        playButton.setPreferredSize(new Dimension(50, 50)); // 버튼 크기 조정
        playButton.setOpaque(false);  // 배경을 투명하게 설정
        playButton.setContentAreaFilled(false); // 버튼 배경 제거
        playButton.setBorderPainted(false); // 버튼 경계선 제거
        
        playButton.addActionListener(e -> {
            audioPlayer.playAudio("song2.wav");  // 음악 재생 버튼
        });
        controlPanel.add(playButton);

        // Stop 버튼에 이미지 추가 (크기 조정)
        ImageIcon stopIcon = new ImageIcon("stop.png");
        Image stopImage = stopIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH); // 크기 조정
        JButton stopButton = new JButton(new ImageIcon(stopImage)); // stopButton을 선언
        stopButton.setPreferredSize(new Dimension(50, 50)); // 버튼 크기 조정
        stopButton.setOpaque(false);  // 배경을 투명하게 설정
        stopButton.setContentAreaFilled(false); // 버튼 배경 제거
        stopButton.setBorderPainted(false); // 버튼 경계선 제거
        
        stopButton.addActionListener(e -> audioPlayer.stopAudio());  // 음악 정지 버튼
        controlPanel.add(stopButton);

        add(controlPanel, BorderLayout.NORTH); // 상단바에 버튼을 추가
    }

    private void handleInput(GameArea gameArea) {
        if (isGameOver) return;

        String input = inputField.getText().trim();
        boolean wordMatched = false;
        
        // 맞는 단어가 있는지 확인
        for (FallingWord word : new ArrayList<>(fallingWords)) {
            if (word.getText().equalsIgnoreCase(input)) {
                scorePanel.increaseScore();
                word.stopFalling();
                fallingWords.remove(word);
                gameArea.remove(word);
                wordMatched = true;
                break;
            }
        }
        // 단어가 맞지 않으면 목숨 감소
        if (!wordMatched) {
            scorePanel.decreaseLife();
        }

        inputField.setText(""); // 입력창 초기화
        gameArea.repaint(); // 게임 화면 갱신

        // 목숨이 0 이하일 경우 게임 오버
        if (scorePanel.getLives() <= 0) {
            gameOver();
        }
    }

    public void startGame() {
        isGameOver = false;
        fallingWords.clear();
        scorePanel.reset();
        spawnTimer.restart();
        inputField.setEnabled(true);
        inputField.setText("");
        
        // 게임 시작 시 음악을 자동으로 재생
        audioPlayer.playAudio("song2.wav");
        repaint();
    }

    private void gameOver() {
        if (isGameOver) return;

        isGameOver = true;
        spawnTimer.stop();
        inputField.setEnabled(false);

        // 음악 정지
        audioPlayer.stopAudio();

        for (FallingWord word : new ArrayList<>(fallingWords)) {
            word.stopFalling();
            remove(word);
        }
        fallingWords.clear();
        repaint();

        // 랭킹 저장
        String rankingFile = languageMode.equals("English") ? "ranking_english.txt" : "ranking_korean.txt";
        RankingManager rankingManager = new RankingManager(rankingFile);
        rankingManager.addScore(scorePanel.getScore());

        List<Integer> topScores = rankingManager.getTopScores(5);
        StringBuilder rankingMessage = new StringBuilder("<font color= 'red'>Game Over!</font><br> Final Score: " + scorePanel.getScore() + "<br><br>Top 5 Scores:<br>");
        
        // 상위 5개 점수 출력
        int rank = 1;
        for (int score : topScores) {
            rankingMessage.append(rank++).append(". ").append(score).append("<br>");
        }

        // HTML로 작성된 메시지
        String message = "<html><body style='font-size:20px;'>" + rankingMessage.toString() + "</body></html>";

        SwingUtilities.invokeLater(() -> {
            // 게임 오버 메시지 및 랭킹과 함께 표시
            JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
            
            
            onGameOver.run();
        });
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    class GameArea extends JPanel {
        public GameArea() {
            setLayout(null);
            setOpaque(false);
        }

        public void addFallingWord() {
            if (isGameOver) return;

            FallingWord word = new FallingWord(textSource.get());
            word.setLocation((int) (Math.random() * (getWidth() - word.getPreferredSize().width)), 0);
            fallingWords.add(word);
            add(word);
            word.startFalling(() -> {
                scorePanel.decreaseLife();
                remove(word);
                fallingWords.remove(word);
                repaint();
                if (scorePanel.getLives() <= 0) gameOver();
            });
            repaint();
        }
    }
}
