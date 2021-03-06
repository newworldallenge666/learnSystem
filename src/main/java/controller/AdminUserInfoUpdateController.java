package controller;

import dao.UserDao;
import pojo.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@WebServlet(name = "AdminUserInfoUpdateController", urlPatterns = "/admin/userInfoUpdate")
public class AdminUserInfoUpdateController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uid = request.getParameter("uid");
        User user = new User();
        if (uid!=null&&uid.length()>0){
            user = new UserDao().findUserById(Integer.valueOf(uid));
        }else{
            response.getWriter().print("<script>alert('修改失败')</script>");
        }
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String face = request.getParameter("face");
        String tel = request.getParameter("tel");
        String sex = request.getParameter("sex");
        String bir = request.getParameter("bir");
        String introduction = request.getParameter("introduction");

        if(user!=null){
            if(password!=null&&password.length()>0){
                user.setUserPwd(password);
            }
            if(email!=null&&email.length()>0){
                user.setEmail(email);
            }
            if(face!=null&&face.length()>0){
                user.setFace(face);
            }
            if(tel!=null&&tel.length()>0){
                user.setTel(tel);
            }
            if (sex!=null&&sex.length()>0){
                user.setGenderId(Integer.valueOf(sex));
            }
            if (bir!=null&&bir.length()>0){
                user.setBirthday(bir);
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    user.setBir(fmt.parse(bir));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (introduction!=null&&introduction.length()>0){
                user.setIntroduction(introduction);
            }
            int row = new UserDao().updateUser(user);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uid = request.getParameter("uid");
        User user = new User();
        if (uid!=null&&uid.length()>0){
            user = new UserDao().findUserById(Integer.valueOf(uid));
            request.setAttribute("user",user);
        }
        RequestDispatcher rd = request.getRequestDispatcher("/pages/admin/update.jsp");
        rd.forward(request,response);
    }

}
