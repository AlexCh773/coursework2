package pro.sky.coursework2.services;

import org.springframework.stereotype.Service;
import pro.sky.coursework2.exceptions.BadParamsException;
import pro.sky.coursework2.interfaces.ExaminerService;
import pro.sky.coursework2.Question;

import java.util.Collection;
import java.util.HashSet;

@Service
public class ExaminerServiceImpl implements ExaminerService {
    private final JavaQuestionService javaQuestionService;

    public ExaminerServiceImpl(JavaQuestionService javaQuestionService) {
        this.javaQuestionService = javaQuestionService;
    }

    @Override
    public Collection<Question> getQuestions(int amount) {
        if (amount > javaQuestionService.getQuestionsAmount()) {
            throw new BadParamsException();
        }
        Collection<Question> examQuestions = new HashSet<>();
        while (examQuestions.size() < amount) {
            examQuestions.add(javaQuestionService.getRandomQuestion());
        }
        return examQuestions;
    }
}
