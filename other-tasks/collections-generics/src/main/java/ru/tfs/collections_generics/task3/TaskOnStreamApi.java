package ru.tfs.collections_generics.task3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toMap;

public class TaskOnStreamApi {

    public static Integer task1(Client[] clients, String name) {
        // 1. Рассчитать суммарный возраст для определенного имени
        Integer ageSummary = stream(clients)
                .filter(client -> name.equals(client.getName()))
                .map(Client::getAge)
                .reduce(0, Integer::sum);
        return ageSummary;
    }

    public static Set<String> task2(Client[] clients) {
        // 2. Получить Set, который содержит в себе только имена клиентов в порядке их упоминания в исходном массиве.
        Set<String> namesInDefaultOrder = stream(clients)
                .map(Client::getName)
                .collect(toCollection(LinkedHashSet::new));
        return namesInDefaultOrder;
    }

    public static boolean task3(Client[] clients, int currentAge) {
        // 3. Узнать, содержит ли список хотя бы одного клиента, у которого возраст больше заданного числа.
        boolean contains = stream(clients).anyMatch(client -> client.getAge() > currentAge);
        return contains;
    }

    public static Map<Integer, String> task4(Client[] clients) {
        // 4. Преобразовать массив в Map, у которой ключ - уникальный идентификатор, значение - имя.
        // Поддержать порядок, в котором клиенты добавлены в массив.
        Map<Integer, String> map = stream(clients)
                .collect(toMap(Client::getId, Client::getName, (s, s2) -> s2, LinkedHashMap::new));
        return map;
    }

    public static Map<Integer, Collection<Client>> task5(Client[] clients) {
        // 5. Преобразовать массив в Map, у которой ключ - возраст, значение - коллекция клиентов с таким возрастом.
        Map<Integer, Collection<Client>> map = stream(clients)
                .collect(
                        groupingBy(
                                Client::getAge, mapping(Function.identity(), toCollection(ArrayList::new))
                        )
                );
        // или просто .collect(groupingBy(Client::getAge));
        return map;
    }

    public static String task6(Client[] clients) {
        // 6. Получить строку, содержащую телефоны всех клиентов через запятую.
        // Предусмотреть, что у клиента телефонов может и не быть.
        String phones = stream(clients)
                .flatMap(
                        client -> ofNullable(client.getPhones())
                                .orElseGet(Collections::emptyList)
                                .stream()
                )
                .map(Phone::getPhoneNumber)
                .collect(joining(", "));
        return phones;
    }

    public static Client task7(Client[] clients) {
        // 7. Найти самого возрастного клиента, которой пользуется стационарным телефоном
        Client oldest = stream(clients)
                .filter(
                        client -> ofNullable(client.getPhones())
                                .orElseGet(Collections::emptyList)
                                .stream()
                                .anyMatch(phone -> PhoneType.STATIONARY.equals(phone.type))
                )
                .max(Comparator.comparingInt(Client::getAge))
                .orElse(null);
        return oldest;
    }

    private static class Client {

        public Client(int id, String name, int age, List<Phone> phones) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.phones = phones;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public List<Phone> getPhones() {
            return phones;
        }

        private final int id;
        private final String name;
        private final int age;
        private final List<Phone> phones;

        @Override
        public String toString() {
            return "Client{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", age=" + age +
                    ", phones=" + phones +
                    '}';
        }
    }

    private static class Phone {
        public Phone(String phoneNumber, PhoneType type) {
            this.phoneNumber = phoneNumber;
            this.type = type;
        }

        private final String phoneNumber;
        private final PhoneType type;

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public PhoneType getType() {
            return type;
        }
    }

    private enum PhoneType {
        STATIONARY,
        MOBILE;
    }

