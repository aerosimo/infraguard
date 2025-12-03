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

import java.io.File;
import java.io.IOException;
import java.lang.management.*;
import java.net.*;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class PulsePoint {

    private static final Instant START_TIME = Instant.now();

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
        } catch (IOException e) {
            return false;
        }
    }

    private static boolean tcpCheck(String host, int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), 2000);
            return true;
        } catch (IOException e) {
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

    public static String[] getDisk() {
        String[] diskusage = new String[3];
        File root = new File("/");
        diskusage[0] = String.format("%.2fGB", (double) root.getTotalSpace() / 1073741824);
        diskusage[1] = String.format("%.2fGB", (double) root.getFreeSpace() / 1073741824);
        diskusage[2] = String.format("%.2fGB", (double) root.getUsableSpace() / 1073741824);
        return diskusage;
    }

    public static String[] getMemory() {
        String[] memoryusage = new String[4];
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        memoryusage[0] = String.format("%.2fGB", (double) memoryMXBean.getHeapMemoryUsage().getInit() / 1073741824);
        memoryusage[1] = String.format("%.2fGB", (double) memoryMXBean.getHeapMemoryUsage().getUsed() / 1073741824);
        memoryusage[2] = String.format("%.2fGB", (double) memoryMXBean.getHeapMemoryUsage().getMax() / 1073741824);
        memoryusage[3] = String.format("%.2fGB", (double) memoryMXBean.getHeapMemoryUsage().getCommitted() / 1073741824);
        return memoryusage;
    }

    public static ArrayList<String> getCpu() {
        ArrayList<String> cpuusage = new ArrayList<>();
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        cpuusage.clear(); // reset list to avoid duplicates
        for (Long threadID : threadMXBean.getAllThreadIds()) {
            ThreadInfo info = threadMXBean.getThreadInfo(threadID);
            if (info != null) { // avoid null ThreadInfo
                cpuusage.add(info.getThreadName());
                cpuusage.add(String.valueOf(info.getThreadState()));
                cpuusage.add(String.format("%s", threadMXBean.getThreadCpuTime(threadID)));
            }
        }
        return cpuusage;
    }
}