package cz.moravec.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.stereotype.Component;

@Component
public class Conditions {

    public static class ReadOnlyModeEnabled implements Condition {

        @Override
        public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
            String property = conditionContext.getEnvironment().getProperty("weather.read-only-mode");
            return property.equals("true");
        }
    }

    public static class ReadOnlyModeDisabled implements Condition {

        @Override
        public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
            String property = conditionContext.getEnvironment().getProperty("weather.read-only-mode");
            return !property.equals("true");
        }
    }


}
