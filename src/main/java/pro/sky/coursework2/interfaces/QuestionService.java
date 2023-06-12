package pro.sky.coursework2.interfaces;

import pro.sky.coursework2.Question;

import java.util.Collection;

public interface QuestionService {

    Question add(Question question);

    Question add(String question, String answer);

    Question remove(Question question);

    Collection<Question> getAll();

    Question getRandomQuestion();

    int getQuestionsAmount();
}
