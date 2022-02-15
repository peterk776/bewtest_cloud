package org.pko.bewtest;

import org.pko.bewtest.configuration.TestConfigurationData;
import org.pko.bewtest.data.TestResult;
import org.pko.bewtest.data.TestResultResponse;
import org.pko.bewtest.handler.TestHandler;
import org.pko.bewtest.process.TestResultProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * TestsProcessor
 *
 * @author Peter Kolarik
 * @date 1.2.2022
 */

@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@Component
public class TestsExecutorScheduler<T extends TestConfigurationData> {

    private static final int TEST_EXECUTE_INTERVAL = 60; // in minutes

    private final List<TestHandler<?>> testHandlersList;

    @Autowired
    TestResultProcessor<T> resultProcessor;

    @Autowired
    public <T extends TestConfigurationData> TestsExecutorScheduler(List<TestHandler<?>> testHandlersList) {
        this.testHandlersList = testHandlersList;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(TestsExecutorScheduler.class);

    /**
     * Use lock to avoid concurrent execution from scheduler and rest service calls
     */
    private final ReentrantLock lock = new ReentrantLock();

    /**
     * Called every 60 minutes, with wait for previous processing
     */
    @Scheduled(fixedRate = TEST_EXECUTE_INTERVAL, initialDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void performTestsScheduled() {
        perform();
    }

    private void perform() {
        if (lock.tryLock()) {
            LOGGER.info("test scheduler starts...");
            try {
                testHandlersList
                        .forEach(this::process); // for each handler
            } finally {
                lock.unlock();
                LOGGER.info("test scheduler ends...");
            }
        }
        else {
            LOGGER.info("Tried to perform tests, but previous execution has not been yet finished.");
        }
    }

    private <T extends TestConfigurationData> void process(TestHandler<T> handler) {
        List<TestResult<T>> list = handler.configuration().getConfigurations()
                .stream()
                .map(data -> handler.executor().execute(data))
                .collect(Collectors.toList());

        final TestResultResponse<T> body = new TestResultResponse<>(list, LocalDateTime.now());
        resultProcessor.process(body);
    }

}
