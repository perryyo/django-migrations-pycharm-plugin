package com.chornsby.intellij.plugins.django;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.ConfigurationTypeUtil;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.project.Project;
import com.intellij.ui.LayeredIcon;
import icons.PythonUltimateIcons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * A custom ConfigurationType for Django migrations.
 */
public class MigrationRunConfigurationType implements ConfigurationType {

    private final MigrationRunConfigurationFactory myFactory = new MigrationRunConfigurationFactory(this);

    public static MigrationRunConfigurationType getInstance() {
        return ConfigurationTypeUtil.findConfigurationType(MigrationRunConfigurationType.class);
    }

    public static class MigrationRunConfigurationFactory extends ConfigurationFactory {
        protected MigrationRunConfigurationFactory(ConfigurationType configurationType) {
            super(configurationType);
        }

        @NotNull
        public RunConfiguration createTemplateConfiguration(@NotNull Project project) {
            return new MigrationRunConfiguration(project, this);
        }
    }

    @Override
    public String getDisplayName() {
        return "Django migration";
    }

    @Override
    public String getConfigurationTypeDescription() {
        return "Run a migration";
    }

    @Override
    public Icon getIcon() {
        return LayeredIcon.create(
                PythonUltimateIcons.Django.Django,
                AllIcons.Nodes.RunnableMark
        );
    }

    @NotNull
    @Override
    public String getId() {
        return MigrationRunConfigurationType.class.getCanonicalName();
    }

    public MigrationRunConfigurationFactory getFactory() {
        return myFactory;
    }

    @Override
    public ConfigurationFactory[] getConfigurationFactories() {
        return new ConfigurationFactory[]{myFactory};
    }
}
