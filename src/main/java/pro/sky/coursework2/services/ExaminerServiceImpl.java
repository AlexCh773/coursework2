package pro.sky.coursework2.services;

import org.springframework.stereotype.Service;
import pro.sky.coursework2.exceptions.BadParamsException;
import pro.sky.coursework2.interfaces.ExaminerService;
import pro.sky.coursework2.Question;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

@Service
public class ExaminerServiceImpl implements ExaminerService {
    private final JavaQuestionService javaQuestionService;
    private final MathQuestionService mathQuestionService;

    public ExaminerServiceImpl(JavaQuestionService javaQuestionService, MathQuestionService mathQuestionService) {
        this.javaQuestionService = javaQuestionService;
        this.mathQuestionService = mathQuestionService;
    }

    @Override
    public Collection<Question> getQuestions(int amount) {
        int allJavaQuestionsAmount = javaQuestionService.getQuestionsAmount();
        int allMathQuestionsAmount = mathQuestionService.getQuestionsAmount();
        if (amount > allJavaQuestionsAmount + allMathQuestionsAmount) {
            throw new BadParamsException();
        }
        Random random = new Random();
        int examJavaQuestionsAmount;
        int examMathQuestionsAmount;
        examJavaQuestionsAmount = random.nextInt(Math.min(amount, allJavaQuestionsAmount) + 1);
        examMathQuestionsAmount = Math.min((amount - examJavaQuestionsAmount), allMathQuestionsAmount);
        if (examJavaQuestionsAmount + examMathQuestionsAmount < amount) {
            examJavaQuestionsAmount = amount - examMathQuestionsAmount;

        }
        Collection<Question> examJavaQuestions = new HashSet<>();
        while (examJavaQuestions.size() < examJavaQuestionsAmount) {
            examJavaQuestions.add(javaQuestionService.getRandomQuestion());
        }
        Collection<Question> examMathQuestions = new HashSet<>();
        while (examMathQuestions.size() < examMathQuestionsAmount) {
            examMathQuestions.add(mathQuestionService.getRandomQuestion());
        }
        Collection<Question> examQuestions = new HashSet<>();
        examQuestions.addAll(examJavaQuestions);
        examQuestions.addAll(examMathQuestions);
        return examQuestions;
    }
}
