package by.training.multithreading.bean.state;

import by.training.multithreading.bean.entity.Matrix;

public interface MatrixState {
    void nextState(Matrix item);
    void previousState(Matrix item);
}
