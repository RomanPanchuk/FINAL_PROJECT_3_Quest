package service;

import other.Answer;
import other.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class QuestServiceTest {
    private QuestService gameService;

    @BeforeEach
    public void setUp() {
        gameService = new QuestService();
    }

    @Test
    void getQuestionById() {
        Question question = gameService.getQuestionById("1");
        assertNotNull(question);
        assertEquals("1", Long.toString(question.getId()));
    }

    @Test
    void getAnswerById() {
        Answer answer = gameService.getAnswerById("11");
        assertNotNull(answer);
        assertEquals("11", answer.getId());
    }
}