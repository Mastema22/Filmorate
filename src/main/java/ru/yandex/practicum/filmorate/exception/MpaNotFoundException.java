package ru.yandex.practicum.filmorate.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MpaNotFoundException extends RuntimeException {
    public MpaNotFoundException(String message) {
        log.error(message);
    }
}
