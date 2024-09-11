import lombok.Getter;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Service {

    private final List<Question> questions;

    public Service() {
        questions = new ArrayList<>();
        loadQuestionsAndAnswers();
    }

    private void loadQuestionsAndAnswers() {
        Map<String, Answer> answerMap = new HashMap<>();
        InputStream is = getClass().getClassLoader().getResourceAsStream("question_answer.txt");
        if (is == null) {
            throw new RuntimeException("Ресурс question_answer.txt не знайдено");
        }
        List<String> lines = IOUtils.readLines(is, "UTF-8");
        for (String line : lines) {
            String[] parts = line.split(": ", 2);
            if (parts[0].length() == 1) {
                Question question = new Question();
                question.setId(Long.parseLong(parts[0]));
                question.setQuestionText(parts[1]);
                question.setAnswers(new ArrayList<>());
                questions.add(question);
            } else if (parts[0].length() == 2) {
                Answer answer = new Answer();
                answer.setId(parts[0]);
                answer.setAnswerText(parts[1]);
                answerMap.put(answer.getId(), answer);
            }
        }
        // Зв’язування відповідей із запитаннями
        for (Question question : questions) {
            String questionId = Long.toString(question.getId());
            for (int i = 1; i <= 2; i++) {
                Answer answer = answerMap.get(questionId + i);
                if (answer != null) {
                    question.getAnswers().add(answer);
                }
            }
        }
        // Пов’язування відповіді з наступним запитанням
        for (int i = 0; i < questions.size() - 1; i++) {
            Question question = questions.get(i);
            for (Answer answer : question.getAnswers()) {
                if (answer.getId().endsWith("1")) {
                    answer.setNextQuestion(questions.get(i + 1));
                    answer.setAnswerCorrect(true);
                } else {
                    answer.setAnswerCorrect(false);
                }
            }
        }
        // Окреме опрацювання останнього питання
        Question lastQuestion = questions.get(questions.size() - 1);
        for (Answer answer : lastQuestion.getAnswers()) {
            if (answer.getId().equals("31")) {
                answer.setAnswerCorrect(true);
            } else if (answer.getId().equals("32")) {
                answer.setAnswerCorrect(false);
            }
        }
    }

    public Question getQuestionById(String questionId) {
        for (Question q : questions) {
            if (Long.toString(q.getId()).equals(questionId)) {
                return q;
            }
        }
        return null;
    }

    public Answer getAnswerById(String answerId) {
        for (Question question : questions) {
            for (Answer answer : question.getAnswers()) {
                if (answer.getId().equals(answerId)) {
                    return answer;
                }
            }
        }
        return null;
    }

    public Question handleGetRequest(String questionId, HttpSession session) {
        if (questionId == null) {
            questionId = "1"; // Починаємо з 1 запитання при відсутності questionId
        }

        Question question = getQuestionById(questionId);

        if ("1".equals(questionId)) {
            session.setAttribute("bodyClass", "answer-waiting");
        }

        return question;
    }

    public void handlePostRequest(String playerName, String answerId, HttpSession session, HttpServletRequest request) {
        if (playerName != null) {
            session.setAttribute("playerName", playerName);

            if (session.getAttribute("gamesPlayed") == null) {
                session.setAttribute("gamesPlayed", 0);
            }
            // Установлюємо для параметра isAnswerCorrect значення false, щоб почати нову гру
            session.setAttribute("isAnswerCorrect", false);
            request.setAttribute("redirect", "/game?id=1");
            return;
        }

        Answer selectedAnswer = getAnswerById(answerId);

        if (selectedAnswer != null) {
            session.setAttribute("isAnswerCorrect", selectedAnswer.isAnswerCorrect());
            String className = selectedAnswer.isAnswerCorrect() ? "answer-right" : "answer-wrong";
            session.setAttribute("bodyClass", className);
            if (selectedAnswer.getNextQuestion() != null) {
                // Якщо надано nextQuestion, установлюємо для bodyClass значення answer-waiting і перенаправте до нього
                session.setAttribute("bodyClass", "answer-waiting");
                request.setAttribute("redirect", "/game?questionId=" + selectedAnswer.getNextQuestion().getId());
            } else {
                handleGameOver(selectedAnswer, session, request);
            }
        }
    }

    private void handleGameOver(Answer selectedAnswer, HttpSession session, HttpServletRequest request) {
        // Якщо немає наступного запитання, гру завершено
        String gameOverMessage;
        if (selectedAnswer.getId() != null) {
            Map<String, String> gameOverMessages = new HashMap<>();
            gameOverMessages.put("31", "Молодець ! Так тримати !");
            gameOverMessages.put("12", "Не жартуйте так ! Давайте заново!");
            gameOverMessages.put("22", "Ви потонули в рутиній типизації!");
            gameOverMessages.put("32", "Ну такий собі вибір! +5 до індусської англійської, -10 до мотивації");

            gameOverMessage = gameOverMessages.getOrDefault(selectedAnswer.getId(), "");
        } else {
            gameOverMessage = "Кінець гри";
        }
        session.setAttribute("gameOverMessage", gameOverMessage);

        Integer gamesPlayed = (Integer) session.getAttribute("gamesPlayed");
        if (gamesPlayed == null) {
            gamesPlayed = 0;
        }
        gamesPlayed++;
        session.setAttribute("gamesPlayed", gamesPlayed);
        request.setAttribute("isAnswerCorrect", selectedAnswer.isAnswerCorrect());
        request.setAttribute("redirect", "/gameover.jsp");
    }
}
