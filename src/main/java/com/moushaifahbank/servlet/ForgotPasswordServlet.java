package com.moushaifahbank.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moushaifahbank.util.DataSeeder;
import com.moushaifahbank.util.UserDAO;

@WebServlet("/forgot-password")
public class ForgotPasswordServlet extends HttpServlet {
    UserDAO dao = new UserDAO();

    protected void doGet(HttpServletRequest r, HttpServletResponse p) throws ServletException, IOException {
        DataSeeder.seed();
        r.getRequestDispatcher("/WEB-INF/views/forgot.jsp").forward(r, p);
    }

    protected void doPost(HttpServletRequest r, HttpServletResponse p) throws ServletException, IOException {
        DataSeeder.seed();
        boolean ok = dao.resetPassword(r.getParameter("email"), r.getParameter("answer"), r.getParameter("password"));
        r.setAttribute(ok ? "success" : "error", ok ? "Password reset successfully. Please login." : "Email or security answer is incorrect.");
        r.getRequestDispatcher(ok ? "/WEB-INF/views/login.jsp" : "/WEB-INF/views/forgot.jsp").forward(r, p);
    }
}
