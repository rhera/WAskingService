/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.feapps.dao;

import br.com.feapps.model.Area;
import br.com.feapps.model.ConjuntoQuestoes;
import br.com.feapps.model.Questao;
import br.com.feapps.model.Resposta;
import br.com.feapps.model.Sala;
import br.com.feapps.model.SubArea;
import br.com.feapps.model.Usuario;
import br.com.feapps.util.CONST;
import br.com.feapps.util.STR;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.StaleStateException;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author F.Einstein
 */
public class SalaDAO extends DAO {
    private List<Sala> listSala;
    
    public SalaDAO () {
        listSala = new ArrayList<>();
    }
    
    public void inserir(Sala a) {
        try {
            this.getSessao().save(a);
            this.commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("SalaDAO > inserir > não inserido");
        } finally {
            //this.closeSession();
        }
    }
            
    public void remover(Sala a) {
        try {
            this.getSessao().delete(a);
            this.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //this.closeSession();
        }
    }
    
    public List<Sala> getLista() {
        List<Sala> result = new ArrayList();
        try {
            Criteria crit = this.getSessao().createCriteria(Sala.class);
            
            crit.add(Restrictions.ne("id", 0));
            
            result = crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
            result = crit.list();
            //System.out.println("SalaDAO: " + result.get(0).getNome());
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //this.closeSession();
            return result;
        }
    }
    
    
    public String[] getListVectStr() {
        List<Sala> listSalas = new ArrayList();
        String[] response = null;
        try {
            Criteria crit = this.getSessao().createCriteria(Sala.class);
            
            crit.add(Restrictions.ne("id", 0));
            
            listSalas = crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
            
            List<String[]> listVectStr = new ArrayList<>();
            
            //int i = 0;
            for (Sala s : listSalas) {
                //Apagando salas vazias
                if (s.getListUser().isEmpty()) {
                    this.getSessao().delete(s);
                    this.commit();
                } else {
                    listVectStr.add(s.toVectorString());
                }
            }
            
            //String[] retorno = null;
            boolean entrou = false;
            for (String[] vecstr : listVectStr) {
                entrou = true;
                if (response == null) {
                    response = vecstr;
                } else {
                    response = STR.Concatenar(response, vecstr);
                }
            }
            if (entrou) {
                String[] newvecstr = new String[1];
                newvecstr[0] = "0";
                response = STR.Concatenar(response, newvecstr);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //this.closeSession();
            return response;
        }
    }
    
    public List<String> getListaStr() {
        List<Sala> result = new ArrayList();
        List<String> resultFinal = new ArrayList();
        try {
            Criteria crit = this.getSessao().createCriteria(Sala.class);
            
            result = crit.list();
            
            for (Sala sa : result) {
                resultFinal.add(sa.getNome());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //this.closeSession();
            return resultFinal;
        }
    }
    
    public List<Sala> getBuscaSalas(String busca, String tipoBusca){
        List result = new ArrayList();
        
        Criteria crit2 = this.getSessao().createCriteria(Sala.class); //sessao.createCriteria(Contato.class);
       
        String busca2 = "%" + busca + "%";
        crit2.add(Restrictions.ilike(tipoBusca, busca2));
        result = crit2.list();
        
        //this.closeSession();
        
        return result;
    }
    
    public Sala getSala(int id) {
        Sala userRet = new Sala();
        
        Criteria crit = this.getSessao().createCriteria(Sala.class);
        
        crit.add(Restrictions.eq("id", id));

        userRet = (Sala) crit.uniqueResult();
        
        //this.closeSession();
        
        return userRet;
    }
    
    public String[] getSalaVectorStr(int id) {
        String[] retorno = null;
        Sala sala = new Sala();
        
        Criteria crit = this.getSessao().createCriteria(Sala.class);
        
        crit.add(Restrictions.eq("id", id));

        sala = (Sala) crit.uniqueResult();
        
        if (sala != null)
            retorno = sala.toVectorString();
        else {
            sala = new Sala(0);
            retorno = sala.toVectorString();
        }
        //this.closeSession();
        
        return retorno;
    }
    
    public int getNumSalasAtivas () {
        
        List<Sala> listSala = this.getLista();
        int num = 0;
        
        Criteria crit = this.getSessao().createCriteria(Sala.class);
        
        num = crit.list().size();
        
        //this.closeSession();
        
        return num;
    }
    
    public boolean ItIsInTheDatabase (String key, String value) {
        Sala salaRet = new Sala();
        
        Criteria crit = this.getSessao().createCriteria(Sala.class);
        
        crit.add(Restrictions.eq(key, value));

        salaRet = (Sala) crit.uniqueResult();
        
        //this.closeSession();
        
        if (salaRet != null)
            return true;
        else
            return false;
    }

    public boolean atualizar(Sala s) {
        try {
            this.getSessao().update(s);
            this.commit();
        } catch (StaleStateException sse) {
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //this.closeSession();
        
        return true;
    }
    
    public String[] CriarSala (int numQuestoes, String strSalas) {
        Sala sala = new Sala();
        String listSalas[] = strSalas.split("@@");
        boolean cancel = false;
        String[] retorno = null;
        
        sala.setNome(listSalas[0]);
        if (this.ItIsInTheDatabase(CONST.SALATABNOME, sala.getNome())) {
            sala.setId(0);
            return sala.toVectorString();
        }
        
        sala.setTipo(listSalas[1]);
        sala.setSenha(listSalas[2]);
        
        Integer idArea = Integer.parseInt(listSalas[3]);
        Area area = new Area(Integer.parseInt(listSalas[3]), listSalas[4]);
        List<Area> lAreas = new ArrayList();
        //pegando lista de subareas
        //List<SubArea> lsa = new ArrayList<>();//subAreaDAO.getSubAreasForIdArea(a.getId());
        Criteria crit = this.getSessao().createCriteria(Area.class);
        lAreas = crit.list();
        for (Area a : lAreas) {
            if (a.getId().equals(idArea))
                area = a;
        }
        //lsa = new ArrayList<>(area.getSubArea());
        sala.setArea(area);
        
        crit = this.getSessao().createCriteria(SubArea.class);
        crit.add(Restrictions.eq("id", Integer.parseInt(listSalas[5])));
        SubArea sa = (SubArea) crit.uniqueResult();
        
        sala.setSubArea(sa);
        /*this.getSessao().save(sala);
        this.commit();*/
        
        sala.setMaxUserSala(sala.getTipoInt()*2);
        
        Usuario u = new Usuario();
        crit = this.getSessao().createCriteria(Usuario.class);
        int id = Integer.parseInt(listSalas[7]);
        crit.add(Restrictions.eq("id", id));
        u = (Usuario) crit.uniqueResult();
        sala.getListUser().add(u);
        
        ConjuntoQuestoes conjRet = new ConjuntoQuestoes();
        QuestaoDAO qDAO = new QuestaoDAO();
        RespostaDAO rDAO = new RespostaDAO();
        crit = this.getSessao().createCriteria(Questao.class);
        List<Questao> qList = qDAO.getListQuestoes(numQuestoes, idArea, sala.getSubArea().getId());
        List<Resposta> rList = rDAO.getListRespostas(numQuestoes, idArea, sala.getSubArea().getId());
        conjRet.setQuestoes(new HashSet<>(qList));
        conjRet.setRespostas(new HashSet<>(rList));
        this.getSessao().save(conjRet);
        this.commit();
        
        sala.setConjuntoQuestoes(conjRet);
        
        this.getSessao().save(sala);
        this.commit();
        
        u = new Usuario();
        crit = this.getSessao().createCriteria(Usuario.class);
        id = Integer.parseInt(listSalas[7]);
        crit.add(Restrictions.eq("id", id));
        u = (Usuario) crit.uniqueResult();
        u.setSala(sala);
        this.getSessao().update(u);
        this.commit();
        
        retorno = sala.toVectorString();
        
        return retorno;
    }
    
    public String[] AdicionaUsuario (int salaId, String strUser) {
        String[] response = null;
        Usuario user = new Usuario();
        user.toUsuario(strUser);
        
        Sala sala = new Sala();
        Criteria crit = this.getSessao().createCriteria(Sala.class);
        crit.add(Restrictions.eq("id", salaId));
        sala = (Sala) crit.uniqueResult();
        
        if (sala != null) {
            crit = this.getSessao().createCriteria(Usuario.class);
            crit.add(Restrictions.eq("id", user.getId()));
            user = (Usuario) crit.uniqueResult();
            
            if (sala.getListUser().size() < sala.getMaxUserSala()) {
                sala.getListUser().add(user);
                if (sala.getListUser().size() == sala.getMaxUserSala()) {
                    sala.setVisivel(false);
                }
                this.getSessao().update(sala);
                this.commit();

                response = sala.toVectorString();
            } else {
                sala = new Sala(0);
                response = sala.toVectorString();
            }
        } else {
            sala = new Sala(0);
            response = sala.toVectorString();
        }
        return response;
    }
    
    public String[] RemoveUsuario(int userId,int salaId) {
        Sala sala = new Sala();
        Usuario user = new Usuario();
        String[] response = sala.toVectorString();
        
        Criteria crit = this.getSessao().createCriteria(Usuario.class);
        crit.add(Restrictions.eq("id", userId));
        user = (Usuario) crit.uniqueResult();
        
        crit = this.getSessao().createCriteria(Sala.class);
        crit.add(Restrictions.eq("id", salaId));
        sala = (Sala) crit.uniqueResult();
        
        sala.getListUser().remove(user);
        
        this.getSessao().update(sala);
        this.commit();
        
        response = sala.toVectorString();
        
        return response;
    }
    
    public void DeletarSala (int idSala) {
        Sala sala = new Sala();
        Criteria crit = this.getSessao().createCriteria(Sala.class);
        crit.add(Restrictions.eq("id", idSala));
        sala = (Sala) crit.uniqueResult();
        
        ConjuntoQuestoes c = sala.getConjuntoQuestoes();
        //Removendo conjunto de questões
        c.setRespostas(new HashSet<Resposta>());
        this.getSessao().update(c);
        //this.commit();
        c.setQuestoes(new HashSet<Questao>());
        this.getSessao().update(c);
        //this.commit();
        sala.setConjuntoQuestoes(null);
        this.getSessao().update(sala);
        //this.commit();
        getSessao().delete(c);
        getTrans().commit();
        
        //Apagando area e subárea
        sala.setArea(null);
        sala.setSubArea(null);
        this.getSessao().update(sala);
        //this.commit();
        
        //Apagando usuários da sala
        List<Usuario> listUsers = new ArrayList<>(sala.getListUser());
        for (Usuario u : listUsers) {
            u.setSala(null);
            this.getSessao().update(u);
            //this.commit();
        }
        sala.setListUser(new HashSet<Usuario>());
        this.getSessao().update(sala);
        //this.commit();
        
        this.getSessao().delete(sala);
        this.commit();
    }
}
