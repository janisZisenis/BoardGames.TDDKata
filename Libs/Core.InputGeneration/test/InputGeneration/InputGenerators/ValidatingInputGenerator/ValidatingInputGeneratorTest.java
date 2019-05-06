package InputGeneration.InputGenerators.ValidatingInputGenerator;

import InputGeneration.CountingGeneratorStub;
import InputGeneration.Input.Input;
import InputGeneration.MappingInputAlerter.InputValidatorStub;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatingInputGeneratorTest {

    private CountingGeneratorStub generator = new CountingGeneratorStub();
    private InputValidatorStub validator = new InputValidatorStub();
    private ValidatingInputGenerator sut = new ValidatingInputGenerator(generator, validator);

    private Input[] generated;

    @Test
    void IfFirstUserInputIsValid_ShouldBeTheFirstUserInput() {
        makeFirstUserInputIsValid();

        Input actual = sut.generate();

        Input expected = generated[0];
        assertEquals(expected, actual);
    }

    @Test
    void IfFirstUserInputIsNotValid_ShouldBeTheSecondUserInput() {
        makeFirstInputIsNotValid();

        Input actual = sut.generate();

        Input expected = generated[1];
        assertEquals(expected, actual);
    }

    @Test
    void IfFirstAndSecondUserInputIsNotValid_ShouldBeTheThirdUserInput() {
        makeFirstAndSecondInputIsNotValid();

        Input actual = sut.generate();

        Input expected = generated[2];
        assertEquals(expected, actual);
    }

    private void makeFirstUserInputIsValid() {
        generated = new Input[] { new Input(0, 1) };

        generator.setGeneratedInputs(generated);
        validator.setValidInputs(generated);
    }

    private void makeFirstInputIsNotValid() {
        generated = new Input[] { new Input(0, 1),
                                  new Input(1, 2) };

        Input[] valid = { new Input(1, 2) };

        generator.setGeneratedInputs(generated);
        validator.setValidInputs(valid);
    }

    private void makeFirstAndSecondInputIsNotValid() {
        generated = new Input[] { new Input(0, 1),
                                  new Input(1, 2),
                                  new Input(2, 3) };

        Input[] valid = { new Input(2, 3) };

        generator.setGeneratedInputs(generated);
        validator.setValidInputs(valid);
    }


}