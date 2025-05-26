import java.io.*;
import java.util.*;

public class RankingManager {
    private String rankingFile; // 랭킹 파일 경로
    private List<Integer> scores;

    public RankingManager(String fileName) {
        this.rankingFile = fileName;
        this.scores = new ArrayList<>();
        loadScores();
    }

    // 스코어를 파일에서 불러오기
    private void loadScores() {
        try (BufferedReader reader = new BufferedReader(new FileReader(rankingFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                scores.add(Integer.parseInt(line.trim()));
            }
        } catch (IOException e) {
            System.out.println("No previous scores found in " + rankingFile);
        }
    }

    // 새로운 스코어 추가
    public void addScore(int score) {
        scores.add(score);
        Collections.sort(scores, Collections.reverseOrder()); // 내림차순 정렬
        saveScores();
    }

    // 스코어를 파일에 저장
    private void saveScores() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rankingFile))) {
            for (int score : scores) {
                writer.write(score + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 상위 N개 스코어 가져오기
    public List<Integer> getTopScores(int topN) {
        return scores.subList(0, Math.min(topN, scores.size()));
    }
}
