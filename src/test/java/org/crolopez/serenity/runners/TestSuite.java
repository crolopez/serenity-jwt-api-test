package org.crolopez.serenity.runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features",
        // Explicitly list the packages containing Step Definitions
        glue = {"org.crolopez.serenity.features.authentication",
                "org.crolopez.serenity.features.user"},
        tags = "not @ignore"
)
public class TestSuite {
} 