    private static Client[] generateArray() {
        Client[] clients = new Client[20];

        Random random = new Random();
        Phone phone0 = new Phone("phoneNumber0", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Phone phone_0 = new Phone("phoneNumber_0", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Phone phone__0 = new Phone("phoneNumber__0", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Client client0 = new Client(0, "client0", 77, asList(phone0, phone_0, phone__0));

        Phone phone1 = new Phone("phoneNumber1", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Phone phone_1 = new Phone("phoneNumber_1", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Phone phone__1 = new Phone("phoneNumber__1", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Client client1 = new Client(1, "client1", 11, asList(phone1, phone_1, phone__1));

        Phone phone2 = new Phone("phoneNumber2", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Phone phone_2 = new Phone("phoneNumber_2", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Phone phone__2 = new Phone("phoneNumber__2", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Client client2 = new Client(2, "client2", 33, asList(phone2, phone_2, phone__2));

        Phone phone3 = new Phone("phoneNumber3", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Phone phone_3 = new Phone("phoneNumber_3", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Phone phone__3 = new Phone("phoneNumber__3", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Client client3 = new Client(3, "client3", 64, asList(phone3, phone_3, phone__3));

        Phone phone4 = new Phone("phoneNumber0", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Phone phone_4 = new Phone("phoneNumber_0", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Phone phone__4 = new Phone("phoneNumber__0", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Client client4 = new Client(4, "client0", 82, asList(phone4, phone_4, phone__4));

        Phone phone5 = new Phone("phoneNumber1", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Phone phone_5 = new Phone("phoneNumber_1", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Phone phone__5 = new Phone("phoneNumber__1", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Client client5 = new Client(5, "client1", 10, asList(phone5, phone_5, phone__5));

        Phone phone6 = new Phone("phoneNumber2", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Phone phone_6 = new Phone("phoneNumber_2", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Phone phone__6 = new Phone("phoneNumber__2", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Client client6 = new Client(6, "client2", 1, asList(phone6, phone_6, phone__6));

        Phone phone7 = new Phone("phoneNumber3", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Phone phone_7 = new Phone("phoneNumber_3", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Phone phone__7 = new Phone("phoneNumber__3", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Client client7 = new Client(7, "client3", 7, asList(phone7, phone_7, phone__7));

        Phone phone8 = new Phone("phoneNumber0", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Phone phone_8 = new Phone("phoneNumber_0", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Phone phone__8 = new Phone("phoneNumber__0", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Client client8 = new Client(8, "client0", 97, asList(phone8, phone_8, phone__8));

        Phone phone9 = new Phone("phoneNumber1", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Phone phone_9 = new Phone("phoneNumber_1", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Phone phone__9 = new Phone("phoneNumber__1", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Client client9 = new Client(9, "client1", 40, asList(phone9, phone_9, phone__9));

        Phone phone10 = new Phone("phoneNumber2", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Phone phone_10 = new Phone("phoneNumber_2", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Phone phone__10 = new Phone("phoneNumber__2", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Client client10 = new Client(10, "client2", 15, asList(phone10, phone_10, phone__10));

        Phone phone11 = new Phone("phoneNumber3", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Phone phone_11 = new Phone("phoneNumber_3", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Phone phone__11 = new Phone("phoneNumber__3", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Client client11 = new Client(11, "client3", 30, asList(phone11, phone_11, phone__11));

        Phone phone12 = new Phone("phoneNumber0", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Phone phone_12 = new Phone("phoneNumber_0", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Phone phone__12 = new Phone("phoneNumber__0", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Client client12 = new Client(12, "client0", 59, asList(phone12, phone_12, phone__12));

        Phone phone13 = new Phone("phoneNumber1", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Phone phone_13 = new Phone("phoneNumber_1", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Phone phone__13 = new Phone("phoneNumber__1", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Client client13 = new Client(13, "client1", 24, asList(phone13, phone_13, phone__13));

        Phone phone14 = new Phone("phoneNumber2", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Phone phone_14 = new Phone("phoneNumber_2", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Phone phone__14 = new Phone("phoneNumber__2", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Client client14 = new Client(14, "client2", 84, asList(phone14, phone_14, phone__14));

        Phone phone15 = new Phone("phoneNumber3", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Phone phone_15 = new Phone("phoneNumber_3", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Phone phone__15 = new Phone("phoneNumber__3", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Client client15 = new Client(15, "client3", 92, asList(phone15, phone_15, phone__15));

        Phone phone16 = new Phone("phoneNumber0", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Phone phone_16 = new Phone("phoneNumber_0", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Phone phone__16 = new Phone("phoneNumber__0", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Client client16 = new Client(16, "client0", 47, asList(phone16, phone_16, phone__16));

        Phone phone17 = new Phone("phoneNumber1", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Phone phone_17 = new Phone("phoneNumber_1", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Phone phone__17 = new Phone("phoneNumber__1", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Client client17 = new Client(17, "client1", 96, asList(phone17, phone_17, phone__17));

        Phone phone18 = new Phone("phoneNumber2", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Phone phone_18 = new Phone("phoneNumber_2", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Phone phone__18 = new Phone("phoneNumber__2", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Client client18 = new Client(18, "client2", 65, asList(phone18, phone_18, phone__18));

        Phone phone19 = new Phone("phoneNumber3", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Phone phone_19 = new Phone("phoneNumber_3", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Phone phone__19 = new Phone("phoneNumber__3", PhoneType.values()[random.nextInt(PhoneType.values().length)]);
        Client client19 = new Client(19, "client3", 6, asList(phone19, phone_19, phone__19));

        clients[0] = client0;
        clients[1] = client1;
        clients[2] = client2;
        clients[3] = client3;
        clients[4] = client4;
        clients[5] = client5;
        clients[6] = client6;
        clients[7] = client7;
        clients[8] = client8;
        clients[9] = client9;
        clients[10] = client10;
        clients[11] = client11;
        clients[12] = client12;
        clients[13] = client13;
        clients[14] = client14;
        clients[15] = client15;
        clients[16] = client16;
        clients[17] = client17;
        clients[18] = client18;
        clients[19] = client19;

        return clients;
    }
}
