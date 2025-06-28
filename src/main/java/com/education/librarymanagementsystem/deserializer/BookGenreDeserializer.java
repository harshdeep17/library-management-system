package com.education.librarymanagementsystem.deserializer;

import com.education.librarymanagementsystem.exception.InvalidEnumException;
import com.education.librarymanagementsystem.model.BookGenre;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class BookGenreDeserializer extends JsonDeserializer<BookGenre> {

    @Override
    public BookGenre deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getText();
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try{
            return BookGenre.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidEnumException("Invalid value for genre: " + value);
        }
    }
}
