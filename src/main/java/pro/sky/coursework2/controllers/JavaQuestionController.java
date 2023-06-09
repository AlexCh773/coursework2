package pro.sky.coursework2.controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.coursework2.exceptions.BadParamsException;
import pro.sky.coursework2.interfaces.QuestionService;
import pro.sky.coursework2.Question;

import java.util.Collection;

@RestController
@RequestMapping("/exam/java")
public class JavaQuestionController {
    private final QuestionService service;

    public JavaQuestionController(@Qualifier("javaQuestionService") QuestionService Service) {
        this.service = Service;
    }

    @GetMapping("/add")
    public Question addQuestion(@RequestParam(required = false) String question, String answer) {
        checkParametersForNull(question, answer);
        return service.add(question.trim(), answer.trim());
    }

    @GetMapping("/remove")
    public Question remove(@RequestParam(required = false) String question, String answer) {
        checkParametersForNull(question, answer);
        return service.remove(new Question(question.trim(), answer.trim()));
    }

    @GetMapping
    public Collection<Question> getQuestions() {
        return service.getAll();
    }
    private void checkParametersForNull(Object... params) {
        for (Object param : params) {
            if (param == null) {
                throw new BadParamsException();
            }
        }
    }
}
