/*
 * Copyright © 2019. Sir Wellington.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tech.sirwellington.cs;

import java.util.Iterator;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author SirWellington
 */
final class Practice
{

    private static final Logger LOG = LoggerFactory.getLogger(Practice.class);

    interface NodeList<V> extends Iterable<V>
    {
        boolean contains(V value);

        boolean isEmpty();

        int size();

        V removeFirst();

        V getAt(int index);

        void add(V value);

        NodeList<V> reversed();

    }

    static class NodeListImplementation<V> implements NodeList<V>
    {
        private Node<V> head = null;
        private Node<V> tail = null;

        @Override
        public boolean contains(V value)
        {
            for (V val: this)
            {
                if (val != null && val.equals(value))
                {
                    return true;
                }
            }

            return false;
        }

        @Override
        public boolean isEmpty()
        {
            return head == null;
        }

        @Override
        public V removeFirst()
        {
            if (head == null)
            {
                return null;
            }

            V value = head.value;

            this.head = head.next;

            return value;
        }

        @Override
        public V getAt(int index)
        {
            if (index < 0)
            {
                throw new ArrayIndexOutOfBoundsException(index);
            }

            int i = 0;

            for (V value : this)
            {
                if (i == index)
                {
                    return value;
                }

                ++i;
            }

            throw new ArrayIndexOutOfBoundsException(index);
        }

        @Override
        public void add(V value)
        {
            if (head == null)
            {
                Node<V> newHead = new Node<>(value, null);
                this.head = newHead;
                this.tail = newHead;
                return;
            }

            Node<V> newTail = new Node<>(value, null);
            this.tail.next = newTail;
            this.tail = newTail;
        }

        @Override
        public int size()
        {
            int size = 0;

            for (V value: this)
            {
                size += 1;
            }

            return size;
        }

        @Override
        public NodeList<V> reversed()
        {
            Node<V> newHead = tail;
            Node<V> newTail = reverseRecursive(head);

            NodeListImplementation<V> newList = new NodeListImplementation<>();
            newList.head = newHead;
            newList.tail = newTail;

            return newList;
        }

        private Node<V> reverseRecursive(Node<V> head)
        {
            if (head == null || head.next == null)
            {
                return head;
            }

            Node<V> next = head.next;
            Node<V> newTail = reverseRecursive(next);
            newTail.next = head;
            head.next = null;

            return head;
        }

        @NotNull
        @Override
        public Iterator<V> iterator()
        {
            return new CustomIterator();
        }

        private class CustomIterator implements Iterator<V>
        {
            private Node<V> current = head;

            @Override
            public boolean hasNext()
            {
                return current != null;
            }

            @Override
            public V next()
            {
                V value = current.value;
                current = current.next;
                return value;
            }

            @Override
            public void remove()
            {
                throw new UnsupportedOperationException("remove");
            }
        }

    }


    static class Node<V>
    {
        private Node<V> next;
        private V value;

        Node(V value, Node<V> next)
        {
            this.value = value;
            this.next = next;
        }

        @Override
        public boolean equals(Object o)
        {
            if (this == o)
            {
                return true;
            }
            if (o == null || getClass() != o.getClass())
            {
                return false;
            }
            Node<?> node = (Node<?>) o;
            return Objects.equals(value, node.value);
        }

        @Override
        public int hashCode()
        {
            return Objects.hash(value);
        }
    }

}
