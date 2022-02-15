package org.pko.bewtest.configuration;

import java.io.Serializable;
import java.util.Objects;

/**
 * TestFieldData
 *
 * @author Peter Kolarik
 * @date 1.2.2022
 */
public final class TestFieldData implements Serializable {
    private final String name;
    private final TestFieldType type;
    private final String defaultValue;

    public TestFieldData(String name, TestFieldType type, String defaultValue) {
        this.name = name;
        this.type = type;
        this.defaultValue = defaultValue;
    }

    public String getName() {
        return name;
    }

    public TestFieldType getType() {
        return type;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestFieldData)) return false;
        TestFieldData that = (TestFieldData) o;
        return Objects.equals(name, that.name) && type == that.type && Objects.equals(defaultValue, that.defaultValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, defaultValue);
    }
}
