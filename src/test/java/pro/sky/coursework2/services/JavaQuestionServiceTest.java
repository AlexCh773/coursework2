package pro.sky.coursework2.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import org.mockito.junit.jupiter.MockitoExtension;

import pro.sky.coursework2.Question;
import pro.sky.coursework2.exceptions.BadParamsException;
import pro.sky.coursework2.exceptions.NoContentException;
import pro.sky.coursework2.repository.JavaQuestionRepository;

import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JavaQuestionServiceTest {

    public final JavaQuestionRepository javaQuestionRepositoryMock = mock(JavaQuestionRepository.class);
    public final JavaQuestionService out = new JavaQuestionService(javaQuestionRepositoryMock);
    public final static String question1 = "вопрос 1";
    public final static String question2 = "вопрос 2";
    public final static String answer1 = "ответ 1";
    public final static String answer2 = "ответ 2";

    public static Stream<Arguments> provideParamsForTestAdd() {
        Question[] questionForPrefilling1 = {};
        Question expectedQuestion1 = new Question(question1, answer1);
        Question[] questionForPrefilling2 = {new Question(question1, answer1)};
        Question expectedQuestion2 = new Question(question2, answer2);
        return Stream.of(
                Arguments.of(questionForPrefilling1, question1, answer1, expectedQuestion1),
                Arguments.of(questionForPrefilling2, question2, answer2, expectedQuestion2));
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTestAdd")
    void testAdd(Question[] questionForPrefilling, String question, String answer, Question expectedQuestion) {
        when(javaQuestionRepositoryMock.add(any())).thenReturn(expectedQuestion);
        for (Question s : questionForPrefilling) {
            out.add(s);
        }
        assertEquals(expectedQuestion, out.add(question, answer));
    }

    public static Stream<Arguments> provideParamsForTestRemove() {
        Question expectedQuestion1 = new Question(question1, answer1);
        Question[] questionForPrefilling1 = {expectedQuestion1};
        Question expectedQuestion2 = new Question(question2, answer2);
        Question[] questionForPrefilling2 = {expectedQuestion1, expectedQuestion2};
        return Stream.of(
                Arguments.of(questionForPrefilling1, expectedQuestion1),
                Arguments.of(questionForPrefilling2, expectedQuestion2));
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTestRemove")
    void testRemove(Question[] questionForPrefilling, Question expectedQuestion) {
        when(javaQuestionRepositoryMock.remove(any())).thenReturn(expectedQuestion);
        for (Question s : questionForPrefilling) {
            out.add(s);
        }
        assertEquals(expectedQuestion, out.remove(expectedQuestion));
    }

    public static Stream<Arguments> provideParamsForTestGetQuestions() {
        Question[] questionForPrefilling1 = {};
        Set<Question> expectedSet1 = Set.of();
        Question[] questionForPrefilling2 = {new Question(question1, answer1), new Question(question2, answer2)};
        Set<Question> expectedSet2 = Set.of(new Question(question1, answer1), new Question(question2, answer2));
        return Stream.of(
                Arguments.of(questionForPrefilling1, expectedSet1),
                Arguments.of(questionForPrefilling2, expectedSet2));
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTestGetQuestions")
    void testGetQuestions(Question[] questionForPrefilling, Set<Question> expectedSet) {
        when(javaQuestionRepositoryMock.getAll()).thenReturn(expectedSet);
        for (Question s : questionForPrefilling) {
            out.add(s);
        }
        assertThat(expectedSet).hasSameElementsAs(out.getAll());
    }

    @Test
    void testGetRandomQuestion() {
        out.add(question1, answer1);
        out.add(question2, answer2);
        Set<Question> questionSet = Set.of(new Question(question1, answer2), new Question(question2, answer2));
        when(javaQuestionRepositoryMock.getAll()).thenReturn(questionSet);
        assertTrue(questionSet.contains(out.getRandomQuestion()));
    }

    @Test
    void testBadParamsException() {
        assertThrows(BadParamsException.class, () -> out.add(question1, question1));
    }

    @Test
    void testNoContentException() {
        assertThrows(NoContentException.class, out::getRandomQuestion);
    }
}