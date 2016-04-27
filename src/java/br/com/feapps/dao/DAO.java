/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.feapps.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import br.com.feapps.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.TransactionException;

/**
 *
 * @author F.Einstein
 */
public class DAO {
    private Session session = HibernateUtil.getSessionFactory().openSession();
    private Transaction transaction = session.beginTransaction();

    public DAO() {
    }

    public Session getSessao() {
        if (!session.isOpen()) {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
        }
        return session;
    }

    public Transaction getTrans() {
        return transaction;
    }
    
    public void commit() {
        try {
            getTrans().commit();
        } catch (TransactionException te) {
            
        }
            
            
        //closeSession();
    }
    
    public void closeSession() {
        if (session != null && session.isOpen()) {
            session.clear();
            session.close();
        }
    }
    
    public void insert(Object u) {
        try {
            getSessao().save(u);
            getTrans().commit();
        } catch (HibernateException hex) {
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            throw hex;
        } finally {
            /*if (session != null && session.isOpen()) {
                session.close();
            }*/
        }
    }
    
    public void update(Object u) {
        try {
            getSessao().update(u);
            getTrans().commit();
        } catch (HibernateException hex) {
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            throw hex;
        } finally {
            /*if (session != null && session.isOpen()) {
                session.close();
            }*/
        }
    }
    
    public void remove(Object c) {
        try {
            getSessao().delete(c);
            getTrans().commit();
        } catch (HibernateException hex) {
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            throw hex;
        } finally {
            /*if (session != null && session.isOpen()) {
                session.close();
            }*/
        }
    }
    
}
