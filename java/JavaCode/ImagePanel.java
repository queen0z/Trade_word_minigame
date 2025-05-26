import javax.swing.*;
import java.awt.*;
import java.io.File;

class ImagePanel extends JPanel {
    private Image backgroundImage;

    public ImagePanel(String imagePath) {
        try {
            // 파일 시스템 경로를 사용해 이미지 불러오기
            backgroundImage = new ImageIcon(new File(imagePath).getAbsolutePath()).getImage();
        } catch (Exception e) {
            System.err.println("Image not found: " + imagePath);
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
