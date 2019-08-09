package by.training.multithreading.bean.state;

import by.training.multithreading.bean.entity.Matrix;

public class CompletelyChangedState implements MatrixState {
    @Override
    public void nextState(final Matrix item) {
        // TODO: required add some code
    }

    @Override
    public void previousState(final Matrix item) {
        item.setState(new NewlyCreatedState());
    }
}
