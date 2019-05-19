package InteractiveGaming.GameOverInputProcessor;

import Gaming.GameFacade.GameOverRuleStub;
import Input2D.Input.Input;
import Input2D.InputProcessorSpy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class GameOverIsOver {

    private InputProcessorSpy processor = new InputProcessorSpy();
    private GameOverRuleStub rule = new GameOverRuleStub();
    private GameOverInputProcessor sut = new GameOverInputProcessor(processor, rule);

    @BeforeEach
    void SetUp() {
        rule.setIsGameOver(true);
    }

    @Test
    void ShouldNotProcessTheInput() {
        Input input = new Input(0, 0);

        sut.process(input);

        assertHasNotProcessedInput();
    }

    private void assertHasNotProcessedInput() {
        boolean actual = processor.hasProcessed();
        assertFalse(actual);
    }
}