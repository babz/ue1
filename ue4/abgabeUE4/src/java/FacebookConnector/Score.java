package FacebookConnector;

public class Score implements Comparable<Score> {

    private Integer scoreResult;
    private String playerName;

    public Score(Integer scoreResult, String playerName) {
        this.scoreResult = scoreResult;
        this.playerName = playerName;
    }

    public Score() {
    }

    public Integer getScoreResult() {
        return scoreResult;
    }

    public void setScoreResult(Integer scoreResult) {
        this.scoreResult = scoreResult;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getFacebookPublicationString() {
        return "Points: " + this.scoreResult + "; Name: " + this.playerName;
    }

    @Override
    public String toString() {
        return "Score [scoreResult=" + scoreResult + ", playerName=" + playerName + "]";
    }

    @Override
    public int compareTo(Score s) {
        if (scoreResult == s.getScoreResult()) {
            return 0;
        }
        if (scoreResult < s.getScoreResult()) {
            return 1;
        }
        return -1;
    }
}
