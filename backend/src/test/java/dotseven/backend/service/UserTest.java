package dotseven.backend.service;

import dotseven.backend.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testEquals() {

        // Arramge
        User referenceUser = new User(
                "1",
                "2",
                "3",
                "4",
                LocalDate.EPOCH,
                "5",
                '6',
                "7"
        );

        User testSubject = new User (
                "1",
                "2",
                "3",
                "4",
                LocalDate.EPOCH,
                "5",
                '6',
                "7"
        );

        User nullSubject = new User (
                null,
                null,
                null,
                null,
                null,
                null,
                '0',
                null
        );

        User differentSubject = new User (
                "a",
                "b",
                "c",
                "d",
                LocalDate.now(),
                "e",
                'f',
                "g"
        );

        User mixedSubject = new User (
                "1",
                "2",
                "3",
                "4",
                LocalDate.EPOCH,
                "e",
                'f',
                "g"
        );

        // Act - Assert
        assertTrue(referenceUser.equals(referenceUser));
        assertTrue(referenceUser.equals(testSubject));

        assertFalse(referenceUser.equals(null));
        assertFalse(referenceUser.equals(new Object()));
        assertFalse(referenceUser.equals(nullSubject));
        assertFalse(referenceUser.equals(differentSubject));
        assertFalse(referenceUser.equals(mixedSubject));
        assertFalse(referenceUser.equals(new User()));
    }
}