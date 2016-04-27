/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.feapps.model;

import java.io.Serializable;
import java.util.regex.Pattern;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author F.Einstein
 */
@Entity
@Table(name = "ranking_temp")
@XmlRootElement
/*@NamedQueries({
@NamedQuery(name = "Questoes.findAll", query = "SELECT * FROM questao u"),
@NamedQuery(name = "Questoes.findById", query = "SELECT * FROM questao u WHERE u.id = :id"),
@NamedQuery(name = "Questoes.findByQuestao", query = "SELECT * FROM questao u WHERE u.questao = :questao")})*/
public class RankingTemp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    private Integer idUser;
    
    private Integer idSala;
    
    private float media5acertos;
    
    private float mediaTempo;

    public RankingTemp() {
        this.idUser = 0;
        this.idSala = 0;
        this.media5acertos = 0;
        this.mediaTempo = 0;
    }

    public RankingTemp(Integer id) {
        this.id = id;
    }

    public RankingTemp(Integer id, Integer idUser, Integer idSala, float media5acertos, float mediaTempo) {
        this.id = id;
        this.idUser = idUser;
        this.idSala = idSala;
        this.media5acertos = media5acertos;
        this.mediaTempo = mediaTempo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdSala() {
        return idSala;
    }

    public void setIdSala(Integer idSala) {
        this.idSala = idSala;
    }

    public float getMedia5acertos() {
        return media5acertos;
    }

    public void setMedia5acertos(float media5acertos) {
        this.media5acertos = media5acertos;
    }

    public float getMediaTempo() {
        return mediaTempo;
    }

    public void setMediaTempo(float mediaTempo) {
        this.mediaTempo = mediaTempo;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUser != null ? idUser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RankingTemp)) {
            return false;
        }
        RankingTemp other = (RankingTemp) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public String toString(Usuario user) {
        String retorno = "";

        retorno += user.toString();
        retorno += "$$";

        retorno += this.getMedia5acertos();
        retorno += "$$";

        retorno += this.getMediaTempo();

        return retorno;
    }

    public void toRanking(String str) {
        String[] strVect = str.split(Pattern.quote("$$"));
        Usuario u = new Usuario();
        u.toUsuario(strVect[0]);
        this.setIdUser(u.getId());
        this.setMedia5acertos(Float.valueOf(strVect[1]));
        this.setMediaTempo(Float.valueOf(strVect[2]));
    }
}

