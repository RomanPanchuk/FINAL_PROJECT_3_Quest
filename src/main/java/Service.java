import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    }
}
