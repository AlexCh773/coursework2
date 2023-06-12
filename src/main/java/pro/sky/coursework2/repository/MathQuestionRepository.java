package pro.sky.coursework2.repository;

import org.springframework.stereotype.Repository;
import pro.sky.coursework2.Question;
import pro.sky.coursework2.exceptions.ElementNotFoundException;
import pro.sky.coursework2.interfaces.QuestionRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Repository
public class MathQuestionRepository implements QuestionRepository {
    private final Set<Question> questions;

    public MathQuestionRepository(Set<Question> questions) {
        this.questions = questions;
    }

    @PostConstruct
    private void init() {
        questions.add(new Question("вопрос по math #1", "ответ по math #1"));
        questions.add(new Question("вопрос по math #2", "ответ по math #2"));
        questions.add(new Question("вопрос по math #3", "ответ по math #3"));
        questions.add(new Question("вопрос по math #4", "ответ по math #4"));
    }
    @Override
    public Question add(Question question) {
        questions.add(question);
        return question;
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
        return new ArrayList<>(questions);
    }
}
