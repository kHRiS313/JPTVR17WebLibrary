/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonbuilders;

import entity.Book;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author Melnikov
 */
public class BookJsonBuilder {
    public JsonObject createJsonBook(Book book){
        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("id", book.getId())
                .add("name", book.getName())
                .add("author", book.getAuthor())
                .add("isbn", book.getIsbn())
                .add("price", book.getPrice())
                .add("countInLibrary", book.getCountInLibrary())
                .add("active", book.isActive());
        return job.build();
    }
}
