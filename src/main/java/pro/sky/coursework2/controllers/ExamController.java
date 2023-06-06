package pro.sky.coursework2.controllers;

import org.springframework.web.bind.annotation.*;
import pro.sky.coursework2.exceptions.BadParamsException;
import pro.sky.coursework2.services.ExaminerServiceImpl;
import pro.sky.coursework2.Question;

import java.util.Collection;

@RestController
@RequestMapping("/exam")
public class ExamController {
    public final ExaminerServiceImpl examinerService;

    public ExamController(ExaminerServiceImpl examinerService) {
        this.examinerService = examinerService;
    }

    @GetMapping("/get/{amount}")
    public Collection<Question> getQuestions(@PathVariable Integer amount) {
        if (amount == null || amount < 1) {
            throw new BadParamsException();
        }
        return examinerService.getQuestions(amount);
    }
}
