/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Book;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Melnikov
 */
@Stateless
public class BookFacade extends AbstractFacade<Book> {

    @PersistenceContext(unitName = "JPTVR17WebLibraryPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BookFacade() {
        super(Book.class);
    }

    public List<Book> findTakeBook() {
        return em.createQuery("SELECT b FROM Book b WHERE b.countInLibrary > 0 AND b.active = 'true'")
                .getResultList();
    }

    public List<Book> findIsActiveBooks() {
        try {
            return em.createQuery("SELECT b FROM Book b WHERE b.active = 'true'")
                .getResultList();
        } catch (Exception e) {
            return null;
        }
        
    }

    public List<Book> getListNewBooks() {
        //ожидается рефакторинг 
        //энтити Book с целью добавления даты создания книги
        try {
            return em.createQuery("SELECT b FROM Book b")
                    .getResultList();
        } catch (Exception e) {
            return null;
        }
    }

   
    
}
