package com.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserCRUD{

    @Autowired
    private DataSource dataSource;

    @GetMapping("/")
    public ArrayList<User> allUsers(HttpServletResponse response){
        try (Connection connection = dataSource.getConnection()){
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM chamis");

            ArrayList<User> L = new ArrayList<User>();
            while (rs.next()) {
                User u = new User();
                u.login = rs.getString("login");
                u.age = rs.getInt("age");
                L.add(u);
            }
            return L;
        } catch(Exception e){
            response.setStatus(500);
        try{
            response.getOutputStream().print(e.getMessage());
        } catch (Exception e2) {
            System.err.println(e2.getMessage()); 
        }
        System.err.println(e.getMessage());
        return null;
        }
    }

    @GetMapping("/{userId}")
    public User read(@PathVariable(value="userId") String id, HttpServletResponse response){
        try (Connection connection = dataSource.getConnection()){
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM chamis WHERE login='"+ id +"'");

            User u = new User();
            if(!rs.next()){
                response.setStatus(404);
                response.getOutputStream().print("erreur 404");
                return null;
            }else{
                u.login = rs.getString("login");
                u.age = rs.getInt("age");
                return u;
            }
        } catch(Exception e){
            response.setStatus(500);
            try{
                response.getOutputStream().print(e.getMessage());
            } catch (Exception e2) {
                System.err.println(e2.getMessage()); 
            }
            System.err.println(e.getMessage());
            return null;
        }
    }

    @PostMapping("/{userId}")
    public User create(@PathVariable(value="userId") String id, @RequestBody User u, HttpServletResponse response){
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM chamis WHERE login='"+ id +"'");
            if (rs.next()) {
                response.setStatus(403);
                response.getOutputStream().print("Erreur HTTP 403");
                return null;
            } else {
                if(id.equals(u.getLogin())) {
                    stmt.executeUpdate("Insert into chamis values('"+ u.getLogin() +"','"+u.getAge() +"')"); 
                    return u;
                } else {
                    response.setStatus(412);
                    response.getOutputStream().print("Erreur HTTP 412");
                    return null;
                }
            }
        } catch(Exception e){
            response.setStatus(500);
        try{
            response.getOutputStream().print(e.getMessage());
        } catch (Exception e2) {
            System.err.println(e2.getMessage()); 
        }
        System.err.println(e.getMessage());
        return null;
        } 
    }

    @PutMapping("/{userId}")
        public User update(@PathVariable(value="userId") String id, @RequestBody User u, HttpServletResponse response){
            try (Connection connection = dataSource.getConnection()){
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM chamis WHERE login='"+ id +"'");
                if(rs.next()){
                    if(rs.getString("login").equals(u.getLogin())){
                        stmt.executeUpdate("UPDATE chamis set age = "+ u.getAge() + "WHERE login = '" + u.login + "'"); 
                    }else{
                        response.setStatus(412);
                        response.getOutputStream().print("Erreur HTTP 412");
                    }
                }else{
                    response.setStatus(404);
                    response.getOutputStream().print("Erreur HTTP 404");
                }
                return u;
            } catch(Exception e){
                response.setStatus(500);
                try{
                    response.getOutputStream().print(e.getMessage());
                } catch (Exception e2) {
                    System.err.println(e2.getMessage()); 
                }
                System.err.println(e.getMessage());
            }
        return null;
    }

    @DeleteMapping("/{userId}")
    void delete(@PathVariable(value="userId") String id, HttpServletResponse response){
        try (Connection connection = dataSource.getConnection()){
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM chamis WHERE login='"+ id +"'");
            if(rs.next()){
                stmt.executeUpdate("DELETE FROM chamis WHERE login = '" + id + "'"); 
            }else{
                response.setStatus(404);
                response.getOutputStream().print("Erreur HTTP 404");
            }
        } catch(Exception e){
            response.setStatus(500);
            try{
                response.getOutputStream().print(e.getMessage());
            } catch (Exception e2) {
                System.err.println(e2.getMessage()); 
            }
            System.err.println(e.getMessage());
        }
    }
 }
