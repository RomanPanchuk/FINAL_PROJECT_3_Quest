package other;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuestionTest {

    @Test
    public void testQuestion() {
        Question question = new Question();
        question.setId(1L);
        question.setQuestionText("Test question text");

        Answer answer = new Answer();
        answer.setText("Test text");
        answer.setId("1");
        answer.setAnswerText("Test answer text");
        answer.setGameOverMessage("Кінець гри");
        answer.setAnswerCorrect(true);

        question.setAnswers(Arrays.asList(answer));

        assertEquals(1L, question.getId());
        assertEquals("Test question text", question.getQuestionText());
        assertEquals(1, question.getAnswers().size());
        assertEquals(answer, question.getAnswers().get(0));
    }
}