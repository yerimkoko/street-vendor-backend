package store.streetvendor.study;

import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class EqualsTest {

    @Test
    void 문제1() {
        // given
        Person person = new Person("토끼", 21);
        Person personTwo = new Person("토끼", 21);

        // when
        Set<Person> people = Set.of(person);

        // then
        assertThat(people.contains(personTwo)).isTrue();
    }

    class Person extends Object {

        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return age == person.age && Objects.equals(name, person.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age);
        }
    }

}
