package pro.sky.coursework2.services;

import org.springframework.stereotype.Service;
import pro.sky.coursework2.exceptions.BadParamsException;
import pro.sky.coursework2.exceptions.ElementNotFoundException;
import pro.sky.coursework2.exceptions.NoContentException;
import pro.sky.coursework2.Question;
import pro.sky.coursework2.interfaces.QuestionService;

import java.util.*;

@Service
public class JavaQuestionService implements QuestionService {

    private final Set<Question> questions;

    public JavaQuestionService(Set<Question> questions) {
        this.questions = questions;
    }

    @Override
    public Question questionAdd(Question question) {
        questions.add(question);
        return question;
    }

    @Override
    public Question questionAdd(String question, String answer) {
        if (question.equals(answer)) {
            throw new BadParamsException();
        }
        return questionAdd(new Question(question, answer));
    }

    @Override
    public Question remove(Question question) {
        if (questions.remove(question)) {
            return question;
        } else {
            throw new ElementNotFoundException();
        }
    }

    @Override
    public Collection<Question> getAll() {
        return new HashSet<>(questions);
    }

    @Override
    public Question getRandomQuestion() {
        if (questions.isEmpty()) {
            throw new NoContentException();
        }
        Random random = new Random();
        int randomIndex = random.nextInt(questions.size());
        return new ArrayList<>(questions).get(randomIndex);
    }

    public int getQuestionsAmount() {
        return questions.size();
    }
}
