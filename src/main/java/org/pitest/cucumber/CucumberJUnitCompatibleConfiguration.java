package org.pitest.cucumber;


import org.pitest.junit.CompoundTestUnitFinder;
import org.pitest.junit.JUnitCompatibleConfiguration;
import org.pitest.testapi.TestGroupConfig;
import org.pitest.testapi.TestUnitFinder;
import org.pitest.util.Log;

import java.util.List;

import static java.util.Arrays.asList;

public class CucumberJUnitCompatibleConfiguration extends JUnitCompatibleConfiguration {

    public CucumberJUnitCompatibleConfiguration(TestGroupConfig config) {
        super(config);
    }

    @Override
    public TestUnitFinder testUnitFinder() {
        final TestUnitFinder finder;
        if (isCucumberUsed()) {
            Log.getLogger().info("Cucumber detected, scenarios will be used");
            List<TestUnitFinder> finders
                    = asList(new CucumberTestUnitFinder(), super.testUnitFinder());
            finder = new CompoundTestUnitFinder(finders);
        } else {
            Log.getLogger().info("Cucumber not used in this project");
            finder = super.testUnitFinder();
        }
        return finder;
    }

    private boolean isCucumberUsed() {
        boolean result = false;
        try {
            Class.forName("cucumber.api.junit.Cucumber");
            result = true;
        } catch (ClassNotFoundException e) {
        }
        return result;
    }
}
