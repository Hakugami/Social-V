package org.spring.FriendService.exceptions;

public class FriendRequestNotFoundException extends RuntimeException {
    public FriendRequestNotFoundException(String message) {
        super(message);
    }
}
