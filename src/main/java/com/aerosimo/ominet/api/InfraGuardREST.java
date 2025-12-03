/******************************************************************************
 * This piece of work is to enhance infraguard project functionality.         *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      InfraGuardREST.java                                             *
 * Created:   02/12/2025, 22:57                                               *
 * Modified:  02/12/2025, 22:57                                               *
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

package com.aerosimo.ominet.api;

import com.aerosimo.ominet.core.model.PulsePoint;
import com.aerosimo.ominet.dao.impl.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

@Path("/guard")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InfraGuardREST {

    private static final Logger log = LogManager.getLogger(InfraGuardREST.class);

    @GET
    @Path("/pulse")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkPulse() {
        boolean jenkinsAlive = PulsePoint.isAlive("ominet.aerosimo.com:8443");
        boolean tomeeAlive   = PulsePoint.isAlive("https://ominet.aerosimo.com:9443");
        boolean linuxAlive   = PulsePoint.isAlive("ominet.aerosimo.com:9090");
        boolean oracleAlive  = true;
        return Response.ok(
                new PingServerResponseDTO(jenkinsAlive, oracleAlive, linuxAlive, tomeeAlive)
        ).build();
    }

    @GET
    @Path("/health")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkHealth() {
        String uptime = PulsePoint.getUptime();
        String load = String.valueOf(PulsePoint.getLoadAverage());
        String connections = String.valueOf(PulsePoint.getActiveConnections());
        String status = PulsePoint.getHealthStatus();
        String hostname = PulsePoint.getHostname();
        return Response.ok(new OverviewResponseDTO(uptime,load,connections,status,hostname)).build();
    }

    @GET
    @Path("/metric")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkUsage() {
        String[] disk = PulsePoint.getDisk();
        String[] memory = PulsePoint.getMemory();
        List<String> cpuRaw = PulsePoint.getCpu();
        DiskUsageDTO diskDTO = new DiskUsageDTO(disk[0], disk[1], disk[2]);
        MemoryUsageDTO memoryDTO = new MemoryUsageDTO(memory[0], memory[1], memory[2], memory[3]);
        List<CpuThreadDTO> cpuDTOs = new ArrayList<>();
        for (int i = 0; i < cpuRaw.size(); i += 3) {
            cpuDTOs.add(new CpuThreadDTO(cpuRaw.get(i), cpuRaw.get(i+1), cpuRaw.get(i+2)));}
        MetricResponseDTO metrics = new MetricResponseDTO(diskDTO, memoryDTO, cpuDTOs);
        return Response.ok(metrics).build();
    }
}