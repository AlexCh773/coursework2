package pro.sky.coursework2.services;

import org.springframework.stereotype.Service;
import pro.sky.coursework2.Question;
import pro.sky.coursework2.exceptions.BadParamsException;
//import pro.sky.coursework2.exceptions.NoContentException;
import pro.sky.coursework2.interfaces.Operationable;
import pro.sky.coursework2.interfaces.QuestionService;
import pro.sky.coursework2.repository.MathQuestionRepository;

import java.util.*;

@Service
public class MathQuestionService implements QuestionService {
    private final MathQuestionRepository mathQuestionRepository;
    private final Random random = new Random();
    private final String[] operationKeys = {"+", "-", "*", "/"};
    private final Map<String, Operationable> operationsMap = new HashMap<>();
    {
        operationsMap.put("+", (x, y) -> x + y);
        operationsMap.put("-", (x, y) -> x - y);
        operationsMap.put("*", (x, y) -> x * y);
        operationsMap.put("/", (x, y) -> x / y);
    }

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

   /* @Override
    public Question getRandomQuestion() {
        List<Question> allQuestions = List.copyOf(mathQuestionRepository.getAll());
        if (allQuestions.isEmpty()) {
            throw new NoContentException();
        }
        int randomIndex = random.nextInt(allQuestions.size());
        return allQuestions.get(randomIndex);
    }*/

    @Override
    public Question getRandomQuestion() {
        String operationKey = operationKeys[random.nextInt(operationKeys.length)];
        Operationable operation = operationsMap.get(operationKey);
        int firstRandomNumber = random.nextInt(100);
        int secondRandomNumber = random.nextInt(100);
        operation.calculate(firstRandomNumber, secondRandomNumber);
        return new Question("сколько будет " + firstRandomNumber + " " + operationKey + " " + secondRandomNumber, " " + operation.calculate(firstRandomNumber, secondRandomNumber));
    }

    public int getQuestionsAmount() {
        return mathQuestionRepository.getAll().size();
    }
}
