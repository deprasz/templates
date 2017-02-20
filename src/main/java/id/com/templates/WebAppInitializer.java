package id.com.templates;

import id.com.templates.configuration.ApplicationConfiguration;
import id.com.templates.configuration.MVCConfiguration;

public class WebAppInitializer extends AbstractInitializer {
    @Override
    public Class getConfigurationClass() {
        return ApplicationConfiguration.class;
    }

    @Override
    public Class getMVCConfigurationClass() {
        return MVCConfiguration.class;
    }
}
