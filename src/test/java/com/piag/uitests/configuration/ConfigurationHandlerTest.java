package com.piag.uitests.configuration;

import com.google.common.collect.Lists;
import com.google.gson.GsonBuilder;
import org.assertj.core.api.Fail;
import org.junit.jupiter.api.Test;
import com.piag.uitests.configuration.bewerber.BewerberTestConfigurationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.*;

/**
 * ConfigurationHandlerTest
 *
 * @author Peter Kolarik
 * @date 3.2.2022
 */

@SpringBootTest
class ConfigurationHandlerTest {

    @Autowired
    ConfigurationHandler<BewerberTestConfigurationData> handler;


    @Test
    public void testReadConfigurations() {
        List<BewerberTestConfigurationData> list = handler.getConfigurations();
        assertTrue("list can not be empty", list.size() > 0);
        assertTrue("first companyEid doesn't match", "ALL".equals(list.get(0).getCompanyEid()));
    }

    @Value("${configuration.bew.file.path}")
    private String configBewFilePath;

    @Test
    public void testAddRemoveConfiguration() {
        try {
            List<BewerberTestConfigurationData> list = handler.getConfigurations();
            int sizeBeforeUpdate = list.size();
            byte[] bytesBeforeAdd = Files.readAllBytes(Paths.get(configBewFilePath));
            BewerberTestConfigurationData first = list.get(0);
            // new test data
            BewerberTestConfigurationData newData = new BewerberTestConfigurationData(first.getEndpointUrl(), first.getCompanyEid() + "2", first.getPositionId() + "2", first.getResponsible(), first.getFieldDataList());
            String json = new GsonBuilder().create().toJson(newData);
            BewerberTestConfigurationData newData2 = handler.addConfiguration(json);
            assertTrue("configuration data must be equal", newData.equals(newData2));
            assertTrue("configuration data must be persisted - check after update", handler.getConfigurations().size() == (sizeBeforeUpdate + 1));
            byte[] bytesAfterAdd = Files.readAllBytes(Paths.get(configBewFilePath));
            // delete new created data
            testRemoveConfiguration(newData2);
            byte[] bytesAfterAddAndDelete = Files.readAllBytes(Paths.get(configBewFilePath));
            assertTrue("seems no data has been added in test config data " + configBewFilePath, bytesBeforeAdd.length !=  bytesAfterAdd.length);
            assertTrue("seems no data has been deleted in test config data " + configBewFilePath, bytesBeforeAdd.length ==  bytesAfterAddAndDelete.length);

        } catch (IOException e) {
            Fail.fail("failure: " + e);
        }
    }

    private void testRemoveConfiguration(BewerberTestConfigurationData dataToRemove) {
        int sizeBeforeDelete = handler.getConfigurations().size();
        BewerberTestConfigurationData deleted = handler.removeConfiguration(dataToRemove.getEndpointUrl(), dataToRemove.getCompanyEid(), dataToRemove.getPositionId());
        assertEquals("created and deleted test configuration data must be the same", dataToRemove, deleted);
        assertTrue("number of configurations after delete must be decreased of 1", handler.getConfigurations().size() == (sizeBeforeDelete - 1));
    }

    @Test
    public void testReplaceConfiguration() {
        try {
            List<BewerberTestConfigurationData> list = handler.getConfigurations();
            int sizeBeforeUpdate = list.size();
            byte[] bytesBeforeAdd = Files.readAllBytes(Paths.get(configBewFilePath));
            BewerberTestConfigurationData first = list.get(0);
            // new test data
            BewerberTestConfigurationData newData = new BewerberTestConfigurationData(first.getEndpointUrl(), first.getCompanyEid() + "2", first.getPositionId() + "2", first.getResponsible(), first.getFieldDataList());
            String json = new GsonBuilder().create().toJson(newData);
            BewerberTestConfigurationData newData2 = handler.addConfiguration(json);
            assertTrue("configuration data must be equal", newData.equals(newData2));
            assertTrue("configuration data must be persisted - check after update", handler.getConfigurations().size() == (sizeBeforeUpdate + 1));
            // test add the same configuration
            List<TestFieldData> fieldData3 = newData2.getFieldDataList();
            fieldData3.add(new TestFieldData("test", TestFieldType.TEXT, "test"));
            BewerberTestConfigurationData newData3 = new BewerberTestConfigurationData(newData2.getEndpointUrl(), newData2.getCompanyEid(), newData2.getPositionId(), newData2.getResponsible(), fieldData3);
            BewerberTestConfigurationData newData3Test = handler.addConfiguration(new GsonBuilder().create().toJson(newData3));

            assertTrue("configuration data must be equal", newData3.equals(newData3Test));
            // must be the same size as after first add, because the last newData2 must bre replaced with newData3 (because of same endpointUrl, comppanyEid and positionId)
            assertTrue("configuration data must be persisted - check after update", handler.getConfigurations().size() == (sizeBeforeUpdate + 1));

            // delete new created data
            testRemoveConfiguration(newData3);
        } catch (IOException e) {
            Fail.fail("failure: " + e);
        }
    }

    @Test
    public void testCreateAndParseConfigurationJsonData() {
        TestFieldData title = new TestFieldData("59d0b92e-9d7f-4089-9098-3d9162461259", TestFieldType.COMBOBOX, "Herr");
        TestFieldData firstName = new TestFieldData("d31760f2-7219-40be-85c6-834f192408e3", TestFieldType.TEXT, "Firstnametest");
        TestFieldData lastName = new TestFieldData("51ecd5db-195f-4134-aab3-fcb57566d46c", TestFieldType.TEXT, "Lastnametest");
        TestFieldData email = new TestFieldData("41475411-8483-490b-943f-287263a83bce", TestFieldType.TEXT, "pkolarik@pi-ag.com");

        BewerberTestConfigurationData data1 = new BewerberTestConfigurationData("https://loga-21-10.pi-asp.de/bewerber-web", "ALL", "91a6f718-8a2e-4b08-bf52-37f5e3bd029a", "pkolarik@pi-ag.com", Lists.newArrayList(title, firstName, lastName, email));
        BewerberTestConfigurationData data2 = new BewerberTestConfigurationData("https://loga-21-10.pi-asp.de/bewerber-web", "EID", "91a6f718-8a2e-4b08-bf52-37f5e3bd2222", "pkolarik@pi-ag.com", Lists.newArrayList(title, firstName, lastName, email));

        String json = new GsonBuilder().create().toJson(Lists.newArrayList(data1, data2));
        System.out.println("json: " + json);
        assertNotNull("produced json can not be null", json);
        assertTrue("produced json can not be empty", json.contains("[{"));
    }

}