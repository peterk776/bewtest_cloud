package org.pko.bewtest.data;

import java.io.Serializable;

import static org.pko.bewtest.data.TestStatus.RESULT.ERROR;
import static org.pko.bewtest.data.TestStatus.RESULT.OK;

/**
 * TestStatus
 *
 * @author Peter Kolarik
 * @date 26.1.2022
 */
public final class TestStatus implements Serializable {

    enum RESULT  {
        OK,
        ERROR
    };

    private final RESULT result;
    private final String message;

    private TestStatus(RESULT result, String message) {
        this.result = result;
        this.message = message;
    }

    public static TestStatus failedStatus(String message) {
        return new TestStatus(ERROR, message);
    }

    public static TestStatus okStatus() {
        return new TestStatus(OK, "");
    }

    public RESULT getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }
}
