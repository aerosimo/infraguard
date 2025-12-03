/******************************************************************************
 * This piece of work is to enhance infraguard project functionality.         *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      OverviewResponseDTO.java                                        *
 * Created:   02/12/2025, 23:31                                               *
 * Modified:  02/12/2025, 23:31                                               *
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

public class OverviewResponseDTO {

    private String uptime;
    private String load;
    private String connections;
    private String status;
    private String hostname;

    public OverviewResponseDTO() {
    }

    public OverviewResponseDTO(String uptime, String load, String connections, String status, String hostname) {
        this.uptime = uptime;
        this.load = load;
        this.connections = connections;
        this.status = status;
        this.hostname = hostname;
    }

    public String getUptime() {return uptime;}
    public void setUptime(String uptime) {this.uptime = uptime;}

    public String getLoad() {return load;}
    public void setLoad(String load) {this.load = load;}

    public String getConnections() {return connections;}
    public void setConnections(String connections) {this.connections = connections;}

    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}

    public String getHostname() {return hostname;}
    public void setHostname(String hostname) {this.hostname = hostname;}

    @Override
    public String toString() {
        return "OverviewResponseDTO{" +
                "uptime='" + uptime + '\'' +
                ", load='" + load + '\'' +
                ", connections='" + connections + '\'' +
                ", status='" + status + '\'' +
                ", hostname='" + hostname + '\'' +
                '}';
    }
}