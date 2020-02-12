/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entity.Book;
import entity.History;
import entity.Reader;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jsonbuilders.BookJsonBuilder;
import session.BookFacade;
import session.HistoryFacade;
import session.ReaderFacade;
import session.UserFacade;
import util.EncriptPass;
import util.RoleManager;

/**
 *
 * @author Melnikov
 */
@WebServlet(name = "UserController", urlPatterns = {
    "/index",
    "/showBook",
    "/takeBook",
    "/doTakeBook",
    "/editReader",
    "/changeReader",
        "/listBooks",
    "/listNewBooks",
    
})
public class UserController extends HttpServlet {
@EJB BookFacade bookFacade;
@EJB ReaderFacade readerFacade;
@EJB HistoryFacade historyFacade;
@EJB private UserFacade userFacade;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String path = request.getServletPath();
        HttpSession session = request.getSession(false);
        if (null == session){
            request.setAttribute("info", "У вас нет прав доступа, войдите в систему");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;   
        }
        if(null == session.getAttribute("user")){
            request.setAttribute("info", "У вас нет прав доступа, войдите в систему");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return; 
        }
        User user = (User) session.getAttribute("user");
        RoleManager rm = new RoleManager();
        if(!rm.isRoleUser("USER", user)){
            request.setAttribute("info", "У вас нет прав доступа, войдите в систему");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return; 
        }
        Reader reader = null;
        request.setAttribute("userRole", rm.getTopRoleName(user));
        switch (path) {
            case "/index":
                request.getRequestDispatcher("/index.jsp").forward(request, response);
                break;
            case "/showBook":
                String bookId = request.getParameter("id");
                Book book = bookFacade.find(Long.parseLong(bookId));
                request.setAttribute("book", book);
                request.getRequestDispatcher("/showBook.jsp").forward(request, response);
                break;
            case "/takeBook":
                List<Book> listBooks = bookFacade.findTakeBook();
                request.setAttribute("listBooks", listBooks);
                request.getRequestDispatcher("/showBook.jsp").forward(request, response);
                break;
            case "/doTakeBook":
                bookId = request.getParameter("bookId");
                try {
                    book = bookFacade.find(Long.parseLong(bookId));
                    if(book.getCountInLibrary()>0){
                        reader = readerFacade.find(user.getReader().getId());
                        int moneyReader = reader.getMoney();
                        int priceBook = book.getPrice();
                        int moneyFromReaderAfterPurcase = moneyReader - priceBook;
                        if(moneyFromReaderAfterPurcase >= 0){
                            book.setCountInLibrary(book.getCountInLibrary()-1);
                            bookFacade.edit(book);
                            reader = user.getReader();
                            reader.setMoney(moneyFromReaderAfterPurcase);
                            readerFacade.edit(reader);
                            History history = new History();
                            history.setBook(book);
                            history.setReader(reader);
                            history.setTakeBook(new Date());
                            historyFacade.create(history);
                            request.setAttribute("info", "Книга куплена");
                        }else{
                            request.setAttribute("info", "Не хватает денег!");
                        }
                    }else{
                        request.setAttribute("info", "Книги нет в наличии");
                    }
                } catch (Exception e) {
                    request.setAttribute("info", "Не корректные данные");
                }
                request.getRequestDispatcher("/listBooks")
                        .forward(request, response);
                break;
            case "/editReader":
                User changeUser=null;
                String id = request.getParameter("id");
                if(id == null){
                   changeUser = (User) session.getAttribute("user");
                   reader = changeUser.getReader();
                
                    if (changeUser == null){
                        request.setAttribute("info", "Войдите, для доступа к функции");
                        request.getRequestDispatcher("/showLogin.jsp")
                            .forward(request, response);
                    }
                }else{
                    reader = readerFacade.find(Long.parseLong(id));
                    changeUser = userFacade.findByReader(reader); 
                }
                
                if("admin".equals(changeUser.getLogin())){
                    if(!"admin".equals(user.getLogin())){
                        request.setAttribute("info", "У вас нет прав, войдите в систему как администратор");
                        request.getRequestDispatcher("/index.jsp").forward(request, response);
                        break;
                    }
                }
                request.setAttribute("reader", reader);
                request.setAttribute("changeUser", changeUser);
                request.getRequestDispatcher("/editReader.jsp")
                        .forward(request, response);
                break;
            case "/changeReader":
                id = request.getParameter("id");
                String name = request.getParameter("name");
                String surname = request.getParameter("surname");
                String phone = request.getParameter("phone");
                String money = request.getParameter("money");
                String login = request.getParameter("login");
                String password1 = request.getParameter("password1");
                String password2 = request.getParameter("password2");
                if("".equals(name) || name==null
                        || "".equals(name) || name==null
                        || "".equals(surname) || surname==null 
                        || "".equals(phone) || phone==null 
                        || "".equals(money) || money==null 
                        || "".equals(login) || login==null 
                        || "".equals(password1) || password1==null
                        || "".equals(password2) || password2==null
                        || !password1.equals(password2)
                        ){
                    request.setAttribute("info", "Не совпадают пароли или незаполнены все поля");
                    request.setAttribute("name", name);
                    request.setAttribute("surname", surname);
                    request.setAttribute("phone", phone);
                    request.setAttribute("money", money);
                    request.setAttribute("login", login);
                    request.setAttribute("id", id);
                    request.getRequestDispatcher("/editReader")
                        .forward(request, response);
                    break;
                }
               
                changeUser = userFacade.findByLogin(login);
                if(changeUser == null){
                    request.setAttribute("info", "Нет такого пользователя");
                    request.setAttribute("id", id);
                    request.getRequestDispatcher("/editReader")
                        .forward(request, response);
                    break;
                }
                changeUser.setLogin(login);
                EncriptPass ep = new EncriptPass();
                String encriptPassword = ep.setEncriptPass(password1, changeUser.getSalts());
                changeUser.setPassword(encriptPassword);
                try {
                    userFacade.edit(changeUser);
                    reader = changeUser.getReader();
                    reader.setName(name);
                    reader.setSurname(surname);
                    reader.setPhone(phone);
                    reader.setMoney(Integer.parseInt(money));
                    //Запись данных в базу
                    readerFacade.edit(reader);
                    request.setAttribute("reader", reader);
                    request.setAttribute("info", "Данные "+changeUser.getLogin()+" изменены");
                    request.getRequestDispatcher("/editReader").forward(request, response);
                } catch (Exception e) {
                   request.setAttribute("info", "Данные не изменены");
                    request.setAttribute("id", id);
                    request.getRequestDispatcher("/editReader")
                        .forward(request, response);
                }
                break;    
                   case "/listBooks":
                listBooks = bookFacade.findIsActiveBooks();
                request.setAttribute("listBooks", listBooks);
                request.getRequestDispatcher("/listBooks.jsp").forward(request, response);
                break;
            case "/listNewBooks":
                List<Book> listNewBooks = bookFacade.getListNewBooks();
                BookJsonBuilder bookJsonBuilder = new BookJsonBuilder();
                JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                String json = "";
                try (Writer writer = new StringWriter()){
                    Json.createWriter(writer).write(arrayBuilder.build());
                    json = writer.toString();
                }
                try (PrintWriter out = response.getWriter()) {
                  out.println(json);        
                }
                break;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
