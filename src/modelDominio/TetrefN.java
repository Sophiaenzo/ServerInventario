package modelDominio;

import java.io.Serializable;

/**
 *
 * @author wilson.simoes
 */
public class TetrefN implements Serializable {
    private static final long serialVersionUID = 123456789L; 
   private int Re_codemp;
   private String Re_datinv;
   private String Re_coddep;
   private String Re_codder;
   private int Re_numcon;
   private String Re_usucon;
   private String Re_datcon;
   private String Re_horcon;
   private int Re_seqcon;
   private String Re_mate;   
   private String Re_codpro;
   private String Re_lote; 
   private String Re_qtdcon;   
   private String Re_status;   
   private String Re_indbip;
   private String Re_obsbip;
   private String Re_etiqueta;
   private boolean Re_bloqueado;
   private int Re_contagem;
   private boolean continua;
   private boolean eti_valida;

    public TetrefN(int Re_codemp, String Re_datinv, String Re_coddep, String Re_codder, int Re_numcon, String Re_usucon, String Re_datcon, String Re_horcon, int Re_seqcon, String Re_mate, String Re_codpro, String Re_lote, String Re_qtdcon, String Re_status, String Re_indbip, String Re_obsbip, String Re_etiqueta, boolean Re_bloqueado, int Re_contagem, boolean continua, boolean eti_valida) {
        this.Re_codemp = Re_codemp;
        this.Re_datinv = Re_datinv;
        this.Re_coddep = Re_coddep;
        this.Re_codder = Re_codder;
        this.Re_numcon = Re_numcon;
        this.Re_usucon = Re_usucon;
        this.Re_datcon = Re_datcon;
        this.Re_horcon = Re_horcon;
        this.Re_seqcon = Re_seqcon;
        this.Re_mate = Re_mate;
        this.Re_codpro = Re_codpro;
        this.Re_lote = Re_lote;
        this.Re_qtdcon = Re_qtdcon;
        this.Re_status = Re_status;
        this.Re_indbip = Re_indbip;
        this.Re_obsbip = Re_obsbip;
        this.Re_etiqueta = Re_etiqueta;
        this.Re_bloqueado = Re_bloqueado;
        this.Re_contagem = Re_contagem;
        this.continua = continua;
        this.eti_valida = eti_valida;
    }

    

   
   public TetrefN(){
   }

    public String getRe_etiqueta() {
        return Re_etiqueta;
    }

    public void setRe_etiqueta(String Re_etiqueta) {
        this.Re_etiqueta = Re_etiqueta;
    }

    public int getRe_codemp() {
        return Re_codemp;
    }

    public void setRe_codemp(int Re_codemp) {
        this.Re_codemp = Re_codemp;
    }

    public String getRe_datinv() {
        return Re_datinv;
    }

    public void setRe_datinv(String Re_datinv) {
        this.Re_datinv = Re_datinv;
    }

    public String getRe_coddep() {
        return Re_coddep;
    }

    public void setRe_coddep(String Re_coddep) {
        this.Re_coddep = Re_coddep;
    }

    public String getRe_codder() {
        return Re_codder;
    }

    public void setRe_codder(String Re_codder) {
        this.Re_codder = Re_codder;
    }

    public int getRe_numcon() {
        return Re_numcon;
    }

    public void setRe_numcon(int Re_numcon) {
        this.Re_numcon = Re_numcon;
    }

    public String getRe_usucon() {
        return Re_usucon;
    }

    public void setRe_usucon(String Re_usucon) {
        this.Re_usucon = Re_usucon;
    }

    public String getRe_datcon() {
        return Re_datcon;
    }

    public void setRe_datcon(String Re_datcon) {
        this.Re_datcon = Re_datcon;
    }

    public String getRe_horcon() {
        return Re_horcon;
    }

    public void setRe_horcon(String Re_horcon) {
        this.Re_horcon = Re_horcon;
    }

    public int getRe_seqcon() {
        return Re_seqcon;
    }

    public void setRe_seqcon(int Re_seqcon) {
        this.Re_seqcon = Re_seqcon;
    }

    public String getRe_mate() {
        return Re_mate;
    }

    public void setRe_mate(String Re_mate) {
        this.Re_mate = Re_mate;
    }

    public String getRe_codpro() {
        return Re_codpro;
    }

    public void setRe_codpro(String Re_codpro) {
        this.Re_codpro = Re_codpro;
    }

    public String getRe_lote() {
        return Re_lote;
    }

    public void setRe_lote(String Re_lote) {
        this.Re_lote = Re_lote;
    }

    public String getRe_qtdcon() {
        return Re_qtdcon;
    }

    public void setRe_qtdcon(String Re_qtdcon) {
        this.Re_qtdcon = Re_qtdcon;
    }

    public String getRe_status() {
        return Re_status;
    }

    public void setRe_status(String Re_status) {
        this.Re_status = Re_status;
    }

    public String getRe_indbip() {
        return Re_indbip;
    }

    public void setRe_indbip(String Re_indbip) {
        this.Re_indbip = Re_indbip;
    }

    public String getRe_obsbip() {
        return Re_obsbip;
    }

    public void setRe_obsbip(String Re_obsbip) {
        this.Re_obsbip = Re_obsbip;
    }

    public boolean isRe_bloqueado() {
        return Re_bloqueado;
    }

    public void setRe_bloqueado(boolean Re_bloqueado) {
        this.Re_bloqueado = Re_bloqueado;
    }

    public int getRe_contagem() {
        return Re_contagem;
    }

    public void setRe_contagem(int Re_contagem) {
        this.Re_contagem = Re_contagem;
    }

    public boolean isContinua() {
        return continua;
    }

    public void setContinua(boolean continua) {
        this.continua = continua;
    }

    public boolean isEti_valida() {
        return eti_valida;
    }

    public void setEti_valida(boolean eti_valida) {
        this.eti_valida = eti_valida;
    }

  
   
}
