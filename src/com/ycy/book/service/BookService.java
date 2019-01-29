package com.ycy.book.service;

import com.ycy.book.DAO.BookDAO;
import com.ycy.book.DAO.impl.BookDAOImpl;
import com.ycy.book.domin.Book;
import com.ycy.book.domin.ShoppingCart;
import com.ycy.book.web.CriteriaBook;
import com.ycy.book.web.Page;

/**
 * @program: Book
 * @description:
 * @author: ChangYue
 * @create: 2019-01-26 12:14
 */
public class BookService {

    private BookDAO bookDAO = new BookDAOImpl();

    public Page<Book> getPage(CriteriaBook criteriaBook) {
        return bookDAO.getPage(criteriaBook);
    }

    public Book getBook(int id) {
        return bookDAO.getBook(id);
    }

    public boolean addToCart(int id, ShoppingCart sc) {
        Book book = bookDAO.getBook(id);
        if (book != null) {
            sc.addBook(book);
            return true;
        }
        return false;
    }

}
