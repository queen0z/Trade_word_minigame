import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    // 영어와 한국어 게임 패널 변수 선언
    private GamePanel englishGame;
    private GamePanel koreanGame;

    public GameFrame() {
        setTitle("Trade Word Game");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // 스코어 패널 생성
        ScorePanel englishScorePanel = new ScorePanel();
        ScorePanel koreanScorePanel = new ScorePanel();

        // GamePanel 초기화
        englishGame = new GamePanel(new EnglishTextSource(), englishScorePanel, this::showStartScreen, "English");
        koreanGame = new GamePanel(new KoreanTextSource(), koreanScorePanel, this::showStartScreen, "Korean");

        // 초기 화면과 각 게임 화면 추가
        mainPanel.add(createStartPanel(), "Start");
        mainPanel.add(createGameContainer(englishGame, englishScorePanel), "EnglishGame");
        mainPanel.add(createGameContainer(koreanGame, koreanScorePanel), "KoreanGame");

        add(mainPanel);
        cardLayout.show(mainPanel, "Start");
    }

    private JPanel createStartPanel() {
        ImagePanel panel = new ImagePanel("trade.jpg");
        panel.setLayout(new BorderLayout());

        // 제목 패널 (불투명한 배경 추가)
        JPanel titlePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                // 불투명도 50% 하얀색 배경
                g2d.setColor(new Color(255, 255, 255, 128)); // 불투명도 50%
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // 둥근 배경 사각형
            }
        };
        titlePanel.setOpaque(false); // 배경 투명
        titlePanel.setLayout(new GridBagLayout());

        
        // 제목 라벨
        JLabel title = new JLabel("Trade Word Game", JLabel.CENTER);
        title.setFont(new Font("Malgun Gothic", Font.BOLD, 60));
        title.setForeground(Color.BLACK);
        titlePanel.add(title);
        
        panel.add(titlePanel, BorderLayout.CENTER);

     // 게임 시작 버튼에 사용할 이미지 크기 조정
        ImageIcon startIcon = new ImageIcon(new ImageIcon("gamestart.png").getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH));

     // 이미지를 버튼에 설정
        JButton startButton = new JButton(startIcon);
        startButton.setPreferredSize(new Dimension(200, 100));  // 버튼 크기 조정
        
     // 버튼의 배경을 투명하게 설정
        startButton.setContentAreaFilled(false);  // 버튼의 내용 영역을 투명하게 설정
        startButton.setBorderPainted(false);     // 버튼 테두리 제거
        startButton.setFocusPainted(false);      // 포커스 효과 제거 (원하는 경우)
        
        startButton.addActionListener(e -> {
            
        	// 사용자 정의 아이콘 로드
            ImageIcon customIcon = new ImageIcon("qustion.png"); // 
         // 아이콘 크기 조정 (너비 50, 높이 50으로 변경)
            Image iconImage = customIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            customIcon = new ImageIcon(iconImage); // 크기 조정된 이미지로 다시 ImageIcon 생성
            
        	String[] options = {"English", "Korean"};
            int choice = JOptionPane.showOptionDialog(
                    this, "Select Word Language", "YOUR CHOICE",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                    customIcon, options, options[0]
            );
            
           
            if (choice == 0) { // English 선택
                cardLayout.show(mainPanel, "EnglishGame");
                englishGame.startGame();
            } else if (choice == 1) { // Korean 선택
                cardLayout.show(mainPanel, "KoreanGame");
                koreanGame.startGame();
            }
        });
        
   
        // 버튼 패널
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(startButton);
        
        
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

  
    private JPanel createGameContainer(GamePanel gamePanel, ScorePanel scorePanel) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scorePanel, BorderLayout.NORTH);
        panel.add(gamePanel, BorderLayout.CENTER);
        return panel;
    }

    private void showStartScreen() {
        cardLayout.show(mainPanel, "Start");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameFrame().setVisible(true));
    }
}
