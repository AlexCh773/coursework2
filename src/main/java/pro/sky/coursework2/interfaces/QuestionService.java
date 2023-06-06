package pro.sky.coursework2.interfaces;

import pro.sky.coursework2.Question;

import java.util.Collection;

public interface QuestionService {

    Question questionAdd(Question question);

    Question questionAdd(String question, String answer);

    Question remove(Question question);

    Collection<Question> getAll();

    Question getRandomQuestion();
}
