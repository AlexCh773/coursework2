package pro.sky.coursework2.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pro.sky.coursework2.Question;
import pro.sky.coursework2.exceptions.BadParamsException;
import pro.sky.coursework2.exceptions.ElementNotFoundException;
import pro.sky.coursework2.exceptions.NoContentException;
import pro.sky.coursework2.services.JavaQuestionService;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class JavaQuestionControllerTest {

    public final JavaQuestionService out = new JavaQuestionService(new HashSet<>());
    public final static String question1 = "вопрос 1";
    public final static String question2 = "вопрос 2";
    public final static String answer1 = "ответ 1";
    public final static String answer2 = "ответ 2";

    @AfterEach
    void afterAll() {
        for (Question question : out.getAll()) {
            out.remove(question);
        }
    }

    public static Stream<Arguments> provideParamsForTestAddQuestion() {
        Question[] questionForPrefilling1 = {};
        Question expectedQuestion1 = new Question(question1, answer1);
        Set<Question> expectedSet1 = new HashSet<>();
        expectedSet1.add(expectedQuestion1);
        Question[] questionForPrefilling2 = {new Question(question1, answer1)};
        Question expectedQuestion2 = new Question(question2, answer2);
        Set<Question> expectedSet2 = new HashSet<>();
        expectedSet2.add(expectedQuestion1);
        expectedSet2.add(expectedQuestion2);
        return Stream.of(
                Arguments.of(questionForPrefilling1, question1, answer1, expectedQuestion1, expectedSet1),
                Arguments.of(questionForPrefilling2, question2, answer2, expectedQuestion2, expectedSet2));
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTestAddQuestion")
    void testAddQuestion(Question[] questionForPrefilling, String question, String answer, Question expectedQuestion, Set<Question> expectedSet) {
        for (Question s : questionForPrefilling) {
            out.questionAdd(s);
        }
        assertEquals(expectedQuestion, out.questionAdd(question, answer));
        assertIterableEquals(expectedSet, out.getAll());
    }

    public static Stream<Arguments> provideParamsForTestRemove() {
        Question[] questionForPrefilling1 = {new Question(question1, answer1)};
        Question expectedQuestion1 = new Question(question1, answer1);
        Set<Question> expectedSet1 = new HashSet<>();
        Question expectedQuestion2 = new Question(question2, answer2);
        Question[] questionForPrefilling2 = {expectedQuestion1, expectedQuestion2};
        Set<Question> expectedSet2 = new HashSet<>();
        expectedSet2.add(expectedQuestion1);
        return Stream.of(
                Arguments.of(questionForPrefilling1, expectedQuestion1, expectedSet1),
                Arguments.of(questionForPrefilling2, expectedQuestion2, expectedSet2));
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTestRemove")
    void testRemove(Question[] questionForPrefilling, Question expectedQuestion, Set<Question> expectedSet) {
        for (Question s : questionForPrefilling) {
            out.questionAdd(s);
        }
        assertEquals(expectedQuestion, out.remove(expectedQuestion));
        assertIterableEquals(expectedSet, out.getAll());
    }

    public static Stream<Arguments> provideParamsForTestGetQuestions() {
        Question[] questionForPrefilling1 = {};
        Set<Question> expectedSet1 = new HashSet<>();
        Question[] questionForPrefilling2 = {new Question(question1, answer1), new Question(question2, answer2)};
        Set<Question> expectedSet2 = new HashSet<>();
        expectedSet2.add(new Question(question1, answer1));
        expectedSet2.add(new Question(question2, answer2));
        return Stream.of(
                Arguments.of(questionForPrefilling1, expectedSet1),
                Arguments.of(questionForPrefilling2, expectedSet2));
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTestGetQuestions")
    void testGetQuestions(Question[] questionForPrefilling, Set<Question> expectedSet) {
        for (Question s : questionForPrefilling) {
            out.questionAdd(s);
        }
        assertIterableEquals(expectedSet, out.getAll());
    }

    @Test
    void testGetRandomQuestion() {
        out.questionAdd(question1, answer1);
        out.questionAdd(question2, answer2);
        Set<Question> questionSet = Set.of(new Question(question1, answer2), new Question(question2, answer2));
        assertTrue(questionSet.contains(out.getRandomQuestion()));
    }

    @Test
    void testBadParamsException() {
        assertThrows(BadParamsException.class, () -> out.questionAdd(question1, question1));
    }

    @Test
    void testElementNotFoundException() {
        out.questionAdd(question1, answer1);
        assertThrows(ElementNotFoundException.class, () -> out.remove(new Question(question2, answer2)));
    }

    @Test
    void testNoContentException() {
        assertThrows(NoContentException.class, out::getRandomQuestion);
    }
}