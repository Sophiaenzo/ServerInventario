package model;

import java.sql.Date;

/**
 *
 * @author wilson.simoes
 */
public class EtiquetaDAO {
    private int USU_CODEMP;
    private String USU_DATINV;
    private String USU_CODDEP;
    private String USU_CODPRO;
    private String USU_CODDER;
    private int USU_NUMCON;
    private Float USU_QTDCON;
    private int USU_USUCON;
    private String USU_DATCON;
    private int USU_SEQCON;
    private String USU_HORCON;
    private String USU_ETIQUETA;
    private String USU_INDBIP;
    private String USU_OBSBIP;

    public EtiquetaDAO(int USU_CODEMP, String USU_DATINV, String USU_CODDEP, String USU_CODPRO, String USU_CODDER, int USU_NUMCON, Float USU_QTDCON, int USU_USUCON, String USU_DATCON, int USU_SEQCON, String USU_HORCON, String USU_ETIQUETA, String USU_INDBIP, String USU_OBSBIP) {
        this.USU_CODEMP = USU_CODEMP;
        this.USU_DATINV = USU_DATINV;
        this.USU_CODDEP = USU_CODDEP;
        this.USU_CODPRO = USU_CODPRO;
        this.USU_CODDER = USU_CODDER;
        this.USU_NUMCON = USU_NUMCON;
        this.USU_QTDCON = USU_QTDCON;
        this.USU_USUCON = USU_USUCON;
        this.USU_DATCON = USU_DATCON;
        this.USU_SEQCON = USU_SEQCON;
        this.USU_HORCON = USU_HORCON;
        this.USU_ETIQUETA = USU_ETIQUETA;
        this.USU_INDBIP = USU_INDBIP;
        this.USU_OBSBIP = USU_OBSBIP;
    }

    public EtiquetaDAO(){
    }

    @Override
    public String toString() {
        return "EtiquetaDAO{" + "USU_CODEMP=" + USU_CODEMP + ", USU_DATINV=" + USU_DATINV + ", USU_CODDEP=" + USU_CODDEP + ", USU_CODPRO=" + USU_CODPRO + ", USU_CODDER=" + USU_CODDER + ", USU_NUMCON=" + USU_NUMCON + ", USU_QTDCON=" + USU_QTDCON + ", USU_USUCON=" + USU_USUCON + ", USU_DATCON=" + USU_DATCON + ", USU_SEQCON=" + USU_SEQCON + ", USU_HORCON=" + USU_HORCON + ", USU_ETIQUETA=" + USU_ETIQUETA + ", USU_INDBIP=" + USU_INDBIP + ", USU_OBSBIP=" + USU_OBSBIP + '}';
    }
    
    
    
    public int getUSU_CODEMP() {
        return USU_CODEMP;
    }

    public void setUSU_CODEMP(int USU_CODEMP) {
        this.USU_CODEMP = USU_CODEMP;
    }

    public String getUSU_DATINV() {
        return USU_DATINV;
    }

    public void setUSU_DATINV(String USU_DATINV) {
        this.USU_DATINV = USU_DATINV;
    }

    public String getUSU_CODDEP() {
        return USU_CODDEP;
    }

    public void setUSU_CODDEP(String USU_CODDEP) {
        this.USU_CODDEP = USU_CODDEP;
    }

    public String getUSU_CODPRO() {
        return USU_CODPRO;
    }

    public void setUSU_CODPRO(String USU_CODPRO) {
        this.USU_CODPRO = USU_CODPRO;
    }

    public String getUSU_CODDER() {
        return USU_CODDER;
    }

    public void setUSU_CODDER(String USU_CODDER) {
        this.USU_CODDER = USU_CODDER;
    }

    public int getUSU_NUMCON() {
        return USU_NUMCON;
    }

    public void setUSU_NUMCON(int USU_NUMCON) {
        this.USU_NUMCON = USU_NUMCON;
    }

    public Float getUSU_QTDCON() {
        return USU_QTDCON;
    }

    public void setUSU_QTDCON(Float USU_QTDCON) {
        this.USU_QTDCON = USU_QTDCON;
    }

    public int getUSU_USUCON() {
        return USU_USUCON;
    }

    public void setUSU_USUCON(int USU_USUCON) {
        this.USU_USUCON = USU_USUCON;
    }

    public String getUSU_DATCON() {
        return USU_DATCON;
    }

    public void setUSU_DATCON(String USU_DATCON) {
        this.USU_DATCON = USU_DATCON;
    }

    public int getUSU_SEQCON() {
        return USU_SEQCON;
    }

    public void setUSU_SEQCON(int USU_SEQCON) {
        this.USU_SEQCON = USU_SEQCON;
    }

    public String getUSU_HORCON() {
        return USU_HORCON;
    }

    public void setUSU_HORCON(String USU_HORCON) {
        this.USU_HORCON = USU_HORCON;
    }

    public String getUSU_ETIQUETA() {
        return USU_ETIQUETA;
    }

    public void setUSU_ETIQUETA(String USU_ETIQUETA) {
        this.USU_ETIQUETA = USU_ETIQUETA;
    }

    public String getUSU_INDBIP() {
        return USU_INDBIP;
    }

    public void setUSU_INDBIP(String USU_INDBIP) {
        this.USU_INDBIP = USU_INDBIP;
    }

    public String getUSU_OBSBIP() {
        return USU_OBSBIP;
    }

    public void setUSU_OBSBIP(String USU_OBSBIP) {
        this.USU_OBSBIP = USU_OBSBIP;
    }

    
}
