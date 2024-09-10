import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Question {
    private Long id;
    private String questionText;
    private List<Answer> answers;

    public Question() {
    }
}
