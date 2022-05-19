package ru.tfs.collections_generics.task2;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import static java.util.Arrays.asList;

public class Top10LikedPosts {

    public static List<Post> getTop10(List<Post> posts) {
        TreeSet<Post> set = new TreeSet<>((o1, o2) -> {
            int result = o2.getLikesCount().compareTo(o1.getLikesCount());
            return result != 0 ? result : 1;
        });
        for (Post post : posts) {
            set.add(post);
            if (set.size() > 10) {
                set.pollLast();
            }
        }
        return new ArrayList<>(set);
    }

    public static class Post {
        private final String text;
        private final Integer likesCount;

        public Post(String text, Integer likesCount) {
            this.text = text;
            this.likesCount = likesCount;
        }

        public String getText() {
            return text;
        }

        public Integer getLikesCount() {
            return likesCount;
        }

        @Override
        public String toString() {
            return "Post{" +
                    "text='" + text + '\'' +
                    ", likesCount=" + likesCount +
                    '}';
        }
    }

    public static void main(String[] args) {
        Post post0 = new Post("post0", 31);
        Post post1 = new Post("post1", 531);
        Post post2 = new Post("post2", 17);
        Post post3 = new Post("post3", 774);
        Post post4 = new Post("post4", 562);
        Post post5 = new Post("post5", 688);
        Post post6 = new Post("post6", 451);
        Post post7 = new Post("post7", 254);
        Post post8 = new Post("post8", 900);
        Post post9 = new Post("post9", 461);
        Post post10 = new Post("post10", 849);
        Post post11 = new Post("post11", 826);
        Post post12 = new Post("post12", 808);
        Post post13 = new Post("post13", 612);
        Post post14 = new Post("post14", 25);
        Post post15 = new Post("post15", 664);
        Post post16 = new Post("post16", 465);
        Post post17 = new Post("post17", 912);
        Post post18 = new Post("post18", 912);
        Post post19 = new Post("post19", 412);
        List<Post> posts = getTop10(asList(post0, post1, post2, post3, post4, post5, post6, post7, post8, post9, post10, post11, post12, post13, post14, post15, post16, post17, post18, post19));
        posts.forEach(System.out::println);
    }
}
