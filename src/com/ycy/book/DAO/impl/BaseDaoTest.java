package com.ycy.book.DAO.impl;

import com.ycy.book.domin.Book;
import org.junit.Test;

import java.sql.Date;
import java.util.List;

public class BaseDaoTest {

    private BookDAOImpl bookDAOImpl = new BookDAOImpl();

    @Test
    public void insert() {
        String sql = "insert into trade (userid,tradetime) values(?,?)";
        long id = bookDAOImpl.insert(sql, 1, new Date(new java.util.Date().getTime()));
        System.out.println(id);
    }

    @Test
    public void update() {
        String sql = "UPDATE mybooks SET Salesamount=? WHERE id = ?";
        bookDAOImpl.update(sql, 10, 2);
    }

    @Test
    public void query() {
        String sql = "SELECT * FROM mybooks WHERE id = ?";
        Book query = bookDAOImpl.query(sql, 2);
        System.out.println(query);
    }

    @Test
    public void queryForList() {
        String sql = "SELECT * FROM mybooks WHERE id > ?";
        List<Book> books = bookDAOImpl.queryForList(sql, 10);
        for (Book book : books) {
            System.out.println(book.toString());
        }

    }

    @Test
    public void getSingleVal() {
        String sql = "SELECT count(id) FROM  mybooks";
        long count = bookDAOImpl.getSingleVal(sql);
        System.out.println(count);
    }

    @Test
    public void batch() {
        String sql = "update mybooks set salesAmount=? , storeNumber=? where id = ?";
        bookDAOImpl.batch(sql, new Object[]{1, 1, 1}, new Object[]{2, 2, 2}, new Object[]{3, 3, 3});
    }
}
