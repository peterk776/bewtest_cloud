package org.pko.bewtest.controllers;

import org.pko.bewtest.testcases.PositionTest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * BewtestAppController
 *
 * @author Peter Kolarik
 * @date 25.1.2022
 */

@RestController
public class BewtestAppController {

    // some defaults
    private static final String SELENIUM_BROWSER_HOST = "selenium-chrome";
    private static final String CANDIDATE_HOST = "loga-22-2.pi-asp.de";
    private static final String COMPANY_EID = "ALL";
    private static final String POSITION_ID = "91a6f718-8a2e-4b08-bf52-37f5e3bd029a";



    @GetMapping("/")
    String hello() {
        return "Hi!";
    }

    @RequestMapping("/checkSinglePosition")
    String checkSinglePosition(@RequestParam("browserHost") String browserHost, @RequestParam("candidateHost") String candidateHost,
                               @RequestParam("companyEid") String companyEid, @RequestParam("positionId") String positionId) {

        browserHost = browserHost == null ? SELENIUM_BROWSER_HOST : browserHost;
        candidateHost = candidateHost == null ? CANDIDATE_HOST : candidateHost;
        companyEid = companyEid == null ? COMPANY_EID : companyEid;
        positionId = positionId == null ? POSITION_ID : positionId;

        PositionTest.execute(browserHost, candidateHost, companyEid, positionId);
        return "done!";
    }

}
