/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelDominio;

import java.io.Serializable;

/**
 *
 * @author wilson.simoes
 */
public class Te220cons implements Serializable {
   private static final long serialVersionUID = 123456789L;
   private int USU_NUMCON;

    public Te220cons(int USU_NUMCON) {
        this.USU_NUMCON = USU_NUMCON;
    }

    public int getUSU_NUMCON() {
        return USU_NUMCON;
    }

    public void setUSU_NUMCON(int USU_NUMCON) {
        this.USU_NUMCON = USU_NUMCON;
    }
    
    
   
    
}
