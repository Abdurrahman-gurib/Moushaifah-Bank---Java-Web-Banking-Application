package com.moushaifahbank.util;

import java.util.List;

import org.hibernate.Session;
import org.mindrot.jbcrypt.BCrypt;

import com.moushaifahbank.model.Account;
import com.moushaifahbank.model.User;

public class UserDAO {

    private String normalizeEmail(String email) {
        return email == null ? null : email.toLowerCase().trim();
    }

    private String normalizeAnswer(String answer) {
        return answer == null ? null : answer.toLowerCase().trim();
    }

    public User findByEmail(String email) {
        String normalizedEmail = normalizeEmail(email);
        if (normalizedEmail == null) {
            return null;
        }
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            List<User> users = s.createQuery("from User where email = :email", User.class)
                    .setParameter("email", normalizedEmail)
                    .setMaxResults(1)
                    .list();
            return users.isEmpty() ? null : users.get(0);
        }
    }

    public User findById(Long id) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.get(User.class, id);
        }
    }

    public boolean register(String name, String email, String phone, String password, String answer) {
        String normalizedEmail = normalizeEmail(email);
        if (normalizedEmail == null || findByEmail(normalizedEmail) != null) {
            return false;
        }

        org.hibernate.Transaction tx = null;

        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            tx = s.beginTransaction();

            User u = new User();
            u.setFullName(name);
            u.setEmail(normalizedEmail);
            u.setPhone(phone);
            u.setPasswordHash(BCrypt.hashpw(password, BCrypt.gensalt()));
            u.setSecurityAnswer(normalizeAnswer(answer));

            s.save(u);
            s.flush();

            Account a = new Account();
            a.setUserId(u.getId());
            a.setAccountNumber("MSB-" + (100000 + u.getId()));
            a.setType("Savings Account");
            a.setBalance(25000.00);

            s.save(a);
            s.flush();

            seedTx(s, a.getId());

            tx.commit();
            return true;

        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                try {
                    tx.rollback();
                } catch (Exception rollbackError) {
                    rollbackError.printStackTrace();
                }
            }

            e.printStackTrace();
            return false;
        }
    }

    private void seedTx(Session s, Long aid) {
        String[] desc = {
                "Welcome opening deposit",
                "Moushaifah debit card issued",
                "Salary transfer",
                "Utility bill payment"
        };

        double[] amt = {25000, -250, 52000, -3200};
        String[] type = {"CREDIT", "DEBIT", "CREDIT", "DEBIT"};

        for (int i = 0; i < desc.length; i++) {
            com.moushaifahbank.model.Transaction t =
                    new com.moushaifahbank.model.Transaction();

            t.setAccountId(aid);
            t.setDescription(desc[i]);
            t.setAmount(amt[i]);
            t.setType(type[i]);

            s.save(t);
        }
    }

    public User authenticate(String email, String password) {
        if (email == null || password == null) {
            System.out.println("authenticate: missing credentials email=" + email + " password=" + (password == null ? "null" : "***"));
            return null;
        }
        User u = findByEmail(email);
        System.out.println("authenticate: lookup email=" + email + " found=" + (u != null));
        if (u != null) {
            boolean ok = BCrypt.checkpw(password, u.getPasswordHash());
            System.out.println("authenticate: password check=" + ok + " hash=" + u.getPasswordHash());
            return ok ? u : null;
        }
        return null;
    }

    public boolean resetPassword(String email, String answer, String newPassword) {
        String normalizedEmail = normalizeEmail(email);
        String normalizedAnswer = normalizeAnswer(answer);
        if (normalizedEmail == null || normalizedAnswer == null) {
            return false;
        }
        org.hibernate.Transaction tx = null;

        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            List<User> users = s.createQuery("from User where email = :email", User.class)
                    .setParameter("email", normalizedEmail)
                    .setMaxResults(1)
                    .list();
            User u = users.isEmpty() ? null : users.get(0);

            if (u == null || !u.getSecurityAnswer().equals(normalizedAnswer)) {
                return false;
            }

            tx = s.beginTransaction();

            u.setPasswordHash(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
            s.update(u);

            tx.commit();
            return true;

        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                try {
                    tx.rollback();
                } catch (Exception rollbackError) {
                    rollbackError.printStackTrace();
                }
            }

            e.printStackTrace();
            return false;
        }
    }

    public List<Account> accounts(Long uid) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("from Account where userId=:uid", Account.class)
                    .setParameter("uid", uid)
                    .list();
        }
    }

    public List<com.moushaifahbank.model.Transaction> transactions(Long aid) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery(
                            "from Transaction where accountId=:aid order by id desc",
                            com.moushaifahbank.model.Transaction.class
                    )
                    .setParameter("aid", aid)
                    .list();
        }
    }
}