package by.training.multithreading.bean.state;

import by.training.multithreading.bean.entity.Matrix;

public class NewlyCreatedState implements MatrixState {
    @Override
    public void nextState(final Matrix item) {
        item.setState(new CompletelyChangedState());
    }

    @Override
    public void previousState(final Matrix item) {
        // TODO: required add some code
    }
}
