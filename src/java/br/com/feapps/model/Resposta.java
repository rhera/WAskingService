/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.feapps.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author F.Einstein
 */
@Entity
@Table(name = "resposta")
@XmlRootElement
/*@NamedQueries({
@NamedQuery(name = "Resposta.findAll", query = "SELECT * FROM resposta u"),
@NamedQuery(name = "Resposta.findById", query = "SELECT * FROM resposta u WHERE u.id = :id"),
@NamedQuery(name = "Resposta.findByResposta", query = "SELECT * FROM resposta u WHERE u.resposta = :resposta")})*/
public class Resposta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Size(max = 2147483647)
    private String resposta;
    
    @OneToOne (cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Area area;
    
    @OneToOne (cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    SubArea subArea;
    

    public Resposta() {
        this.id = 0;
        this.resposta = "";
        area = new Area();
        subArea = new SubArea();
    }

    public Resposta(int id, String resposta, Area area, SubArea subArea) {
        this.id = id;
        this.resposta = resposta;
        this.area = area;
        this.subArea = subArea;
    }
    
    public Resposta(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public SubArea getSubArea() {
        return subArea;
    }

    public void setSubArea(SubArea subArea) {
        this.subArea = subArea;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Resposta)) {
            return false;
        }
        Resposta other = (Resposta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //List<Usuario> usuarios = this.usuarios;
        String[] vectorBuffer = new String[6];

        vectorBuffer[0] = String.valueOf(this.getId());
        vectorBuffer[1] = this.getResposta();
        vectorBuffer[2] = String.valueOf(this.getArea().getId());
        vectorBuffer[3] = this.getArea().getNome();
        vectorBuffer[4] = String.valueOf(this.getSubArea().getId());
        vectorBuffer[5] = this.getSubArea().getNome();

        String buffer="";
        for(int i = 0; i < vectorBuffer.length; i++) {
            if(i!=(vectorBuffer.length-1))
                buffer=buffer+vectorBuffer[i]+"$flag";
            else
                buffer=buffer+vectorBuffer[i];
        }

        return buffer;
    }

    public void toResposta(String str) {
        String[] vectorBuffer = str.split(Pattern.quote("$flag"));

        this.setId(Integer.parseInt(vectorBuffer[0]));
        this.setResposta(vectorBuffer[1]);
        Area a = new Area(Integer.parseInt(vectorBuffer[2]), vectorBuffer[3]);
        this.setArea(a);
        SubArea sb = new SubArea(Integer.parseInt(vectorBuffer[4]), vectorBuffer[5]);
        this.setSubArea(sb);
    }
}

