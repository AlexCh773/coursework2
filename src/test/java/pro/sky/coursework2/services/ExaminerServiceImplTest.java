package pro.sky.coursework2.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.coursework2.Question;
import pro.sky.coursework2.exceptions.BadParamsException;
import pro.sky.coursework2.interfaces.ExaminerService;

import java.util.Collection;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExaminerServiceImplTest {
    private final Question question1 = new Question("вопрос 1", "ответ 1");
    private final Question question2 = new Question("вопрос 2", "ответ 2");
    private final Question question3 = new Question("вопрос 3", "ответ 3");
    private final Question question4 = new Question("вопрос 4", "ответ 4");
    private final Question question5 = new Question("вопрос 5", "ответ 5");
    private final JavaQuestionService javaQuestionServiceMock = mock(JavaQuestionService.class);
    private final MathQuestionService mathQuestionServiceMock = mock(MathQuestionService.class);
    public final ExaminerService out = new ExaminerServiceImpl(javaQuestionServiceMock, mathQuestionServiceMock);

    @Test
    void testGetQuestions() {
        when(javaQuestionServiceMock.getQuestionsAmount()).thenReturn(3);
        when(mathQuestionServiceMock.getQuestionsAmount()).thenReturn(2);
        when(javaQuestionServiceMock.getRandomQuestion()).
                thenReturn(question1).
                thenReturn(question2).
                thenReturn(question2).
                thenReturn(question3);
        when(mathQuestionServiceMock.getRandomQuestion()).
                thenReturn(question4).
                thenReturn(question4).
                thenReturn(question5);
        Collection<Question> result = out.getQuestions(4);
        assertThat(Set.of(question1, question2, question3, question4, question5)).containsAll(result);
        assertThat(result.size()).isEqualTo(4);
    }

    @Test
    void testBadParamsException() {
        when(javaQuestionServiceMock.getQuestionsAmount()).thenReturn(3);
        when(mathQuestionServiceMock.getQuestionsAmount()).thenReturn(2);
        assertThrows(BadParamsException.class, () -> out.getQuestions(6));
    }
}