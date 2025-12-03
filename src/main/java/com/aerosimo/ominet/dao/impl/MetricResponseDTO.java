/******************************************************************************
 * This piece of work is to enhance infraguard project functionality.         *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      MetricResponseDTO.java                                          *
 * Created:   03/12/2025, 00:21                                               *
 * Modified:  03/12/2025, 00:21                                               *
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

package com.aerosimo.ominet.dao.impl;

import java.util.List;

public class MetricResponseDTO {
    private DiskUsageDTO disk;
    private MemoryUsageDTO memory;
    private List<CpuThreadDTO> cpu;

    public MetricResponseDTO() {
    }

    public MetricResponseDTO(DiskUsageDTO disk, MemoryUsageDTO memory, List<CpuThreadDTO> cpu) {
        this.disk = disk;
        this.memory = memory;
        this.cpu = cpu;
    }

    public DiskUsageDTO getDisk() {
        return disk;
    }

    public void setDisk(DiskUsageDTO disk) {
        this.disk = disk;
    }

    public MemoryUsageDTO getMemory() {
        return memory;
    }

    public void setMemory(MemoryUsageDTO memory) {
        this.memory = memory;
    }

    public List<CpuThreadDTO> getCpu() {
        return cpu;
    }

    public void setCpu(List<CpuThreadDTO> cpu) {
        this.cpu = cpu;
    }

    @Override
    public String toString() {
        return "MetricResponseDTO{" +
                "disk=" + disk +
                ", memory=" + memory +
                ", cpu=" + cpu +
                '}';
    }
}