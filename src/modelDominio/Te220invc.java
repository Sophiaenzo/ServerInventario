package modelDominio;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author wilson.simoes
 */
public class Te220invc implements Serializable{
    private static final long serialVersionUID = 123456789L;
    private int usu_codemp;
    private Date usu_datinv;
    private String usu_coddep;
    private int usu_ultcon;
    private String usu_blomov;

    public Te220invc(int usu_codemp, Date usu_datinv, String usu_coddep, int usu_ultcon, String usu_blomov) {
        this.usu_codemp = usu_codemp;
        this.usu_datinv = usu_datinv;
        this.usu_coddep = usu_coddep;
        this.usu_ultcon = usu_ultcon;
        this.usu_blomov = usu_blomov;
    }

    public int getUsu_codemp() {
        return usu_codemp;
    }

    public void setUsu_codemp(int usu_codemp) {
        this.usu_codemp = usu_codemp;
    }

    public Date getUsu_datinv() {
        return usu_datinv;
    }

    public void setUsu_datinv(Date usu_datinv) {
        this.usu_datinv = usu_datinv;
    }

    public String getUsu_coddep() {
        return usu_coddep;
    }

    public void setUsu_coddep(String usu_coddep) {
        this.usu_coddep = usu_coddep;
    }

    public int getUsu_ultcon() {
        return usu_ultcon;
    }

    public void setUsu_ultcon(int usu_ultcon) {
        this.usu_ultcon = usu_ultcon;
    }

    public String getUsu_blomov() {
        return usu_blomov;
    }

    public void setUsu_blomov(String usu_blomov) {
        this.usu_blomov = usu_blomov;
    }

    
}
