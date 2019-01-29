package com.ycy.book.web;

import java.util.List;

/**
 * @program: Book
 * @description:
 * @author: ChangYue
 * @create: 2019-01-25 15:05
 */
public class Page<T> {
    private int pageNo;
    private List<T> list;
    private int pageSize = 3;
    private long totalItemsNumber;

    public Page() {
    }

    public int getPageNo() {
        if (pageNo < 0) {
            pageNo = 1;
        }
        if (pageNo > getTotalPageNumber()) {
            pageNo = getTotalPageNumber();
        }
        return pageNo;
    }

    public Page(int pageNo) {
        super();
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }

    public int getTotalPageNumber() {
        int totalPageNumber = (int) (totalItemsNumber / pageSize);
        if (totalPageNumber % pageSize != 0) {
            totalPageNumber++;
        }
        return totalPageNumber;
    }

    public void setTotalItemsNumber(long totalItemsNumber) {
        this.totalItemsNumber = totalItemsNumber;
    }

    private boolean isHasNext() {
        return getPageNo() < getTotalPageNumber();
    }

    private boolean isHasPrev() {
        return getPageNo() > 1;
    }

    public int getPrevPage() {
        if (isHasPrev()) {
            return getPageNo() - 1;
        }
        return getPageNo();
    }

    public int getNextPage() {
        if (isHasNext()) {
            return getPageNo() + 1;
        }
        return getPageNo();
    }
}
