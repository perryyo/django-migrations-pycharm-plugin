package com.chornsby.intellij.plugins.django;

import com.intellij.execution.Location;
import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MigrationUtils {

    /**
     * Return the manage.py file from the project base directory or null.
     *
     * @param project The current Django project.
     * @return The manage.py VirtualFile or null.
     */
    @Nullable
    public static VirtualFile getManagePyFile(@NotNull Project project) {
        VirtualFile baseDir = project.getBaseDir();
        return baseDir == null ? null : baseDir.findChild("manage.py");
    }

    /**
     * Generate the script parameters to pass into a PythonRunConfiguration.
     *
     * @param context The context for which to generate parameters.
     * @return The calculated script parameters or null.
     */
    @Nullable
    public static String generateScriptParameters(ConfigurationContext context) {
        Location location = context.getLocation();
        if (location == null) return null;

        VirtualFile migrationFile = location.getVirtualFile();
        if (migrationFile == null || !canPerformAction(migrationFile)) return null;

        String appName = getAppName(migrationFile);
        String migrationName = getMigrationName(migrationFile);

        return "migrate " + appName + " " + migrationName;
    }

    /**
     * Return the name of the migration file without a file extension.
     *
     * If an __init__.py file is selected then treat this as `migrate <app> zero`.
     *
     * @param migrationFile The file that may be a Django migration file.
     * @return The name of the migration file without the .py extension or null.
     */
    @Contract("null -> null")
    public static String getMigrationName(VirtualFile migrationFile) {
        if (migrationFile == null) return null;

        String filename = migrationFile.getNameWithoutExtension();

        if (filename.equals("__init__")) {
            return "zero";
        } else {
            return filename;
        }
    }

    /**
     * Return the name of the Django app associated with this migration file.
     *
     * If an app name cannot be guessed then return null.
     *
     * @param migrationFile The file that may be a Django migration file.
     * @return The name of the containing app or null.
     */
    @Contract("null -> null")
    public static String getAppName(VirtualFile migrationFile) {
        if (migrationFile == null) return null;

        VirtualFile migrationsFolder = migrationFile.getParent();

        if (migrationsFolder == null) return null;
        if (!migrationsFolder.getName().equals("migrations")) return null;

        VirtualFile appFolder = migrationsFolder.getParent();

        if (appFolder == null) return null;

        return appFolder.getName();
    }

    /**
     * Return true if it is possible to generate a MigrationRunConfiguration.
     *
     * @param selectedFile The target file in the current context.
     * @return A boolean signifying if a MigrationRunConfiguration would be valid.
     */
    public static boolean canPerformAction(VirtualFile selectedFile) {
        return getMigrationName(selectedFile) != null && getAppName(selectedFile) != null;
    }
}
