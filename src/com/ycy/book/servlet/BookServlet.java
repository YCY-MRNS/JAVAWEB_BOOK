package com.ycy.book.servlet;

import com.ycy.book.domin.Book;
import com.ycy.book.domin.ShoppingCart;
import com.ycy.book.service.BookService;
import com.ycy.book.web.BookStoreWebUtils;
import com.ycy.book.web.CriteriaBook;
import com.ycy.book.web.Page;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

@WebServlet(name = "BookServlet", urlPatterns = {"/bookServlet"})
public class BookServlet extends HttpServlet {
    private BookService bookService = new BookService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String methodName = request.getParameter("method");
        System.out.println(methodName);
        try {
            Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.setAccessible(true);
            method.invoke(this, request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void deleteCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        int id = 0;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
        }
        ShoppingCart shoppingCart = BookStoreWebUtils.getShoppingCart(request);
        shoppingCart.removeItem(id);
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }

    protected void toCartPages(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }

    protected void addToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        int id = 0;
        boolean flag = false;

        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
        }

        if (id > 0) {
            ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
            flag = bookService.addToCart(id, sc);
        }

        if (flag) {
            getBooks(request, response);
            return;
        }
        response.sendRedirect(request.getContextPath() + "/error.jsp");

    }

    protected void getDetailBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");

        int id = 0;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {

        }

        Book book = bookService.getBook(id);

        if (book == null) {
            response.sendRedirect(request.getContextPath() + "/error.jsp");
            return;
        }

        request.setAttribute("bookDetail", book);
        request.getRequestDispatcher("WEB-INF/pages/bookDetails.jsp").forward(request, response);

    }

    protected void getBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pageNoStr = request.getParameter("pageNo");
        String minPriceStr = request.getParameter("minPrice");
        String maxPriceStr = request.getParameter("maxPrice");

        int pageNo = 1;
        int minPrice = 0;
        int maxPrice = Integer.MAX_VALUE;

        try {
            pageNo = Integer.parseInt(pageNoStr);
        } catch (NumberFormatException e) {
        }
        try {
            minPrice = Integer.parseInt(minPriceStr);
        } catch (NumberFormatException e) {
        }
        try {
            maxPrice = Integer.parseInt(maxPriceStr);
        } catch (NumberFormatException e) {
        }

        CriteriaBook criteriaBook = new CriteriaBook(minPrice, maxPrice, pageNo);

        Page<Book> page = bookService.getPage(criteriaBook);

        request.setAttribute("bookpage", page);
        request.getRequestDispatcher("/WEB-INF/pages/book.jsp").forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
