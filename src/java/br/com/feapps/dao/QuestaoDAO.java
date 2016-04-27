/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.feapps.dao;

import br.com.feapps.model.Area;
import br.com.feapps.model.Questao;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author F.Einstein
 */
public class QuestaoDAO extends DAO {
    private List<Questao> questoes;
    
    public QuestaoDAO () {
        questoes = new ArrayList<>();
    }
    
    public void inserir(Questao q) {
        try {
            this.getSessao().save(q);
            this.commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("QuestãoDAO > inserir > não inserido");
        } finally {
            //this.closeSession();
        }
    }
    
    public void remover(Questao a) {
        try {
            this.getSessao().delete(a);
            this.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //this.closeSession();
        }
    }
    
    public List<Questao> getLista() {
        List<Questao> result = new ArrayList();
        try {
            Criteria crit = this.getSessao().createCriteria(Questao.class);
            result = crit.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //this.closeSession();
            return result;
        }
    }
    
    public List<Questao> getBuscaQuestoes(String busca, String tipoBusca){
        List result = new ArrayList();
        
        Criteria crit2 = this.getSessao().createCriteria(Questao.class); //sessao.createCriteria(Contato.class);
       
        String busca2 = "%" + busca + "%";
        crit2.add(Restrictions.ilike(tipoBusca, busca2));
        result = crit2.list();
        
        //this.closeSession();
        
        return result;
    }
    
    public Questao getQuestao(String id) {
        Questao userRet = new Questao();
        
        Criteria crit = this.getSessao().createCriteria(Questao.class);
        
        crit.add(Restrictions.eq("id", id));

        userRet = (Questao) crit.uniqueResult();
        
        //this.closeSession();
        
        return userRet;
    }
    
    public List<Questao> getListQuestoes (int numQuestoes, int idArea, int idSubArea) {
        questoes = new ArrayList<>();
        
        Criteria criteria = this.getSessao().createCriteria(Questao.class);
        criteria.add(Restrictions.eq("area.id", idArea));
        if (idSubArea != 0)
            criteria.add(Restrictions.eq("subArea.id", idSubArea));
        criteria.add(Restrictions.sqlRestriction("1=1 order by rand()"));
        criteria.setMaxResults(numQuestoes);
        questoes = criteria.list();
        
        //this.closeSession();
        
        return questoes;
    }
    
    public boolean ItIsInTheDatabase (String key, String value) {
        Questao ret = new Questao();
        
        Criteria crit = this.getSessao().createCriteria(Questao.class);
        
        crit.add(Restrictions.eq(key, value));

        ret = (Questao) crit.uniqueResult();
        
        //this.closeSession();
        
        if (ret != null)
            return true;
        else
            return false;
    }

    public void atualizar(Questao a) {
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
