/******************************************************************************
 * This piece of work is to enhance infraguard project functionality.         *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      PulsePoint.java                                                 *
 * Created:   02/12/2025, 22:24                                               *
 * Modified:  02/12/2025, 22:24                                               *
 *                                                                            *
 * Copyright (c)  2025.  Aerosimo Ltd                                         *
 *                                                                            *
 * Permission is hereby granted, free of charge, to any person obtaining a    *
 * copy of this software and associated documentation files (the "Software"), *
 * to deal in the Software without restriction, including without limitation  *
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,   *
 * and/or sell copies of the Software, and to permit persons to whom the      *
 * Software is furnished to do so, subject to the following conditions:       *
 *                                                                            *
 * The above copyright notice and this permission notice shall be included    *
 * in all copies or substantial portions of the Software.                     *
 *                                                                            *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,            *
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES            *
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND                   *
 * NONINFINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT                 *
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,               *
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING               *
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE                 *
 * OR OTHER DEALINGS IN THE SOFTWARE.                                         *
 *                                                                            *
 ******************************************************************************/

package com.aerosimo.ominet.core.model;

import com.aerosimo.ominet.dao.impl.DiskUsageDTO;
import com.aerosimo.ominet.dao.impl.MemoryUsageDTO;
import com.aerosimo.ominet.dao.mapper.MetricsDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.lang.management.*;
import java.net.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class PulsePoint {

    private static final Logger log = LogManager.getLogger(PulsePoint.class.getName());

    public static boolean isAlive(String target) {
        if (target.startsWith("http://") || target.startsWith("https://")) {
            return httpCheck(target);
        } else if (target.contains(":")) {
            String[] parts = target.split(":");
            String host = parts[0];
            int port = Integer.parseInt(parts[1]);
            return tcpCheck(host, port);
        }
        return false;
    }

    private static boolean httpCheck(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(2000);
            conn.setReadTimeout(2000);
            conn.setRequestMethod("GET");
            int code = conn.getResponseCode();
            return (code >= 200 && code < 400);
        } catch (IOException err) {
            log.error("System http check failed with the following: - {}", String.valueOf(err));
            try {
                Spectre.recordError("TE-20001", "System http check failed with the following " + err, PulsePoint.class.getName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return false;
        }
    }

    private static boolean tcpCheck(String host, int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), 2000);
            return true;
        } catch (IOException err) {
            log.error("System tcp check failed with the following: - {}", String.valueOf(err));
            try {
                Spectre.recordError("TE-20001", "System tcp check failed with the following " + err, PulsePoint.class.getName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return false;
        }
    }

    public static String getUptime() {
        RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
        long uptimeMillis = rb.getUptime();
        Duration uptime = Duration.ofMillis(uptimeMillis);

        long days = uptime.toDays();
        long hours = uptime.minusDays(days).toHours();
        long minutes = uptime.minusDays(days).minusHours(hours).toMinutes();

        return String.format("%dd %dh %dm", days, hours, minutes);
    }

    public static double getLoadAverage() {
        OperatingSystemMXBean os = ManagementFactory.getOperatingSystemMXBean();
        return os.getSystemLoadAverage();
    }

    public static String getHostname() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "Unknown";
        }
    }

    public static int getActiveConnections() {
        // Placeholder – you could plug in TomEE/JDBC/Netstat here
        return (int) (Math.random() * 200);
    }

    public static String getHealthStatus() {
        // Could be a composite check
        return "Running";
    }

    public static List<DiskUsageDTO> getDisk() {
        List<DiskUsageDTO> diskusage = new ArrayList<>();
        List<Map<String, Object>> dataList = MetricsDAO.getLatestDiskMetric();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Map<String, Object> data : dataList) {
            Timestamp timestamp = (Timestamp) data.get("modifiedDate");
            String formattedDate = (timestamp != null ? dateFormat.format(timestamp) : "N/A");
            diskusage.add(new DiskUsageDTO(
                    String.format("%.2fGB", (Double) data.get("total")),
                    String.format("%.2fGB", (Double) data.get("free")),
                    String.format("%.2fGB", (Double) data.get("usable")),
                    formattedDate
            ));
        }
        return diskusage;
    }

    public static List<MemoryUsageDTO> getMemory() {
        List<MemoryUsageDTO> memoryusage = new ArrayList<>();
        List<Map<String, Object>> dataList = MetricsDAO.getLatestMemoryMetric();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Map<String, Object> data : dataList) {
            Timestamp timestamp = (Timestamp) data.get("modifiedDate");
            String formattedDate = (timestamp != null ? dateFormat.format(timestamp) : "N/A");
            memoryusage.add(new MemoryUsageDTO(
                    String.format("%.2fGB", (Double) data.get("init")),
                    String.format("%.2fGB", (Double) data.get("used")),
                    String.format("%.2fGB", (Double) data.get("max")),
                    String.format("%.2fGB", (Double) data.get("committed")),
                    formattedDate
            ));
        }
        return memoryusage;
    }

    public static ArrayList<String> getCpu() {
        ArrayList<String> cpuList = new ArrayList<>();
        List<Map<String, Object>> threads = MetricsDAO.getLatestCpuMetrics();
        Collections.reverse(threads);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Map<String, Object> snapshot : threads) {
            Timestamp timestamp = (Timestamp) snapshot.get("modifiedDate");
            String formattedDate = (timestamp != null ? dateFormat.format(timestamp) : "N/A");
            cpuList.add((String) snapshot.getOrDefault("threadName", "N/A"));
            cpuList.add((String) snapshot.getOrDefault("state", "N/A"));
            cpuList.add(String.valueOf(snapshot.getOrDefault("cpuTime", "0")));
            cpuList.add(formattedDate);
        }
        log.info("CPU result in PulsePoint: " + cpuList);
        return cpuList;
    }

    public static void captureAndSaveMetrics() {
        String audit = "SYSTEM_PULSE";

        // Capture and Save Disk
        File root = new File("/");
        Map<String, String> diskMap = new HashMap<>();
        diskMap.put("total", String.valueOf((double) root.getTotalSpace() / 1073741824));
        diskMap.put("free", String.valueOf((double) root.getFreeSpace() / 1073741824));
        diskMap.put("usable", String.valueOf((double) root.getUsableSpace() / 1073741824));
        MetricsDAO.saveDiskMetrics(diskMap, audit);

        // Capture and Save Memory
        MemoryMXBean memBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heap = memBean.getHeapMemoryUsage();
        Map<String, String> memMap = new HashMap<>();
        memMap.put("init", String.valueOf((double) heap.getInit() / 1073741824));
        memMap.put("used", String.valueOf((double) heap.getUsed() / 1073741824));
        memMap.put("max", String.valueOf((double) heap.getMax() / 1073741824));
        memMap.put("committed", String.valueOf((double) heap.getCommitted() / 1073741824));
        MetricsDAO.saveMemoryMetrics(memMap, audit);

        // Capture and Save CPU
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        List<Map<String, Object>> cpuThreads = new ArrayList<>();
        for (long id : threadMXBean.getAllThreadIds()) {
            ThreadInfo info = threadMXBean.getThreadInfo(id);
            if (info != null) {
                Map<String, Object> t = new HashMap<>();
                t.put("threadName", info.getThreadName());
                t.put("state", info.getThreadState().toString());
                t.put("cpuTime", threadMXBean.getThreadCpuTime(id));
                cpuThreads.add(t);
            }
        }
        MetricsDAO.saveCpuMetrics(cpuThreads, audit);
    }
}