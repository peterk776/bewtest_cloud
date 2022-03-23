package com.piag.uitests.data;

import com.piag.uitests.configuration.TestConfigurationData;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * TestResultResponse
 *
 * @author Peter Kolarik
 * @date 11.2.2022
 */
public class TestResultResponse<T extends TestConfigurationData> implements Serializable {

    private final List<TestResult<T>> results;
    private final LocalDateTime timestamp;

    public TestResultResponse(List<TestResult<T>> results, LocalDateTime timestamp) {
        this.results = results;
        this.timestamp = timestamp;
    }

    public List<TestResult<T>> getResults() {
        return results;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
