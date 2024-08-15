package com.movielix.movieApi.exceptions;

public class FileExistsException extends RuntimeException{

    public FileExistsException(String message){
        super(message);

    }
}
