import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Answer {

    private String text;
    private String id;
    private String answerText;
    private Question nextQuestion;
    private String gameOverMessage;
    private boolean isAnswerCorrect;

    public Answer() {
    }
}
