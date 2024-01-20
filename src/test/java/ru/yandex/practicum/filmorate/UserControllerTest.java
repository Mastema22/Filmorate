/*
package ru.yandex.practicum.filmorate;

import org.springframework.boot.test.context.SpringBootTest;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.*;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserControllerTest {

  */
/*  private final UserService userService;


    private static Validator validator;

    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }

    @Test
    public void shouldCreateUser() throws ValidationException {
        User user = new User(15, "qwert@mail.ru", "Mango", "Игнат", LocalDate.of(2002, 8, 15));
        userService.createUser(user);
        assertEquals("qwert@mail.ru", userService.findUserById(user.getId()).getEmail());
    }

    @Test
    public void shouldUpdateUser() throws ValidationException {
        User user = new User(1, "qwert@mail.ru", "Mango", "Игнат", LocalDate.of(2002, 8, 15));
        userService.createUser(user);
        User userUpdate = new User(user.getId(), "qwert@mail.ru", "Mango", "Игнат", LocalDate.of(1999, 3, 2));
        userService.upadateUser(userUpdate);
        assertEquals(userUpdate, userService.findUserById(user.getId()));
    }

    @Test
    public void shouldCreateUserWithEmptyName() throws ValidationException {
        User user = new User(15, "qwert@mail.ru", "Mango", "", LocalDate.of(2002, 8, 15));
        userService.createUser(user);
        assertEquals("Mango", user.getName());
    }

    @Test
    void shouldNotPassEmailValidation() {
        User user = new User(15, "qwertmail.ru", "Mango", "Игнат", LocalDate.of(2002, 8, 15));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
    }

    @Test
    public void shouldNotPassLoginValidationWithEmptyLogin() {
        User user = new User(15, "qwert@mail.ru", "", "Игнат", LocalDate.of(2002, 8, 15));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(2, violations.size());
    }

    @Test
    public void shouldNotPassLoginValidationWithBlanksInLogin() {
        User user = new User(15, "qwert@mail.ru", "Ma ngo15", "Игнат", LocalDate.of(2002, 8, 15));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
    }

    @Test
    public void shouldNotPassBirthdayValidation() {
        User user = new User(15, "qwert@mail.ru", "Mango15", "Игнат", LocalDate.of(3000, 8, 15));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
    }

    @Test
    public void shouldAddFriend() throws ValidationException {
        User user = new User(15, "qwert@mail.ru", "Mango15", "Игнат", LocalDate.of(2000, 8, 15));
        userService.createUser(user);
        User friend = new User(2, "adsdadsa@mail.ru", "Ma5", "Ольга", LocalDate.of(2005, 3, 1));
        userService.createUser(friend);
        userService.addToFriendList(user.getId(), friend.getId());
        assertEquals(List.of(friend), userService.friendsListForUser(user.getId()));
    }

    @Test
    public void shouldDeleteFriend() throws ValidationException {
        User user = new User(15, "qwert@mail.ru", "Mango15", "Игнат", LocalDate.of(2000, 8, 15));
        userService.createUser(user);
        User friend = new User(2, "adsdadsa@mail.ru", "Ma5", "Ольга", LocalDate.of(2005, 3, 1));
        userService.createUser(friend);

        userService.addToFriendList(user.getId(), friend.getId());
        userService.removeFromFriendList(user.getId(), friend.getId());

        assertEquals(Collections.emptyList(), List.copyOf(user.getFriends()));
    }

    @Test
    public void shouldFindMutualFriend() throws ValidationException {
        User user = new User(15, "qwert@mail.ru", "Mango15", "Игнат", LocalDate.of(2000, 8, 15));
        userService.createUser(user);
        User friend = new User(2, "adsdadsa@mail.ru", "Ma5", "Ольга", LocalDate.of(2005, 3, 1));
        userService.createUser(friend);
        User mutualFriend = new User(3, "rose@mail.ru", "Rose", "Melissa", LocalDate.of(2000, 8, 15));
        userService.createUser(mutualFriend);

        userService.addToFriendList(user.getId(), mutualFriend.getId());
        userService.addToFriendList(friend.getId(), mutualFriend.getId());

        List<User> mutual = userService.friendsListCommonOtherUsers(user.getId(), friend.getId());

        assertEquals(List.of(mutualFriend), mutual);
    }

    @Test
    public void shouldReturnAllFriends() throws ValidationException {
        User user = new User(15, "qwert@mail.ru", "Mango15", "Игнат", LocalDate.of(2000, 8, 15));
        userService.createUser(user);
        User friend = new User(2, "adsdadsa@mail.ru", "Ma5", "Ольга", LocalDate.of(2005, 3, 1));
        userService.createUser(friend);

        userService.createUser(friend);
        userService.addToFriendList(user.getId(), friend.getId());

        assertEquals(1, userService.friendsListForUser(user.getId()).size());
    }

    @Test
    public void shouldDeleteUser() throws ValidationException {
        User user = new User(15, "qwert@mail.ru", "Mango15", "Игнат", LocalDate.of(2000, 8, 15));
        userService.createUser(user);
        userService.removeUser(user);

        assertFalse(userService.findAllUsers().contains(user));
    }*//*


}
*/
