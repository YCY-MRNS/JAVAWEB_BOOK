package com.ycy.book.web;

import com.ycy.book.domin.ShoppingCart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @program: Book
 * @description:
 * @author: ChangYue
 * @create: 2019-01-26 19:12
 */
public class BookStoreWebUtils {

    /**
     * 获得购物车：从session中获取
     *
     * @param request
     * @return
     */
    public static ShoppingCart getShoppingCart(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart();
            session.setAttribute("shoppingCart", shoppingCart);
        }

        return shoppingCart;
    }


}
