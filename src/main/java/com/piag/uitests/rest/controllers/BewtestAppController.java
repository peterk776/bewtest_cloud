package com.piag.uitests.rest.controllers;

import com.google.common.collect.Lists;
import com.google.gson.GsonBuilder;
import com.piag.uitests.TestsExecutorScheduler;
import com.piag.uitests.data.BewerberTestResult;
import com.piag.uitests.data.TestResult;
import com.piag.uitests.data.TestResultResponse;
import com.piag.uitests.handler.BewerberTestHandler;
import com.piag.uitests.process.TestResultProcessor;
import com.piag.uitests.testcases.PositionTest;
import com.piag.uitests.configuration.bewerber.BewerberTestConfigurationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * BewtestAppController
 * REST entrypoints
 * todo authentication
 *
 * @author Peter Kolarik
 * @date 25.1.2022
 */

@RestController
public class BewtestAppController {

    // some defaults (test only)
    private static final String SELENIUM_BROWSER_HOST = "selenium-chrome";
    private static final String CANDIDATE_HOST = "loga-22-2.pi-asp.de";
    private static final String COMPANY_EID = "ALL";
    private static final String POSITION_ID = "91a6f718-8a2e-4b08-bf52-37f5e3bd029a";

    @Autowired
    BewerberTestHandler bewerberTestHandler;
    @Autowired
    TestResultProcessor<BewerberTestConfigurationData> resultProcessor;


    @GetMapping("/")
    String hello() {
        return "Hi!";
    }

    @Deprecated
    @RequestMapping("/checkSinglePositionCloud")
    public ResponseEntity<TestResult<BewerberTestConfigurationData>> checkSinglePositionCloud(@RequestParam("remoteDriverUrl") String remoteDriverUrl, @RequestParam("candidateHost") String candidateHost,
                                                                                              @RequestParam("companyEid") String companyEid, @RequestParam("positionId") String positionId) {

        candidateHost = candidateHost == null ? CANDIDATE_HOST : candidateHost;
        companyEid = companyEid == null ? COMPANY_EID : companyEid;
        positionId = positionId == null ? POSITION_ID : positionId;

        String appUrl = "https://" + candidateHost +"/bewerber-web/?companyEid=" + companyEid + "#position,id=" + positionId;

        return ResponseEntity.ok(PositionTest.execute(remoteDriverUrl, appUrl));
    }

    @Deprecated
    @RequestMapping("/checkSinglePosition")
    public ResponseEntity<TestResult<BewerberTestConfigurationData>> checkSinglePosition(@RequestParam("browserHost") String browserHost, @RequestParam("candidateHost") String candidateHost,
                                                                  @RequestParam("companyEid") String companyEid, @RequestParam("positionId") String positionId) {

        browserHost = browserHost == null ? SELENIUM_BROWSER_HOST : browserHost;
        candidateHost = candidateHost == null ? CANDIDATE_HOST : candidateHost;
        companyEid = companyEid == null ? COMPANY_EID : companyEid;
        positionId = positionId == null ? POSITION_ID : positionId;

        String appUrl = "https://" + candidateHost +"/bewerber-web/?companyEid=" + companyEid + "#position,id=" + positionId;
        String remoteWebDriverUrl = "http://" + browserHost + ":4444/wd/hub/";

        return ResponseEntity.ok(PositionTest.execute(remoteWebDriverUrl, appUrl));
    }

    @RequestMapping("/addBewConfig")
    public ResponseEntity<BewerberTestConfigurationData> addConfiguration(@RequestBody String json) {
        BewerberTestConfigurationData added = bewerberTestHandler.configuration().addConfiguration(json);
        return (added == null) ? ResponseEntity.badRequest().build() : ResponseEntity.ok(added);
    }

    @RequestMapping("/removeBewConfig")
    public ResponseEntity<BewerberTestConfigurationData> removeConfiguration(@RequestParam("endpointUrl") String endpointUrl, @RequestParam("companyEid") String companyEid,
                                                                             @RequestParam("positionId") String positionId) {
        BewerberTestConfigurationData removed = bewerberTestHandler.configuration().removeConfiguration(endpointUrl, companyEid, positionId);
        return (removed == null) ? ResponseEntity.badRequest().build() : ResponseEntity.ok(removed);
    }

    @RequestMapping("/getBewConfig")
    public ResponseEntity<String> getConfiguration() {
        return ResponseEntity.ok(new GsonBuilder().create().toJson(bewerberTestHandler.configuration().getConfigurations()));
    }

    @RequestMapping("/executeBewerberTests")
    public ResponseEntity<TestResultResponse<BewerberTestConfigurationData>> executeBewerberTests() {
        if (bewerberTestHandler.configuration().getConfigurations().isEmpty())
            return ResponseEntity.ok(new TestResultResponse<>(Lists.newArrayList(new BewerberTestResult<>(null, TestResult.State.ERROR, "no configuration")), LocalDateTime.now()));

        List<TestResult<BewerberTestConfigurationData>> list =
                bewerberTestHandler.configuration().getConfigurations()
                        .stream()
                        .map(data -> bewerberTestHandler.executor().execute(data))
                        .collect(Collectors.toList());

        final TestResultResponse<BewerberTestConfigurationData> body = new TestResultResponse<>(list, LocalDateTime.now());

        resultProcessor.process(body);

        return ResponseEntity.ok(body);
    }


}
