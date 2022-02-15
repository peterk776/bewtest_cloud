package org.pko.bewtest.configuration.bewerber;

import org.pko.bewtest.configuration.TestConfigurationData;
import org.pko.bewtest.configuration.TestFieldData;

import java.util.List;
import java.util.Objects;

/**
 * TestConfigurationData
 *
 * @author Peter Kolarik
 * @date 1.2.2022
 */
public final class BewerberTestConfigurationData implements TestConfigurationData {
    private final String endpointUrl;
    private final String companyEid;
    private final String positionId;
    private final String responsible;
    private final List<TestFieldData> fieldDataList;

    public BewerberTestConfigurationData(String endpointUrl, String companyEid, String positionId, String responsible, List<TestFieldData> fieldDataList) {
        Objects.requireNonNull(endpointUrl, "endpointUrl can not be null");
        Objects.requireNonNull(companyEid, "companyEid can not be null");
        Objects.requireNonNull(positionId, "positionId can not be null");
        Objects.requireNonNull(responsible, "responsible can not be null");

        this.endpointUrl = endpointUrl;
        this.companyEid = companyEid;
        this.positionId = positionId;
        this.responsible = responsible;
        this.fieldDataList = fieldDataList;
    }

    @Override
    public String getEndpointUrl() {
        return endpointUrl;
    }

    public String getCompanyEid() {
        return companyEid;
    }

    public String getPositionId() {
        return positionId;
    }

    @Override
    public String getResponsible() {
        return responsible;
    }

    @Override
    public List<TestFieldData> getFieldDataList() {
        return fieldDataList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BewerberTestConfigurationData)) return false;
        BewerberTestConfigurationData that = (BewerberTestConfigurationData) o;
        return Objects.equals(endpointUrl, that.endpointUrl) && Objects.equals(companyEid, that.companyEid) && Objects.equals(positionId, that.positionId) && Objects.equals(fieldDataList, that.fieldDataList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(endpointUrl, companyEid, positionId, fieldDataList);
    }

    @Override
    public String toString() {
        return "BewerberTestConfigurationData{" +
                "endpointUrl='" + endpointUrl + '\'' +
                ", companyEid='" + companyEid + '\'' +
                ", positionId='" + positionId + '\'' +
                '}';
    }
}
