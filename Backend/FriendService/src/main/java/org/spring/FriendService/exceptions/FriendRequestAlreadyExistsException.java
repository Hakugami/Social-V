package org.spring.FriendService.exceptions;

public class FriendRequestAlreadyExistsException extends RuntimeException {
    public FriendRequestAlreadyExistsException(String message) {
        super(message);
    }
}