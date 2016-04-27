/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.feapps.dao;

import br.com.feapps.model.Area;
import br.com.feapps.model.Questao;
import br.com.feapps.model.Resposta;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author F.Einstein
 */
public class RespostaDAO extends DAO {
    private List<Resposta> respostas;
    
    public RespostaDAO () {
        respostas = new ArrayList<>();
    }
    
    public void inserir(Resposta q) {
        try {
            this.getSessao().save(q);
            this.commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("RespostaDAO > inserir > não inserido");
        } finally {
            //this.closeSession();
        }
    }
    
    public void remover(Resposta a) {
        try {
            this.getSessao().delete(a);
            this.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //this.closeSession();
        }
    }
    
    public List<Resposta> getLista() {
        List<Resposta> result = new ArrayList();
        try {
            Criteria crit = this.getSessao().createCriteria(Resposta.class);
            result = crit.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //this.closeSession();
            return result;
        }
    }
    
    public List<Resposta> getBuscaResposta(String busca, String tipoBusca){
        List result = new ArrayList();
        
        Criteria crit2 = this.getSessao().createCriteria(Resposta.class); //sessao.createCriteria(Contato.class);
       
        String busca2 = "%" + busca + "%";
        crit2.add(Restrictions.ilike(tipoBusca, busca2));
        result = crit2.list();
        
        //this.closeSession();
        
        return result;
    }
    
    public Resposta getResposta(String id) {
        Resposta userRet = new Resposta();
        
        Criteria crit = this.getSessao().createCriteria(Resposta.class);
        
        crit.add(Restrictions.eq("id", id));

        userRet = (Resposta) crit.uniqueResult();
        
        //this.closeSession();
        
        return userRet;
    }
    
    public List<Resposta> getListRespostas (int numQuestoes, int idArea, int idSubArea) {
        respostas = new ArrayList<>();
        
        Criteria criteria = this.getSessao().createCriteria(Resposta.class);
        
        criteria.add(Restrictions.eq("area.id", idArea));
        if (idSubArea != 0)
            criteria.add(Restrictions.eq("subArea.id", idSubArea));
        criteria.add(Restrictions.sqlRestriction("1=1 order by rand()"));
        criteria.setMaxResults(numQuestoes*3);
        
        respostas = criteria.list();
        
        //this.closeSession();
        
        return respostas;
    }
    
    public boolean ItIsInTheDatabase (String key, String value) {
        Resposta ret = new Resposta();
        
        Criteria crit = this.getSessao().createCriteria(Resposta.class);
        
        crit.add(Restrictions.eq(key, value));

        ret = (Resposta) crit.uniqueResult();
        
        //this.closeSession();
        
        if (ret != null)
            return true;
        else
            return false;
    }

    public void atualizar(Resposta a) {
        try {
            this.getSessao().update(a);
            this.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //this.closeSession();
        }
    }
}
