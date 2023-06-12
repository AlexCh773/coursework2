package pro.sky.coursework2.services;

import org.springframework.stereotype.Service;
import pro.sky.coursework2.Question;
import pro.sky.coursework2.exceptions.BadParamsException;
import pro.sky.coursework2.exceptions.NoContentException;
import pro.sky.coursework2.interfaces.QuestionService;
import pro.sky.coursework2.repository.MathQuestionRepository;

import java.util.Collection;
import java.util.List;
import java.util.Random;

@Service
public class MathQuestionService implements QuestionService {
    private final MathQuestionRepository mathQuestionRepository;

    public MathQuestionService(MathQuestionRepository mathQuestionRepository) {
        this.mathQuestionRepository = mathQuestionRepository;
    }

    @Override
    public Question add(Question question) {
        return mathQuestionRepository.add(question);
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
        return mathQuestionRepository.remove(question);
    }

    @Override
    public Collection<Question> getAll() {
        return mathQuestionRepository.getAll();
    }

    @Override
    public Question getRandomQuestion() {
        List<Question> allQuestions = List.copyOf(mathQuestionRepository.getAll());
        if (allQuestions.isEmpty()) {
            throw new NoContentException();
        }
        Random random = new Random();
        int randomIndex = random.nextInt(allQuestions.size());
        return allQuestions.get(randomIndex);
    }

    public int getQuestionsAmount() {
        return mathQuestionRepository.getAll().size();
    }
}
