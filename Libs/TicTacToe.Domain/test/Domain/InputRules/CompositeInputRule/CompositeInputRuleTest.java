package Domain.InputRules.CompositeInputRule;

import Domain.Input.Input;
import Domain.RuleChoosingInputAlerter.InputRuleStub;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CompositeInputRuleTest {

    private CompositeInputRule sut = new CompositeInputRule();
    private Input input = new Input(0 ,1);

    @Test
    void FreshInstance_InputShouldBeValid() {
        boolean actual = sut.isValid(input);

        assertTrue(actual);
    }

    @Test
    void AddedInvalidatingRule_InputShouldBeInvalid() {
        makeInvalidatingRuleAdded();

        boolean actual = sut.isValid(input);

        assertFalse(actual);
    }

    @Test
    void AddedValidatingRule_InputShouldBeInvalid() {
        makeValidatingRuleAdded();

        boolean actual = sut.isValid(input);

        assertTrue(actual);
    }

    @Test
    void AddedValidatingRuleAfterInvalidatingRule_InputShouldBeFalse() {
        makeInvalidatingRuleAdded();
        makeValidatingRuleAdded();

        boolean actual = sut.isValid(input);

        assertFalse(actual);
    }

    @Test
    void AddedInvalidatingRuleAfterValidatingRule_InputShouldBeFalse() {
        makeValidatingRuleAdded();
        makeInvalidatingRuleAdded();

        boolean actual = sut.isValid(input);

        assertFalse(actual);
    }

    @Test
    void AddedInvalidatingRuleInBetweenTwoValidatingRules_InputShouldBeFalse() {
        makeValidatingRuleAdded();
        makeInvalidatingRuleAdded();
        makeValidatingRuleAdded();

        boolean actual = sut.isValid(input);

        assertFalse(actual);
    }

    @Test
    void AddedInvalidatingRuleAfterTwoValidatingRules_InputShouldBeFalse() {
        makeValidatingRuleAdded();
        makeValidatingRuleAdded();
        makeInvalidatingRuleAdded();

        boolean actual = sut.isValid(input);

        assertFalse(actual);
    }


    private void makeValidatingRuleAdded() {
        InputRuleStub rule = new InputRuleStub();
        Input[] valid = { input };
        rule.setValidInputs(valid);
        sut.add(rule);
    }

    private void makeInvalidatingRuleAdded() {
        InputRuleStub rule = new InputRuleStub();
        Input[] valid = {};
        rule.setValidInputs(valid);
        sut.add(rule);
    }

}
