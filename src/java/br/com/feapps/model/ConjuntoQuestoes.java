/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.feapps.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author F.Einstein
 */
@Entity
@Table(name = "conjunto_questoes")
@XmlRootElement
/*@NamedQueries({
@NamedQuery(name = "ConjuntoQuestoes.findAll", query = "SELECT * FROM ConjuntoQuestoes u"),
@NamedQuery(name = "ConjuntoQuestoes.findById", query = "SELECT * FROM ConjuntoQuestoes u WHERE u.id = :id")})*/
public class ConjuntoQuestoes implements Serializable {

    //private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @ManyToMany(fetch = FetchType.LAZY)
    //@LazyCollection(LazyCollectionOption.FALSE)
    Set<Questao> questoes;
    
    @ManyToMany(fetch = FetchType.LAZY)
    //@LazyCollection(LazyCollectionOption.FALSE)
    Set<Resposta> respostas;
    

    public ConjuntoQuestoes() {
        /*this.id = 0;*/
        this.questoes = new HashSet<>();
        this.respostas = new HashSet<>();
    }

    public ConjuntoQuestoes(Integer id) {
        this.id = id;
        this.questoes = new HashSet<>();
        this.respostas = new HashSet<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Questao> getQuestoes() {
        return questoes;
    }

    public void setQuestoes(Set<Questao> questoes) {
        this.questoes = questoes;
    }

    public Set<Resposta> getRespostas() {
        return respostas;
    }

    public void setRespostas(Set<Resposta> respostas) {
        this.respostas = respostas;
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
        if (!(object instanceof ConjuntoQuestoes)) {
            return false;
        }
        ConjuntoQuestoes other = (ConjuntoQuestoes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    public String[] toVectorString() {
        String[] vectorBuffer = new String[2+questoes.size()+respostas.size()];
        vectorBuffer[0] = String.valueOf(this.getId());

        int i = 1;
        for (Questao q : questoes) {
            vectorBuffer[i++] = q.toString();
        }
        
        vectorBuffer[i++] = "$fimquestoes";

        for (Resposta r : respostas) {
            vectorBuffer[i++] = r.toString();
        }
        
        return vectorBuffer;
    }
    
    @Override
    public String toString() {
        String[] vectorBuffer = this.toVectorString();
        String buffer = "";
        for (int i = 0; i < vectorBuffer.length; i++) {
            buffer += vectorBuffer[i];
            buffer += "%%";
        }
        return buffer;
    }

    public void toConjunto(String vectorBuffer[]) {
        Questao q = new Questao();
        Resposta r = new Resposta();

        this.setId(Integer.parseInt(vectorBuffer[0]));
        
        int i = 1;
        while (!vectorBuffer[i].equals("$fimquestoes")) {
            q = new Questao();
            q.toQuestao(vectorBuffer[i++]);
            questoes.add(q);
        }
        i++;
        
        for (int j = i; j < vectorBuffer.length; j++) {
            r = new Resposta();
            r.toResposta(vectorBuffer[i++]);
            respostas.add(r);
        }
    }
    
    public void toConjunto(String str) {
        String[] vectorBuffer = str.split("%%");
        toConjunto(vectorBuffer);
    }
}