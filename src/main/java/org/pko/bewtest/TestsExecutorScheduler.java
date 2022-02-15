package org.pko.bewtest;

import org.pko.bewtest.configuration.TestConfigurationData;
import org.pko.bewtest.handler.TestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * TestsProcessor
 *
 * @author Peter Kolarik
 * @date 1.2.2022
 */

@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@Component
public class TestsExecutorScheduler {

    private static final int TEST_EXECUTE_INTERVAL = 60; // in minutes

    private final List<TestHandler<TestConfigurationData>> testHandlersList;

    @Autowired
    public TestsExecutorScheduler(List<TestHandler<TestConfigurationData>> testHandlersList) {
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
    @Scheduled(fixedRate = 60, timeUnit = TimeUnit.MINUTES)
    public void performTestsScheduled() {
        perform();
    }

    public void performTestsRestCall() {
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
        // todo process test results
        handler.configuration().getConfigurations().forEach(configuration -> handler.executor().execute(configuration));
    }

}
