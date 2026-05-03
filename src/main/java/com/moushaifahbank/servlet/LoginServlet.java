package com.moushaifahbank.servlet; import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moushaifahbank.model.User;
import com.moushaifahbank.util.DataSeeder;
import com.moushaifahbank.util.UserDAO;
 @WebServlet("/login") public class LoginServlet extends HttpServlet{UserDAO dao=new UserDAO(); protected void doGet(HttpServletRequest r,HttpServletResponse p)throws ServletException,IOException{DataSeeder.seed();r.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(r,p);} protected void doPost(HttpServletRequest r,HttpServletResponse p)throws ServletException,IOException{String email=r.getParameter("email");String password=r.getParameter("password");User u=dao.authenticate(email,password); if(u!=null){r.getSession().setAttribute("userId",u.getId());p.sendRedirect("dashboard");}else{r.setAttribute("error","Invalid email or password.");r.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(r,p);}}}
