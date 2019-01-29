package com.ycy.book.DAO.impl;

import com.ycy.book.DAO.BookDAO;
import com.ycy.book.domin.Book;
import com.ycy.book.web.CriteriaBook;
import com.ycy.book.web.Page;
import org.junit.Test;

public class BookDAOImplTest {
    private BookDAO bookDAO = new BookDAOImpl();

    @Test
    public void getBook() {
        Book book = bookDAO.getBook(5);
        System.out.println(book.toString());
    }

    @Test
    public void getPage() {
        Page<Book> page = bookDAO.getPage(new CriteriaBook(50, 60, 90));
        System.out.println("page no : " + page.getPageNo());
        System.out.println("total page num : " + page.getTotalPageNumber());
        System.out.println(" list : " + page.getList());
        System.out.println(" pve : " + page.getPrevPage());
        System.out.println(" next : " + page.getNextPage());
    }

    @Test
    public void getTotalBookNumber() {
    }

    @Test
    public void getPageList() {
    }

    @Test
    public void getStoreNumber() {
        int storeNumber = bookDAO.getStoreNumber(5);
        System.out.println(storeNumber);
    }
}