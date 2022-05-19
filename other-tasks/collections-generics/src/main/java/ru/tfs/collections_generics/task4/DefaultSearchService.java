package ru.tfs.collections_generics.task4;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

public class DefaultSearchService implements SearchService {
    @Override
    public List<User> searchForFriendsInWidth(User me, String name) {
        Set<User> lookingFor = new HashSet<>();
        Set<User> visited = new HashSet<>();
        Queue<User> queue = new ArrayDeque<>();
        visited.add(me);
        queue.add(me);

        while (!queue.isEmpty()) {
            User current = queue.remove();
            for (User friend : current.getFriends()) {

                if (!visited.contains(friend)) {
                    visited.add(friend);
                    queue.add(friend);

                    if (Objects.equals(friend.getName(), name))
                        lookingFor.add(friend);
                }
            }
        }
        return new ArrayList<>(lookingFor);
    }

    @Override
    public List<User> searchForFriendsInDepth(User me, String name) {
        return dfs(me, name, false);
    }

    private List<User> dfs(User me, String name, boolean usingRecursion) {
        Set<User> visited = new HashSet<>();
        Set<User> lookingFor = new HashSet<>();
        if (usingRecursion) {
            dfsRecursive(me, name, visited, lookingFor);
        } else {
            dfsWithoutRecursion(me, name, visited, lookingFor);
        }
        return new ArrayList<>(lookingFor);
    }

    private void dfsRecursive(User current, String name, Set<User> visited, Set<User> lookingFor) {
        visited.add(current);
        for (User friend : current.getFriends()) {

            if (!visited.contains(friend)) {
                if (Objects.equals(friend.getName(), name))
                    lookingFor.add(friend);

                dfsRecursive(friend, name, visited, lookingFor);
            }
        }
    }

    private void dfsWithoutRecursion(User me, String name, Set<User> visited, Set<User> lookingFor) {
        Deque<User> stack = new ArrayDeque<>();
        stack.push(me);

        while (!stack.isEmpty()) {
            User current = stack.pop();
            if (!visited.contains(current)) {
                visited.add(current);

                if (Objects.equals(current.getName(), name))
                    lookingFor.add(current);

                for (User friend : current.getFriends()) {
                    if (!visited.contains(friend))
                        stack.push(friend);
                }
            }
        }
        lookingFor.remove(me);
    }
}
