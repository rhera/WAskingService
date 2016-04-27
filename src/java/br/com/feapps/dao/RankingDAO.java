/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.feapps.dao;

import br.com.feapps.model.ConjuntoQuestoes;
import br.com.feapps.model.Questao;
import br.com.feapps.model.Ranking;
import br.com.feapps.model.RankingTemp;
import br.com.feapps.model.Resposta;
import br.com.feapps.model.Sala;
import br.com.feapps.model.Usuario;
import br.com.feapps.util.CONST;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.SQLGrammarException;

/**
 *
 * @author F.Einstein
 */
public class RankingDAO extends DAO {
    private List<Ranking> ranking;
    private List<RankingTemp> rankingTemp;
    
    public RankingDAO () {
        ranking = new ArrayList<>();
        rankingTemp = new ArrayList<>();
    }
    
    public void inserirRanking(Ranking r) {
        try {
            this.getSessao().save(r);
            this.commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("rankingDAO > inserirRanking > não inserido");
        } finally {
            //this.closeSession();
        }
    }
    
    public void inserirRankingTemp (RankingTemp r) {
        try {
            this.getSessao().save(r);
            this.commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("rankingDAO > inserirRankingTemp > não inserido");
        } finally {
            //this.closeSession();
        }
    }
    
    public void removerRanking (Ranking a) {
        try {
            this.getSessao().delete(a);
            this.commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("rankingDAO > removerRanking > não removido");
        } finally {
            //this.closeSession();
        }
    }
    
    public void removerRankingTemp (RankingTemp a) {
        try {
            this.getSessao().delete(a);
            this.commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("rankingDAO > removerRankingTemp > não removido");
        } finally {
            //this.closeSession();
        }
    }
    
    public List<Ranking> getListaRanking() {
        List<Ranking> result = new ArrayList();
        try {
            Criteria crit = this.getSessao().createCriteria(Ranking.class);
            //crit.add(Restrictions.sqlRestriction("order by mediatempo desc, media5acertos desc"));
            crit.addOrder(Order.desc("media5acertos"));
            crit.addOrder(Order.asc("mediaTempo"));
            result = crit.list();
            if (result == null)
                result = new ArrayList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //this.closeSession();
            return result;
        }
    }
    
    public List<RankingTemp> getListaRankingTemp() {
        List<RankingTemp> result = new ArrayList();
        try {
            Criteria crit = this.getSessao().createCriteria(RankingTemp.class);
            //crit.add(Restrictions.sqlRestriction("order by mediatempo desc, media5acertos desc"));
            crit.addOrder(Order.desc("media5acertos"));
            crit.addOrder(Order.asc("mediaTempo"));
            result = crit.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //this.closeSession();
            return result;
        }
    }
    
    public List<Ranking> getBuscaRanking(String busca, String tipoBusca){
        List result = new ArrayList();
        
        Criteria crit2 = this.getSessao().createCriteria(Ranking.class); //sessao.createCriteria(Contato.class);
       
        String busca2 = "%" + busca + "%";
        crit2.add(Restrictions.ilike(tipoBusca, busca2));
        result = crit2.list();
        
        //this.closeSession();
        
        return result;
    }
    
    public List<RankingTemp> getBuscaRankingTemp(String busca, String tipoBusca){
        List result = new ArrayList();
        
        Criteria crit2 = this.getSessao().createCriteria(RankingTemp.class); //sessao.createCriteria(Contato.class);
       
        String busca2 = "%" + busca + "%";
        crit2.add(Restrictions.ilike(tipoBusca, busca2));
        result = crit2.list();
        
        //this.closeSession();
        
        return result;
    }
    
    public Ranking getRanking(Integer id) {
        Ranking userRet = new Ranking();
        
        Criteria crit = this.getSessao().createCriteria(Ranking.class);
        
        crit.add(Restrictions.eq("id", id));

        userRet = (Ranking) crit.uniqueResult();
        
        //this.closeSession();
        
        return userRet;
    }
    
    public Ranking getRankingForIdUser(Integer idUser) {
        Ranking userRet = new Ranking();
        
        Criteria crit = this.getSessao().createCriteria(Ranking.class);
        
        crit.add(Restrictions.eq("idUser", idUser));

        userRet = (Ranking) crit.uniqueResult();
        
        //this.closeSession();
        
        return userRet;
    }
    
    public RankingTemp getRankingTempForIdUser(Integer id) {
        RankingTemp userRet = new RankingTemp();
        
        Criteria crit = this.getSessao().createCriteria(RankingTemp.class);
        
        crit.add(Restrictions.eq("idUser", id));

        userRet = (RankingTemp) crit.uniqueResult();
        
        //this.closeSession();
        
        return userRet;
    }
    
    public List<RankingTemp> getListRamTemps (int salaId) {
        List<RankingTemp> listRamTemps = new ArrayList<>();
        
        Criteria crit = this.getSessao().createCriteria(RankingTemp.class);
        
        /*for (int i = 0; i < ids.length; i++)
            crit.add(Restrictions.eq("id", ids[i]));*/
        crit.add(Restrictions.eq("idSala", salaId));
        
        crit.addOrder(Order.desc("mediaTempo"));
        crit.addOrder(Order.desc("media5acertos"));
        
        listRamTemps = crit.list();
        
        //this.closeSession();
        
        return listRamTemps;
    }
    
