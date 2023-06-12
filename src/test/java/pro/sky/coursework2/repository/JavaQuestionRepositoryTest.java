package pro.sky.coursework2.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pro.sky.coursework2.Question;
import pro.sky.coursework2.exceptions.ElementNotFoundException;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class JavaQuestionRepositoryTest {

    public final JavaQuestionRepository out = new JavaQuestionRepository(new HashSet<>());
    public final static Question question1 = new Question("вопрос 1", "ответ 1");
    public final static Question question2 = new Question("вопрос 2", "ответ 2");

    @AfterEach
    void afterAll() {
        for (Question question : out.getAll()) {
            out.remove(question);
        }
    }

    public static Stream<Arguments> provideParamsForTestAdd() {
        Question[] questionForPrefilling1 = {};
        Set<Question> expectedSet1 = Set.of(question1);
        Question[] questionForPrefilling2 = {question1};
        Set<Question> expectedSet2 = Set.of(question1, question2);
        return Stream.of(
                Arguments.of(questionForPrefilling1, question1, expectedSet1),
                Arguments.of(questionForPrefilling2, question2, expectedSet2));
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTestAdd")
    void testAdd(Question[] questionForPrefilling, Question expectedQuestion, Set<Question> expectedSet) {
        for (Question s : questionForPrefilling) {
            out.add(s);
        }
        assertEquals(expectedQuestion, out.add(expectedQuestion));
        assertThat(expectedSet).hasSameElementsAs(out.getAll());
    }

    public static Stream<Arguments> provideParamsForTestRemove() {
        Question[] questionForPrefilling1 = {question1};
        Set<Question> expectedSet1 = Set.of();
        Question[] questionForPrefilling2 = {question1, question2};
        Set<Question> expectedSet2 = Set.of(question1);
        return Stream.of(
                Arguments.of(questionForPrefilling1, question1, expectedSet1),
                Arguments.of(questionForPrefilling2, question2, expectedSet2));
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTestRemove")
    void testRemove(Question[] questionForPrefilling, Question expectedQuestion, Set<Question> expectedSet) {
        for (Question s : questionForPrefilling) {
            out.add(s);
        }
        assertEquals(expectedQuestion, out.remove(expectedQuestion));
        assertThat(expectedSet).hasSameElementsAs(out.getAll());
    }

    public static Stream<Arguments> provideParamsForTestGetAll() {
        Question[] questionForPrefilling1 = {};
        Set<Question> expectedSet1 = Set.of();
        Question[] questionForPrefilling2 = {question1, question2};
        Set<Question> expectedSet2 = Set.of(question1, question2);
        return Stream.of(
                Arguments.of(questionForPrefilling1, expectedSet1),
                Arguments.of(questionForPrefilling2, expectedSet2));
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTestGetAll")
    void testGetAll(Question[] questionForPrefilling, Set<Question> expectedSet) {
        for (Question s : questionForPrefilling) {
            out.add(s);
        }
        assertThat(expectedSet).hasSameElementsAs(out.getAll());
    }

    @Test
    void testElementNotFoundException() {
        out.add(question1);
        assertThrows(ElementNotFoundException.class, () -> out.remove(question2));
    }
}