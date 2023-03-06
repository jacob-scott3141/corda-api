package net.corda.v5.application.messaging;

public interface NewInterface {
    void someFunction1();

    default int someFunction2() {
        return 42;
    }
}
