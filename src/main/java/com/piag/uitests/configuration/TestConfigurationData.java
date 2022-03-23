package com.piag.uitests.configuration;

import java.io.Serializable;
import java.util.List;

/**
 * TestConfigurationData
 *
 * @author Peter Kolarik
 * @date 3.2.2022
 */
public interface TestConfigurationData extends Serializable {

    /** Endpoint url, where a test should be performed */
    String getEndpointUrl();

    /** Contact on responsible, who should be informed in case of failed test */
    String getResponsible();

    /** List of fields that should be during the test used */
    List<TestFieldData> getFieldDataList();
}
