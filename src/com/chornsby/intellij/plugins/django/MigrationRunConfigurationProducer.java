package com.chornsby.intellij.plugins.django;

import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.actions.RunConfigurationProducer;
import com.intellij.openapi.util.Ref;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.jetbrains.python.run.PythonRunConfiguration;

/**
 * A RunConfigurationProducer to display the "migrate" option for migration
 * files.
 */
public class MigrationRunConfigurationProducer extends RunConfigurationProducer<PythonRunConfiguration> {

    public MigrationRunConfigurationProducer() {
        super(MigrationRunConfigurationType.getInstance().getFactory());
    }

    /**
     * Return true if it is relevant to display a MigrationRunConfiguration.
     *
     * As a side-effect, modify the basic PythonRunConfiguration with the
     * relevant script name and parameters.
     *
     * @param configuration The run configuration to setup.
     * @param context The context within which this is considered to be run.
     * @param sourceElement The source element at the caret.
     * @return A boolean signifying whether this configuration is relevant.
     */
    @Override
    protected boolean setupConfigurationFromContext(PythonRunConfiguration configuration,
                                                    ConfigurationContext context,
                                                    Ref<PsiElement> sourceElement) {
        VirtualFile managePyFile = MigrationUtils.getManagePyFile(context.getProject());
        if (managePyFile == null) return false;

        String params = MigrationUtils.generateScriptParameters(context);
        if (params == null) return false;

        configuration.setName(params);
        configuration.setScriptName(managePyFile.getPath());
        configuration.setScriptParameters(params);

        return true;
    }

    /**
     * Return true if the given run configuration matches one generated here.
     *
     * @param configuration The run configuration to consider.
     * @param context The current context.
     * @return A boolean signifying if the configuration matches the context.
     */
    @Override
    public boolean isConfigurationFromContext(PythonRunConfiguration configuration,
                                              ConfigurationContext context) {
        String params = MigrationUtils.generateScriptParameters(context);
        return configuration.getScriptParameters().equals(params);
    }
}