    public String[] getListRamTempsForSala (int idSala) {
        List<RankingTemp> listRamTemps = new ArrayList<>();
        String[] retorno;
        
        Criteria crit = this.getSessao().createCriteria(RankingTemp.class);
        
        crit.add(Restrictions.eq("idSala", idSala));
        
        crit.addOrder(Order.desc("mediaTempo"));
        crit.addOrder(Order.desc("media5acertos"));
        
        listRamTemps = crit.list();
        
        retorno = new String[listRamTemps.size()];
        
        for (int i = 0; i < listRamTemps.size(); i++) {
            Criteria critUser = this.getSessao().createCriteria(Usuario.class);
            //System.out.println(listRamTemps.get(i).getIdUser());
            critUser.add(Restrictions.eq("id", listRamTemps.get(i).getIdUser()));
            Usuario user = (Usuario) critUser.uniqueResult();
            retorno[i] = listRamTemps.get(i).toString(user);
        }
        
        //this.closeSession();
        
        return retorno;
    }
    
    /*public List<Questao> getListQuestoes (int numQuestoes, int idArea, int idSubArea) {
        Criteria criteria = DAO.getSessao().createCriteria(Questao.class);
        criteria.add(Restrictions.eq("area.id", idArea));
        if (idSubArea != 0)
            criteria.add(Restrictions.eq("subArea.id", idSubArea));
        criteria.add(Restrictions.sqlRestriction("1=1 order by random()"));
        criteria.setMaxResults(numQuestoes);
        return criteria.list();
    }*/
    
    public boolean ItIsInTheDatabase (String key, int value, int tipo) {
        Criteria crit;
        
        if (tipo == CONST.RANKING)
            crit = this.getSessao().createCriteria(Ranking.class);
        else
            crit = this.getSessao().createCriteria(RankingTemp.class);
        
        crit.add(Restrictions.eq(key, value));
        
        if (tipo == CONST.RANKING) {
            Ranking ret = new Ranking();
            ret = (Ranking) crit.uniqueResult();
            
            //this.closeSession();
            
            if (ret != null)
                return true;
            else
                return false;
        } else {
            RankingTemp ret = new RankingTemp();
            ret = (RankingTemp) crit.uniqueResult();
            
            //this.closeSession();
            
            if (ret != null)
                return true;
            else
                return false;
        }
    }

    public void atualizarRanking(Ranking a) {
        try {
            this.getSessao().update(a);
            this.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //this.closeSession();
        }
    }
    
    public void atualizarRankingTemp(RankingTemp a) {
        try {
            this.getSessao().update(a);
            this.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //this.closeSession();
        }
    }
    
    public void SalvarRankings (int salaId, int userId) {
        List<RankingTemp> listRankTemp = this.getListRamTemps(salaId);
        
        Sala sala = new Sala();
        Criteria crit = this.getSessao().createCriteria(Sala.class);
        crit.add(Restrictions.eq("id", salaId));
        sala = (Sala) crit.uniqueResult();
        
        Usuario user = new Usuario();
        crit = this.getSessao().createCriteria(Usuario.class);
        crit.add(Restrictions.eq("id", userId));
        user = (Usuario) crit.uniqueResult();
        
        Set<Usuario> listUsers = new HashSet<Usuario>();
        if (sala != null)
            listUsers = sala.getListUser();
        
        //Salva ranking temporário do usuário
        RankingTemp rTemp = this.getRankingTempForIdUser(user.getId());
        Ranking r = new Ranking();
        if (this.ItIsInTheDatabase("idUser", user.getId(), CONST.RANKING)) {
            r = this.getRankingForIdUser(user.getId());
            float media = (r.getMedia5acertos() + rTemp.getMedia5acertos())/2;
            r.setMedia5acertos(media);
            media = (r.getMediaTempo() + rTemp.getMediaTempo())/2;
            r.setMediaTempo(media);
            this.atualizarRanking(r);
        } else {
            r = new Ranking(rTemp);
            this.inserirRanking(r);
        }
        
        //Retirando usuário da sala e excluindo rankings temporários apenas se não há mais usuários na sala
        sala.getListUser().remove(user);
        this.getSessao().update(sala);
        this.commit();
        
        if (sala.getListUser().isEmpty()) {
            for (RankingTemp rt : listRankTemp) {
                this.removerRankingTemp(rt);
                this.commit();
            }
        }
        
        if (sala.getListUser().isEmpty()) {
            sala = new Sala();
            crit = this.getSessao().createCriteria(Sala.class);
            crit.add(Restrictions.eq("id", salaId));
            sala = (Sala) crit.uniqueResult();

            ConjuntoQuestoes c = sala.getConjuntoQuestoes();
            //Removendo conjunto de questões
            c.setRespostas(new HashSet<Resposta>());
            this.getSessao().update(c);
            this.commit();
            c.setQuestoes(new HashSet<Questao>());
            this.getSessao().update(c);
            this.commit();
            sala.setConjuntoQuestoes(null);
            this.getSessao().update(sala);
            this.commit();
            getSessao().delete(c);
            this.commit();

            //Apagando area e subárea
            sala.setArea(null);
            sala.setSubArea(null);
            this.getSessao().update(sala);
            this.commit();

            //Apagando usuários da sala
            Set<Usuario> listUser = new HashSet<>(sala.getListUser());
            for (Usuario u : listUser) {
                u.setSala(null);
                this.getSessao().update(u);
                this.commit();
            }
            sala.setListUser(new HashSet<Usuario>());
            this.getSessao().update(sala);
            this.commit();

            this.getSessao().delete(sala);
            this.commit();
        }
        //this.commit();
    }
    
    public String[] ListarRankings () {
        List<Ranking> list = new ArrayList();
        list = this.getListaRanking();
        String[] retorno = new String[list.size()];
        
        int i = 0;
        Criteria crit = null;
        for (Ranking r : list) {
            Usuario user = new Usuario();
            crit = this.getSessao().createCriteria(Usuario.class);
            crit.add(Restrictions.eq("id", r.getIdUser()));
            user = (Usuario) crit.uniqueResult();
            retorno[i++] = r.toString(user);
        }
        
        return retorno;
    }
}
