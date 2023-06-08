package pro.sky.coursework2.interfaces;

import pro.sky.coursework2.Question;

import java.util.Collection;

public interface QuestionRepository {

    Question add(Question question);

    Question remove(Question question);

    Collection<Question> getAll();
}
