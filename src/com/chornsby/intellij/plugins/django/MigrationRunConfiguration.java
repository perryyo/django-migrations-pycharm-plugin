package com.chornsby.intellij.plugins.django;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.openapi.project.Project;
import com.jetbrains.python.run.PythonRunConfiguration;

/**
 * Dummy subclass of PythonRunConfiguration to allow instantiation
 */
public class MigrationRunConfiguration extends PythonRunConfiguration {
    protected MigrationRunConfiguration(Project project, ConfigurationFactory configurationFactory) {
        super(project, configurationFactory);
    }
}