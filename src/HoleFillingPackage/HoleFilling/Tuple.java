package HoleFillingPackage.HoleFilling;

import java.io.Serializable;
import java.util.Objects;

class Tuple<T, U> implements Serializable {
        private static final long serialVersionUID = 1L;
        private final T first;
        private final U second;

        public Tuple(T first, U second) {
            this.first = first;
            this.second = second;
        }
        public T getFirst() {
            return first;
        }

        public U getSecond() {
            return second;
        }

        @Override
        public String toString() {
            return "(" + first + ", " + second + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Tuple<?, ?> tuple = (Tuple<?, ?>) o;
            return Objects.equals(first, tuple.first) && Objects.equals(second, tuple.second);
        }

        @Override
        public int hashCode() {
            return Objects.hash(first, second);
        }
    }
