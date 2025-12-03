/******************************************************************************
 * This piece of work is to enhance infraguard project functionality.         *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      PingServerResponseDTO.java                                      *
 * Created:   02/12/2025, 22:47                                               *
 * Modified:  02/12/2025, 22:47                                               *
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

public class PingServerResponseDTO {

    private String jenkins;
    private String tomee;
    private String linux;
    private String oracle;

    public PingServerResponseDTO() {
    }

    public PingServerResponseDTO(String jenkins, String oracle, String linux, String tomee) {
        this.jenkins = jenkins;
        this.oracle = oracle;
        this.linux = linux;
        this.tomee = tomee;
    }

    public PingServerResponseDTO(boolean jenkinsAlive, boolean oracleAlive, boolean linuxAlive, boolean tomeeAlive) {
        this.jenkins = jenkinsAlive ? "online" : "offline";
        this.oracle  = oracleAlive  ? "online" : "offline";
        this.linux   = linuxAlive   ? "online" : "offline";
        this.tomee   = tomeeAlive   ? "online" : "offline";
    }

    public String getJenkins() { return jenkins; }
    public void setJenkins(String jenkins) { this.jenkins = jenkins; }

    public String getTomee() { return tomee; }
    public void setTomee(String tomee) { this.tomee = tomee; }

    public String getLinux() { return linux; }
    public void setLinux(String linux) { this.linux = linux; }

    public String getOracle() { return oracle; }
    public void setOracle(String oracle) { this.oracle = oracle; }

    @Override
    public String toString() {
        return "PingServerResponseDTO{" +
                "jenkins='" + jenkins + '\'' +
                ", tomee='" + tomee + '\'' +
                ", linux='" + linux + '\'' +
                ", oracle='" + oracle + '\'' +
                '}';
    }
}