package com.moushaifahbank.util;
import org.hibernate.SessionFactory;import org.hibernate.cfg.Configuration;
public class HibernateUtil{private static final SessionFactory FACTORY=build();private static SessionFactory build(){try{return new Configuration().configure().buildSessionFactory();}catch(Throwable ex){throw new ExceptionInInitializerError(ex);}} public static SessionFactory getSessionFactory(){return FACTORY;}}
