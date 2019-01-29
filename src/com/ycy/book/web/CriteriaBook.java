package com.ycy.book.web;

/**
 * @program: Book
 * @description: 条件
 * @author: ChangYue
 * @create: 2019-01-25 15:05
 */
public class CriteriaBook {
    private float minPrice = 0;
    private float maxPrice = Integer.MAX_VALUE;
    private int page;

    public CriteriaBook(float minPrice, float maxPrice, int page) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.page = page;
    }

    public float getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(float minPrice) {
        this.minPrice = minPrice;
    }

    public float getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(float maxPrice) {
        this.maxPrice = maxPrice;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "CriteriaBook{" +
                "minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", page=" + page +
                '}';
    }
}
