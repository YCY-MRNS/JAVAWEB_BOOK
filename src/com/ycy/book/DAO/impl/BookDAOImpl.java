package com.ycy.book.DAO.impl;

import com.ycy.book.DAO.BookDAO;
import com.ycy.book.domin.Book;
import com.ycy.book.domin.ShoppingCartItem;
import com.ycy.book.web.CriteriaBook;
import com.ycy.book.web.Page;

import java.util.Collection;
import java.util.List;

/**
 * @program: Book
 * @description:
 * @author: ChangYue
 * @create: 2019-01-25 16:23
 */
public class BookDAOImpl extends BaseDao<Book> implements BookDAO {

    @Override
    public Book getBook(int id) {
        String sql = "select * from mybooks where id = ?";
        return query(sql, id);
    }

    @Override
    public Page<Book> getPage(CriteriaBook cb) {
        Page<Book> bookPage = new Page<>(cb.getPage());
        bookPage.setTotalItemsNumber(getTotalBookNumber(cb));
        //校验page的合法性
        cb.setPage(bookPage.getPageNo());
        bookPage.setList(getPageList(cb, 3));
        return bookPage;
    }

    @Override
    public long getTotalBookNumber(CriteriaBook cb) {
        String sql = "select count(id) from mybooks where price >=? and price <= ?";
        return getSingleVal(sql, cb.getMinPrice(), cb.getMaxPrice());
    }

    @Override
    public List<Book> getPageList(CriteriaBook cb, int pageSize) {
        String sql = "select * from mybooks where price >=? and price <= ? limit ?,?";
        return queryForList(sql, cb.getMinPrice(), cb.getMaxPrice(), ((cb.getPage() - 1) * pageSize), pageSize);
    }

    @Override
    public int getStoreNumber(Integer id) {
        String sql = "select  storeNumber from mybooks where id=?";
        return getSingleVal(sql, id);
    }

    @Override
    public void batchUpdateStoreNumberAndSalesAmount(Collection<ShoppingCartItem> items) {

    }
}
