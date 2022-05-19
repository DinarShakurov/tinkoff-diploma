package ru.tfs.collections_generics.task4;

import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchServiceTest {

    @Test
    public void test1() {
        User vasilii = new User("Василий");
        User arina = new User("Арина");
        User yulya = new User("Юля");
        User evgenii = new User("Евгений");
        User konstantin = new User("Константин");
        User vladimir = new User("Владимир");
        User mariya = new User("Мария");
        User dmitrii = new User("Дмитрий");
        User anatolii = new User("Анатолий");
        User gennadii = new User("Геннадий");
        User anna = new User("Анна");
        User mikhail = new User("Михаил");
        User sergei = new User("Сергей");
        User natasha = new User("Наташа");
        vasilii.setFriends(asList(arina, yulya));
        arina.setFriends(asList(vasilii, dmitrii));
        yulya.setFriends(asList(vasilii, konstantin, evgenii));
        evgenii.setFriends(asList(yulya, natasha));
        konstantin.setFriends(asList(yulya, vladimir, mariya, natasha));
        vladimir.setFriends(asList(konstantin, mariya));
        mariya.setFriends(asList(konstantin, vladimir));
        dmitrii.setFriends(asList(arina, anatolii, sergei));
        anatolii.setFriends(asList(dmitrii, gennadii, mikhail));
        gennadii.setFriends(asList(anatolii, anna));
        anna.setFriends(asList(gennadii, mikhail));
        mikhail.setFriends(asList(anatolii, sergei, anna, natasha));
        sergei.setFriends(asList(dmitrii, mikhail));
        natasha.setFriends(asList(konstantin, evgenii, mikhail));

        SearchService service = new DefaultSearchService();

        List<User> foundFriends = service.searchForFriendsInDepth(vasilii, "Наташа");
        assertTrue(foundFriends.contains(natasha));
        foundFriends = service.searchForFriendsInWidth(vasilii, "Наташа");
        assertTrue(foundFriends.contains(natasha));
    }

    @Test
    public void test2() {
        User vasilii = new User("Василий");
        User arina = new User("Арина");
        User yulya = new User("Юля");
        User evgenii = new User("Евгений");
        User konstantin = new User("Константин");
        User vladimir = new User("Владимир");
        User mariya = new User("Мария");
        User dmitrii = new User("Дмитрий");
        User anatolii = new User("Анатолий");
        User gennadii = new User("Геннадий");
        User anna = new User("Анна");
        User mikhail = new User("Михаил");
        User sergei = new User("Сергей");
        User natasha = new User("Наташа");
        User natasha2 = new User("Наташа");
        vasilii.setFriends(asList(arina, yulya));
        arina.setFriends(asList(vasilii, dmitrii, natasha2));
        yulya.setFriends(asList(vasilii, evgenii));
        evgenii.setFriends(asList(yulya));
        konstantin.setFriends(asList(vladimir, mariya));
        vladimir.setFriends(asList(konstantin));
        mariya.setFriends(asList(konstantin, vasilii));
        dmitrii.setFriends(asList(arina, anatolii, natasha));
        anatolii.setFriends(asList(dmitrii, gennadii));
        gennadii.setFriends(asList(anatolii));
        anna.setFriends(asList(mikhail));
        mikhail.setFriends(asList(sergei, anna));
        sergei.setFriends(asList(natasha, mikhail));
        natasha.setFriends(asList(dmitrii, sergei));
        natasha2.setFriends(asList(arina));

        SearchService service = new DefaultSearchService();

        List<User> foundFriends = service.searchForFriendsInDepth(vasilii, "Наташа");
        assertTrue(foundFriends.contains(natasha));
        assertTrue(foundFriends.contains(natasha2));
        foundFriends = service.searchForFriendsInWidth(vasilii, "Наташа");
        System.out.println(foundFriends);
        assertTrue(foundFriends.contains(natasha));
        assertTrue(foundFriends.contains(natasha2));
    }

    @Test
    public void test3() {
        User vasilii = new User("Василий");
        User arina = new User("Арина");
        User mariya = new User("Мария");
        User vladimir = new User("Владимир");
        User evgenii = new User("Евгений");
        User yulya = new User("Юля");
        User konstantin = new User("Константин");
        User dmitrii = new User("Дмитрий");
        User natasha = new User("Наташа");
        vasilii.setFriends(asList(arina, mariya, vladimir, evgenii));
        arina.setFriends(asList(vasilii, dmitrii, vasilii));
        yulya.setFriends(asList(konstantin, evgenii, vladimir, dmitrii));
        evgenii.setFriends(asList(yulya, vasilii, dmitrii));
        konstantin.setFriends(asList(vladimir, mariya, yulya, natasha));
        vladimir.setFriends(asList(konstantin, vasilii, yulya));
        mariya.setFriends(asList(konstantin, vasilii, natasha));
        dmitrii.setFriends(asList(arina, evgenii, natasha, yulya));
        natasha.setFriends(asList(dmitrii, arina, konstantin, mariya));
        SearchService service = new DefaultSearchService();
        List<User> foundFriends = service.searchForFriendsInDepth(vasilii, "Наташа");
        assertTrue(foundFriends.contains(natasha));
        foundFriends = service.searchForFriendsInWidth(vasilii, "Наташа");
        assertTrue(foundFriends.contains(natasha));
    }
}