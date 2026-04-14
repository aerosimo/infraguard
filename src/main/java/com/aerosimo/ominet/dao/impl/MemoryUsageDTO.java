/******************************************************************************
 * This piece of work is to enhance infraguard project functionality.         *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      MemoryUsageDTO.java                                             *
 * Created:   03/12/2025, 00:26                                               *
 * Modified:  03/12/2025, 00:26                                               *
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

import java.sql.Timestamp;

public class MemoryUsageDTO {
    private String init;
    private String used;
    private String max;
    private String committed;
    private Timestamp modifiedDate;

    public MemoryUsageDTO() {
    }

    public MemoryUsageDTO(String init, String used, String max, String committed, Timestamp modifiedDate) {
        this.init = init;
        this.used = used;
        this.max = max;
        this.committed = committed;
        this.modifiedDate = modifiedDate;
    }

    public String getInit() {
        return init;
    }

    public void setInit(String init) {
        this.init = init;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getCommitted() {
        return committed;
    }

    public void setCommitted(String committed) {
        this.committed = committed;
    }

    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Override
    public String toString() {
        return "MemoryUsageDTO{" +
                "init='" + init + '\'' +
                ", used='" + used + '\'' +
                ", max='" + max + '\'' +
                ", committed='" + committed + '\'' +
                ", modifiedDate=" + modifiedDate +
                '}';
    }
}