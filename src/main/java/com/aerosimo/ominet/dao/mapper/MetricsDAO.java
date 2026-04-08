/******************************************************************************
 * This piece of work is to enhance infraguard project functionality.         *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      MetricsDAO.java                                                 *
 * Created:   06/04/2026, 11:27                                               *
 * Modified:  06/04/2026, 11:27                                               *
 *                                                                            *
 * Copyright (c)  2026.  Aerosimo Ltd                                         *
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

package com.aerosimo.ominet.dao.mapper;

import com.aerosimo.ominet.core.config.Connect;
import com.aerosimo.ominet.core.model.Spectre;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MetricsDAO {

    private static final Logger log = LogManager.getLogger(MetricsDAO.class.getName());

    /**
     * Helper to strip "GB" and convert string to Double
     * e.g., "299.85GB" -> 299.85
     */
    private static Double parseGB(String value) {
        if (value == null) return 0.0;
        try {
            return Double.parseDouble(value.replaceAll("[^0-9.]", ""));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    /**
     * Saves Disk Metrics to Oracle via PL/SQL Package
     */
    public static void saveDiskMetrics(Map<String, String> diskData, String modifiedBy) {
        String sql = "{call infraguard_pkg.logDiskUsage(?, ?, ?, ?)}";

        try (Connection con = Connect.dbase();
             CallableStatement stmt = con.prepareCall(sql)) {
            stmt.setDouble(1, parseGB(diskData.get("total")));
            stmt.setDouble(2, parseGB(diskData.get("free")));
            stmt.setDouble(3, parseGB(diskData.get("usable")));
            stmt.setString(4, modifiedBy);
            stmt.execute();
        } catch (SQLException err) {
            log.error("Error in infraguard_pkg (LOG DISK USAGE)", err);
            try {
                Spectre.recordError("TE-20001", "Error in infraguard_pkg (LOG DISK USAGE): " + err.getMessage(), MetricsDAO.class.getName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Saves Memory Metrics to Oracle via PL/SQL Package
     */
    public static void saveMemoryMetrics(Map<String, String> memData, String modifiedBy) {
        String sql = "{call infraguard_pkg.logMemoryUsage(?, ?, ?, ?, ?)}";

        try (Connection con = Connect.dbase();
             CallableStatement stmt = con.prepareCall(sql)) {
            stmt.setDouble(1, parseGB(memData.get("init")));
            stmt.setDouble(2, parseGB(memData.get("used")));
            stmt.setDouble(3, parseGB(memData.get("max")));
            stmt.setDouble(4, parseGB(memData.get("committed")));
            stmt.setString(5, modifiedBy);
            stmt.execute();
        } catch (SQLException err) {
            log.error("Error in infraguard_pkg (LOG MEMORY USAGE)", err);
            try {
                Spectre.recordError("TE-20001", "Error in infraguard_pkg (LOG MEMORY USAGE): " + err.getMessage(), MetricsDAO.class.getName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Saves CPU Threads (Batch insert loop)
     */
    public static void saveCpuMetrics(List<Map<String, Object>> cpuThreads, String modifiedBy) {
        String sql = "{call infraguard_pkg.logCpuUsage(?, ?, ?, ?)}";

        try (Connection con = Connect.dbase();
             CallableStatement stmt = con.prepareCall(sql)) {
            for (Map<String, Object> thread : cpuThreads) {
                stmt.setString(1, (String) thread.get("threadName"));
                stmt.setString(2, (String) thread.get("state"));
                stmt.setObject(3, thread.get("cpuTime"));
                stmt.setString(4, modifiedBy);
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (SQLException err) {
            log.error("Error in infraguard_pkg (LOG MEMORY USAGE)", err);
            try {
                Spectre.recordError("TE-20001", "Error in infraguard_pkg (LOG MEMORY USAGE): " + err.getMessage(), MetricsDAO.class.getName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static List<Map<String, Object>> getLatestDiskMetric() {
        List<Map<String, Object>> result = new ArrayList<>();
        String sql = "{call infraguard_pkg.getDiskMetrics(?, ?)}";
        try (Connection con = Connect.dbase();
             CallableStatement stmt = con.prepareCall(sql)) {
            stmt.setInt(1, 12);
            stmt.registerOutParameter(2, java.sql.Types.REF_CURSOR);
            stmt.execute();
            try (java.sql.ResultSet rs = (java.sql.ResultSet) stmt.getObject(2)) {
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("total", rs.getDouble("total"));
                    row.put("free", rs.getDouble("free"));
                    row.put("usable", rs.getDouble("usable"));
                    result.add(row);
                }
            }
        } catch (Exception err) {
            log.error("Error in infraguard_pkg (GET DISK METRIC)", err);
            try {
                Spectre.recordError("TE-20001", "Error in infraguard_pkg (GET DISK METRIC): " + err.getMessage(), MetricsDAO.class.getName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    public static List<Map<String, Object>> getLatestMemoryMetric() {
        List<Map<String, Object>> result = new ArrayList<>();
        String sql = "{call infraguard_pkg.getMemoryMetrics(?, ?)}";
        try (Connection con = Connect.dbase();
             CallableStatement stmt = con.prepareCall(sql)) {
            stmt.setInt(1, 12);
            stmt.registerOutParameter(2, java.sql.Types.REF_CURSOR);
            stmt.execute();
            try (java.sql.ResultSet rs = (java.sql.ResultSet) stmt.getObject(2)) {
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("init", rs.getDouble("init"));
                    row.put("used", rs.getDouble("used"));
                    row.put("max", rs.getDouble("max"));
                    row.put("committed", rs.getDouble("committed"));
                    result.add(row);
                }
            }
        } catch (Exception err) {
            log.error("Error in infraguard_pkg (GET MEMORY METRIC)", err);
            try {
                Spectre.recordError("TE-20001", "Error in infraguard_pkg (GET MEMORY METRIC): " + err.getMessage(), MetricsDAO.class.getName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    public static List<Map<String, Object>> getLatestCpuMetrics() {
        List<Map<String, Object>> result = new ArrayList<>();
        String sql = "{call infraguard_pkg.getCPUMetrics(?, ?)}";
        try (Connection con = Connect.dbase();
             CallableStatement stmt = con.prepareCall(sql)) {
            stmt.setInt(1, 12);
            stmt.registerOutParameter(2, java.sql.Types.REF_CURSOR);
            stmt.execute();
            try (java.sql.ResultSet rs = (java.sql.ResultSet) stmt.getObject(2)) {
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("threadName", rs.getDouble("threadName"));
                    row.put("state", rs.getDouble("state"));
                    row.put("cpuTime", rs.getDouble("cpuTime"));
                    result.add(row);
                }
            }
        } catch (Exception err) {
            log.error("Error in infraguard_pkg (GET CPU METRIC)", err);
            try {
                Spectre.recordError("TE-20001", "Error in infraguard_pkg (GET CPU METRIC): " + err.getMessage(), MetricsDAO.class.getName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }
}