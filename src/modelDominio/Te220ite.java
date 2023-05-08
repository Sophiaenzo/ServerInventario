/*
 * The MIT License
 *
 * Copyright 2023 wilson.simoes.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package modelDominio;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author wilson.simoes
 */
public class Te220ite implements Serializable{
    private static final long serialVersionUID = 123456789L;
    private Date datinv;
    private String coddep;
    private String codpro;
    private Float qtdest;
    private Float qtdsoma;
    private Float qtdsaldo;

    public Te220ite(Date datinv, String coddep, String codpro, float qtdest, float qtdsoma, float qtdsaldo) {
        this.datinv = datinv;
        this.coddep = coddep;
        this.codpro = codpro;
        this.qtdest = qtdest;
        this.qtdsoma = qtdsoma;
        this.qtdsaldo = qtdsaldo;
    }

    public Date getDatinv() {
        return datinv;
    }

    public void setDatinv(Date datinv) {
        this.datinv = datinv;
    }

    public String getCoddep() {
        return coddep;
    }

    public void setCoddep(String coddep) {
        this.coddep = coddep;
    }

    public String getCodpro() {
        return codpro;
    }

    public void setCodpro(String codpro) {
        this.codpro = codpro;
    }

    public Float getQtdest() {
        return qtdest;
    }

    public void setQtdest(Float qtdest) {
        this.qtdest = qtdest;
    }

    public Float getQtdsoma() {
        return qtdsoma;
    }

    public void setQtdsoma(Float qtdsoma) {
        this.qtdsoma = qtdsoma;
    }

    public Float getQtdsaldo() {
        return qtdsaldo;
    }

    public void setQtdsaldo(Float qtdsaldo) {
        this.qtdsaldo = qtdsaldo;
    }
    
    
    
}
