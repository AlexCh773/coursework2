package pro.sky.coursework2.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.coursework2.exceptions.BadParamsException;
import pro.sky.coursework2.services.JavaQuestionService;
import pro.sky.coursework2.Question;

import java.util.Collection;

@RestController
@RequestMapping("/exam/java")
public class JavaQuestionController {
    private final JavaQuestionService javaQuestionService;

    public JavaQuestionController(JavaQuestionService javaQuestionService) {
        this.javaQuestionService = javaQuestionService;
    }

    @GetMapping("/add")
    public Question addQuestion(@RequestParam(required = false) String question, String answer) {
        checkParametersForNull(question, answer);
        return javaQuestionService.questionAdd(question, answer);
    }

    @GetMapping("/remove")
    public Question remove(@RequestParam(required = false) String question, String answer) {
        checkParametersForNull(question, answer);
        return javaQuestionService.remove(new Question(question, answer));
    }

    @GetMapping
    public Collection<Question> getQuestions() {
        return javaQuestionService.getAll();
    }
    private void checkParametersForNull(Object... params) {
        for (Object param : params) {
            if (param == null) {
                throw new BadParamsException();
            }
        }
    }
}
