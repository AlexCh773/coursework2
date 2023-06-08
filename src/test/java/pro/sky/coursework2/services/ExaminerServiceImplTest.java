package pro.sky.coursework2.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.coursework2.Question;
import pro.sky.coursework2.interfaces.ExaminerService;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExaminerServiceImplTest {
    private final Question question1 = new Question("вопрос 1", "ответ 1");
    private final Question question2 = new Question("вопрос 2", "ответ 2");
    private final Question question3 = new Question("вопрос 3", "ответ 3");
    private final JavaQuestionService javaQuestionServiceMock = Mockito.mock(JavaQuestionService.class);
    public final ExaminerService out = new ExaminerServiceImpl(javaQuestionServiceMock);

    @Test
    void testGetQuestions() {
        when(javaQuestionServiceMock.getQuestionsAmount()).thenReturn(3);
        when(javaQuestionServiceMock.getRandomQuestion()).thenReturn(question1).thenReturn(question2).thenReturn(question2).thenReturn(question3);
        assertThat(Set.of(question1, question2, question3)).hasSameElementsAs(out.getQuestions(3));
    }
}