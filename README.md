# 🏗️ Trade Word Game 🎮

## 프로젝트 개요
이 프로젝트는 무역 관련 용어(영문/한글)를 게임 형식으로 학습할 수 있는 타자 게임입니다.  
화면에 낙하하는 단어를 입력하면 점수가 증가하고, 오타 또는 시간 초과 시 목숨이 감소합니다.  
총 3개의 목숨이 모두 소진되면 게임이 종료되고, 최종 점수와 랭킹이 표시됩니다.

## 🧩 주요 기능
- 영어 / 한글 모드 선택 가능
- 단어 낙하 애니메이션 및 실시간 타자 입력 처리
- 점수 및 목숨 UI 표시
- 게임 오버 시 랭킹 Top 5 표시 (파일 저장 기반)
- 배경 음악 ON/OFF 기능 (WAV 파일 재생)

## 🛠 사용 기술
| 분류 | 내용 |
|------|------|
| 언어 | Java |
| GUI | Java Swing (`JFrame`, `JPanel`, `JLabel`, `CardLayout` 등) |
| 타이머 | `javax.swing.Timer` 사용, 주기적 이벤트 발생 |
| 음악 재생 | `javax.sound.sampled.Clip` 사용 (WAV 파일 재생/정지) |
| 객체지향 | 인터페이스 (`TextSource`), 다형성 구현 (`EnglishTextSource`, `KoreanTextSource`) |
| 파일 입출력 | `BufferedReader`, `BufferedWriter`를 통한 점수 기록 관리 |
| 자료구조 | `List`, `ArrayList`, `Collections.sort()` |

#
