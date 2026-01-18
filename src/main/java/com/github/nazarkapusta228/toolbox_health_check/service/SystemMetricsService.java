package com.github.nazarkapusta228.toolbox_health_check.service;

import com.github.nazarkapusta228.toolbox_health_check.model.SystemMetrics;
import org.springframework.stereotype.Service;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.text.DecimalFormat;

@Service
public class SystemMetricsService {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");
    private static final long GB = 1024 * 1024 * 1024;
    private static final long MB = 1024 * 1024;

    public SystemMetrics checkSystemMetrics() {
        try {

            String diskFree = getDiskFreeSpace();
            String memoryFree = getMemoryFree();
            double cpuUsage = getCpuUsage();

            return SystemMetrics.builder()
                    .diskFree(diskFree)
                    .memoryFree(memoryFree)
                    .cpuUsage(Double.parseDouble(DECIMAL_FORMAT.format(cpuUsage)))
                    .build();

        } catch (Exception e) {

            return getFallbackMetrics();
        }
    }

    private String getDiskFreeSpace() {
        try {
            File[] roots = File.listRoots();
            if (roots != null && roots.length > 0) {
                File root = roots[0];
                long freeBytes = root.getFreeSpace();
                long totalBytes = root.getTotalSpace();

                return formatBytes(freeBytes) + " / " + formatBytes(totalBytes) + " free";
            }
            return "Disk info unavailable";
        } catch (Exception e) {
            return "Error reading disk space";
        }
    }

    private String getMemoryFree() {
        try {
            Runtime runtime = Runtime.getRuntime();
            long freeMemory = runtime.freeMemory();
            long totalMemory = runtime.totalMemory();
            long maxMemory = runtime.maxMemory();

            return formatBytes(freeMemory) + " free (" +
                    formatBytes(totalMemory) + " allocated, " +
                    formatBytes(maxMemory) + " max)";
        } catch (Exception e) {
            return "Error reading memory";
        }
    }

    private double getCpuUsage() {
        try {
            OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();

            if (osBean instanceof com.sun.management.OperatingSystemMXBean) {
                com.sun.management.OperatingSystemMXBean sunOsBean =
                        (com.sun.management.OperatingSystemMXBean) osBean;
                return sunOsBean.getSystemCpuLoad() * 100; // Відсотки
            }


            double loadAverage = osBean.getSystemLoadAverage();
            if (loadAverage >= 0) {
                return loadAverage;
            }

            return 0.0;
        } catch (Exception e) {
            return 0.0;
        }
    }

    private String formatBytes(long bytes) {
        if (bytes >= GB) {
            return DECIMAL_FORMAT.format((double) bytes / GB) + " GB";
        } else if (bytes >= MB) {
            return DECIMAL_FORMAT.format((double) bytes / MB) + " MB";
        } else if (bytes >= 1024) {
            return DECIMAL_FORMAT.format((double) bytes / 1024) + " KB";
        } else {
            return bytes + " B";
        }
    }

    private SystemMetrics getFallbackMetrics() {
        return SystemMetrics.builder()
                .diskFree("System metrics unavailable")
                .memoryFree("Check system permissions")
                .cpuUsage(0.0)
                .build();
    }
}