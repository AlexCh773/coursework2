package pro.sky.coursework2.services;

import org.springframework.stereotype.Service;
import pro.sky.coursework2.exceptions.BadParamsException;
import pro.sky.coursework2.exceptions.NoContentException;
import pro.sky.coursework2.Question;
import pro.sky.coursework2.interfaces.QuestionService;
import pro.sky.coursework2.repository.JavaQuestionRepository;

import java.util.*;

@Service
public class JavaQuestionService implements QuestionService {

    private final JavaQuestionRepository javaQuestionRepository;
    private final Random random = new Random();

    public JavaQuestionService(JavaQuestionRepository javaQuestionRepository) {
        this.javaQuestionRepository = javaQuestionRepository;
    }

    @Override
    public Question add(Question question) {
        return javaQuestionRepository.add(question);
    }

    @Override
    public Question add(String question, String answer) {
        if (question.equals(answer)) {
            throw new BadParamsException();
        }
        return add(new Question(question, answer));
    }

    @Override
    public Question remove(Question question) {
        return javaQuestionRepository.remove(question);
    }

    @Override
    public Collection<Question> getAll() {
        return javaQuestionRepository.getAll();
    }

    @Override
    public Question getRandomQuestion() {
        List<Question> allQuestions = List.copyOf(javaQuestionRepository.getAll());
        if (allQuestions.isEmpty()) {
            throw new NoContentException();
        }
        int randomIndex = random.nextInt(allQuestions.size());
        return allQuestions.get(randomIndex);
    }

    public int getQuestionsAmount() {
        return javaQuestionRepository.getAll().size();
    }
}
