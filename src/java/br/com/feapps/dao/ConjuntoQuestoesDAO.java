/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.feapps.dao;

import br.com.feapps.model.ConjuntoQuestoes;
import br.com.feapps.model.Questao;
import br.com.feapps.model.Resposta;
import br.com.feapps.model.Sala;
import br.com.feapps.util.CONST;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author F.Einstein
 */
public class ConjuntoQuestoesDAO extends DAO {
    private List<ConjuntoQuestoes> conjuntoQuestoes;
    
    public ConjuntoQuestoesDAO() {
        conjuntoQuestoes = new ArrayList<>();
    }
    
    public void inserir(ConjuntoQuestoes q) {
        try {
            this.getSessao().save(q);
            this.commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ConjuntoQuestoesDAO > inserir > não inserido");
        } finally {
            //this.closeSession();
        }
    }
    
    public void remover(ConjuntoQuestoes a) {
        try {
            this.getSessao().delete(a);
            this.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //this.closeSession();
        }
    }
    
    public List<ConjuntoQuestoes> getLista() {
        List<ConjuntoQuestoes> result = new ArrayList();
        try {
            Criteria crit = this.getSessao().createCriteria(ConjuntoQuestoes.class);
            result = crit.list();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //this.closeSession();
            return result;
        }
    }
    
    public List<ConjuntoQuestoes> getBuscaConjuntoQuestoes(String busca, String tipoBusca){
        List result = new ArrayList();
        
        Criteria crit2 = this.getSessao().createCriteria(ConjuntoQuestoes.class); //sessao.createCriteria(Contato.class);
       
        String busca2 = "%" + busca + "%";
        crit2.add(Restrictions.ilike(tipoBusca, busca2));
        result = crit2.list();
        
        //this.closeSession();
        
        return result;
    }
    
    public ConjuntoQuestoes novoConjuntoQuestoes(int numQuestoes, int idArea, int idSubArea) {
        ConjuntoQuestoes conjRet = new ConjuntoQuestoes();
        QuestaoDAO qDAO = new QuestaoDAO();
        RespostaDAO rDAO = new RespostaDAO();
        
        Criteria crit = this.getSessao().createCriteria(Questao.class);
        
        List<Questao> qList = qDAO.getListQuestoes(numQuestoes, idArea, idSubArea);
        List<Resposta> rList = rDAO.getListRespostas(numQuestoes, idArea, idSubArea);
        
        conjRet.setQuestoes(new HashSet<>(qList));
        conjRet.setRespostas(new HashSet<>(rList));
        
        this.inserir(conjRet);
        
        //this.closeSession();
        
        return conjRet;
    }
    
    public ConjuntoQuestoes getConjuntoById(int idConjQuest) {
        ConjuntoQuestoes conjunto = new ConjuntoQuestoes();
        
        Criteria crit = this.getSessao().createCriteria(ConjuntoQuestoes.class);
        
        crit.add(Restrictions.eq("id", idConjQuest));

        conjunto = (ConjuntoQuestoes) crit.uniqueResult();
        
        //this.closeSession();
        
        return conjunto;
    }
    
    public String[] getVectorConjuntoById(int idConjQuest) {
        ConjuntoQuestoes conjunto = new ConjuntoQuestoes();
        String[] retorno = null;
        
        Criteria crit = this.getSessao().createCriteria(ConjuntoQuestoes.class);
        
        crit.add(Restrictions.eq("id", idConjQuest));

        conjunto = (ConjuntoQuestoes) crit.uniqueResult();
        
        retorno = conjunto.toVectorString();
        
        return retorno;
    }
    
    public boolean ItIsInTheDatabase (String key, String value) {
        ConjuntoQuestoes ret = new ConjuntoQuestoes();
        
        Criteria crit = this.getSessao().createCriteria(ConjuntoQuestoes.class);
        
        crit.add(Restrictions.eq(key, value));

        ret = (ConjuntoQuestoes) crit.uniqueResult();
        
        //this.closeSession();
        
        if (ret != null)
            return true;
        else
            return false;
    }

    public void atualizar(ConjuntoQuestoes a) {
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
