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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author F.Einstein
 */
@Entity
@Table(name = "questao")
@XmlRootElement
/*@NamedQueries({
@NamedQuery(name = "Questoes.findAll", query = "SELECT * FROM questao u"),
@NamedQuery(name = "Questoes.findById", query = "SELECT * FROM questao u WHERE u.id = :id"),
@NamedQuery(name = "Questoes.findByQuestao", query = "SELECT * FROM questao u WHERE u.questao = :questao")})*/
public class Questao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "id")
     Integer id;
    
    @Size(max = 2147483647)
    private String questao;
    
    @ManyToOne
    Area area;
    
    @ManyToOne
    SubArea subArea;
    
    @OneToOne (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Resposta resposta;

    public Questao() {
        this.id = 0;
        this.questao = "";
        this.area = new Area();
        this.subArea = new SubArea();
        this.resposta = new Resposta();
    }

    public Questao(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestao() {
        return questao;
    }

    public void setQuestao(String questao) {
        this.questao = questao;
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

    public Resposta getResposta() {
        return resposta;
    }

    public void setResposta(Resposta resposta) {
        this.resposta = resposta;
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
        if (!(object instanceof Questao)) {
            return false;
        }
        Questao other = (Questao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //List<Usuario> usuarios = this.usuarios;
        String[] vectorBuffer = new String[8];

        vectorBuffer[0] = String.valueOf(this.getId());
        vectorBuffer[1] = this.getQuestao();
        vectorBuffer[2] = String.valueOf(this.getArea().getId());
        vectorBuffer[3] = this.getArea().getNome();
        vectorBuffer[4] = String.valueOf(this.getSubArea().getId());
        vectorBuffer[5] = this.getSubArea().getNome();

        vectorBuffer[6] = String.valueOf(this.getResposta().getId());
        vectorBuffer[7] = this.getResposta().getResposta();

        String buffer="";
        for(int i = 0; i < vectorBuffer.length; i++) {
            if(i!=(vectorBuffer.length-1))
                buffer=buffer+vectorBuffer[i]+"$flag";
            else
                buffer=buffer+vectorBuffer[i];
        }

        return buffer;
    }

    public void toQuestao(String str) {
        String[] vectorBuffer = str.split(Pattern.quote("$flag"));

        this.setId(Integer.parseInt(vectorBuffer[0]));
        this.setQuestao(vectorBuffer[1]);
        Area a = new Area(Integer.parseInt(vectorBuffer[2]), vectorBuffer[3]);
        this.setArea(a);
        SubArea sb = new SubArea(Integer.parseInt(vectorBuffer[4]), vectorBuffer[5]);
        this.setSubArea(sb);
        this.setResposta(new Resposta(Integer.parseInt(vectorBuffer[6]), vectorBuffer[7], a, sb));
    }
}
