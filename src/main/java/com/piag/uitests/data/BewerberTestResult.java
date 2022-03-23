package com.piag.uitests.data;

import java.util.Objects;
import javax.annotation.Nullable;

import com.google.common.base.Strings;
import com.piag.uitests.configuration.bewerber.BewerberTestConfigurationData;

/**
 * TestStatus
 *
 * @author Peter Kolarik
 * @date 26.1.2022
 */
public final class BewerberTestResult<T extends BewerberTestConfigurationData> implements TestResult<BewerberTestConfigurationData> {

    private final State state;
    private final String message;
    private final TestInputData testInputData;
    private final String responsible;
    private final String target;

    public BewerberTestResult(@Nullable T configData, State state, String message) {
        this.state = state;
        this.message = message;
        if (configData != null) {
            this.testInputData = new BewerberTestInputData(configData.getCompanyEid(), configData.getPositionId());
            this.responsible = configData.getResponsible();
            this.target = resolveTarget(configData.getEndpointUrl());
        } else {
            this.testInputData = null;
            this.responsible = null;
            this.target = null;
        }
    }

    @Override
    public TestInputData getTestData() {
        return testInputData;
    }

    @Override
    public String getTarget() {
        return target;
    }

    @Override
    public State getState() {
        return state;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getResponsible() {
        return responsible;
    }

    private String resolveTarget(String url) {
        String target;
        if (Strings.isNullOrEmpty(url))
            return "n/a";

        String[] str = url.split("://");
        if (str.length > 1) {
            final String urlRest = str[1];
            final int slashIndex = urlRest.indexOf('/');
            final int endIndex = slashIndex == -1 ? urlRest.length() : slashIndex;
            target = urlRest.substring(0, endIndex);
        } else {
            target = "n/a";
        }

        return target;
    }

    public static class BewerberTestInputData implements TestInputData {
        private final String companyEid;
        private final String positionId;

        public BewerberTestInputData(String companyEid, String positionId) {
            this.companyEid = companyEid;
            this.positionId = positionId;
        }

        public String getCompanyEid() {
            return companyEid;
        }

        public String getPositionId() {
            return positionId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof BewerberTestResult.BewerberTestInputData)) return false;
            BewerberTestInputData that = (BewerberTestInputData) o;
            return Objects.equals(companyEid, that.companyEid) && Objects.equals(positionId, that.positionId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(companyEid, positionId);
        }
    }

}
