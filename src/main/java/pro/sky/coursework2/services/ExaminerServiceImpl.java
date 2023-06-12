package pro.sky.coursework2.services;

import org.springframework.stereotype.Service;
import pro.sky.coursework2.exceptions.BadParamsException;
import pro.sky.coursework2.interfaces.ExaminerService;
import pro.sky.coursework2.Question;
import pro.sky.coursework2.interfaces.QuestionService;

import java.util.*;

@Service
public class ExaminerServiceImpl implements ExaminerService {
    private final Map<String, QuestionService> questionServicesMap= new HashMap<>();
    private  final Random random = new Random();
    public ExaminerServiceImpl(JavaQuestionService javaQuestionService, MathQuestionService mathQuestionService) {
        questionServicesMap.put("java", javaQuestionService);
        questionServicesMap.put("math", mathQuestionService);
    }

    @Override
    public Collection<Question> getQuestions(int amount) {
        int allJavaQuestionsAmount = questionServicesMap.get("java").getQuestionsAmount();
        int allMathQuestionsAmount = questionServicesMap.get("math").getQuestionsAmount();
        if (amount > allJavaQuestionsAmount + allMathQuestionsAmount) {
            throw new BadParamsException();
        }
        int examJavaQuestionsAmount;
        int examMathQuestionsAmount;
        examJavaQuestionsAmount = random.nextInt(Math.min(amount, allJavaQuestionsAmount) + 1);
        examMathQuestionsAmount = Math.min((amount - examJavaQuestionsAmount), allMathQuestionsAmount);
        if (examJavaQuestionsAmount + examMathQuestionsAmount < amount) {
            examJavaQuestionsAmount = amount - examMathQuestionsAmount;

        }
        Collection<Question> examQuestions = new HashSet<>();
        while (examQuestions.size() < examJavaQuestionsAmount) {
            examQuestions.add(questionServicesMap.get("java").getRandomQuestion());
        }
        while (examQuestions.size() < examMathQuestionsAmount + examJavaQuestionsAmount) {
            examQuestions.add(questionServicesMap.get("math").getRandomQuestion());
        }
        return examQuestions;
    }
}
