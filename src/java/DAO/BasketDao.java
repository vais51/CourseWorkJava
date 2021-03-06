/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Models.Basket;
import Models.Department;
import Models.Product;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Саня
 */
public class BasketDao {
    private ArrayList<Basket> baskets;
    private  Connection con;
    private  PreparedStatement pstmt;
    private  ResultSet rs;
    
    public BasketDao(Connection con){
        this.con = con;
    }
    
    public ArrayList<Basket> getAllProductsFromBasket() throws SQLException, IOException {   
        baskets= new ArrayList<Basket>();
        String query = "select  b.idProduct, b.dateP, b.login, p.name, p.price, "
                + "p.discountPrice, b.id from Basket as b, product as p "
                + "where b.idProduct = p.idProduct ";
        pstmt = con.prepareStatement(query);
        rs = pstmt.executeQuery();
        while (rs.next()) {
            Basket basket = new Basket();
            Product product = new Product();
            product.setIdProduct(rs.getInt(1));
            product.setName(rs.getString(4));
            product.setPrice(rs.getBigDecimal(5));
            product.setDiscountPrice(rs.getBigDecimal(6));
            basket.setProduct(product);           
            basket.setLoginCus(rs.getString(3));
            basket.setDate(rs.getDate(2));
            basket.setId(rs.getInt(7));
            baskets.add(basket);
        }
        return baskets;
    }
    
    public void addProductInBasket(Product product, String login) throws SQLException{
        pstmt = con.prepareStatement("insert into basket (idProduct,dateP,login) values (?,?,?)");
        pstmt.setLong(1, product.getIdProduct());
        pstmt.setDate(2, new java.sql.Date((new java.util.Date()).getTime()));
        pstmt.setString(3,login);
        pstmt.executeUpdate();
        
    }
    public void removePrductFromBasket(Basket basket) throws SQLException{
        pstmt = con.prepareStatement("DELETE FROM basket WHERE id = ? ;");
        pstmt.setLong(1, basket.getId());
        pstmt.executeUpdate();
        //Удалить из бд продукт по login пользователя 
    }
}
