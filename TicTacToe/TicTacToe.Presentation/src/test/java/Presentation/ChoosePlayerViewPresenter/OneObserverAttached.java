package Presentation.ChoosePlayerViewPresenter;

import Utilities.Observer.ObserverSpy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OneObserverAttached {

    private ChoosePlayerViewDummy view = new ChoosePlayerViewDummy();
    private ChoosePlayerViewPresenter sut = new ChoosePlayerViewPresenter(view);
    private ObserverSpy observer = new ObserverSpy();

    @BeforeEach
    void setUp() {
        sut.attach(observer);
    }

    @Test
    void IfHumanGetsClicked_ShouldUpdateObserver() {
        sut.onHumanClicked();

        assertWasUpdated();
    }

    @Test
    void IfHumbleGetsClicked_ShouldUpdateObserver() {
        sut.onHumbleCPUClicked();

        assertWasUpdated();
    }

    @Test
    void IfInvincibleGetsClicked_ShouldUpdateObserver() {
        sut.onInvincibleCPUClicked();

        assertWasUpdated();
    }

    private void assertWasUpdated() {
        boolean actual = observer.wasUpdated();
        assertTrue(actual);
    }
}
