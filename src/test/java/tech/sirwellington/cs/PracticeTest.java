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

import java.util.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import tech.sirwellington.alchemy.generator.*;
import tech.sirwellington.alchemy.test.junit.ThrowableAssertion;
import tech.sirwellington.alchemy.test.junit.runners.*;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;

@RunWith(AlchemyTestRunner.class)
@Repeat
public class PracticeTest
{
    private Practice.NodeList<String> instance;

    @GenerateString
    private String string;

    private List<String> strings;

    @Before
    public void setup()
    {
        instance = new Practice.NodeListImplementation<>();

        int size = NumberGenerators.integers(10, 100).get();
        strings = CollectionGenerators.listOf(StringGenerators.alphabeticStrings(), size);
    }

    @Test
    public void testContainsWhenDoesnt() throws Exception
    {
        assertFalse(instance.contains(string));
    }

    @Test
    public void testContainsWhenDoes() throws Exception
    {
        instance.add(string);

        assertTrue(instance.contains(string));
    }

    @Test
    public void testIsEmptyWhenEmpty() throws Exception
    {
        assertTrue(instance.isEmpty());
    }

    @Test
    public void testIsEmptyWhenNotEmpty() throws Exception
    {
        instance.add(string);
        assertFalse(instance.isEmpty());
    }

    @Test
    public void testSizeWhenEmpty() throws Exception
    {
        int size = instance.size();
        assertThat(size, equalTo(0));
    }

    @Test
    public void testSizeWhenHasElement() throws Exception
    {
        instance.add(string);

        int size = instance.size();
        assertThat(size, equalTo(1));
    }

    @Test
    public void testSizeWithMultiple() throws Exception
    {
        strings.forEach(instance::add);

        int size = instance.size();
        assertThat(size, equalTo(strings.size()));
    }

    @Test
    public void testAdd() throws Exception
    {
        instance.add(string);
    }

    @Test
    public void testAddMultiple() throws Exception
    {
        strings.forEach(instance::add);

        strings.forEach(s -> assertTrue(instance.contains(s)));
    }

    @Test
    public void testRemoveFirstWhenEmpty() throws Exception
    {
        String result = instance.removeFirst();
        assertThat(result, nullValue());
    }

    @Test
    public void testRemoveFirst() throws Exception
    {
        instance.add(string);

        String result = instance.removeFirst();
        assertThat(result, equalTo(string));

        assertTrue(instance.isEmpty());
    }

    @Test
    public void testGetAtWithBadIndex() throws Exception
    {
        ThrowableAssertion.assertThrows(() ->
        {
           instance.getAt(NumberGenerators.negativeIntegers().get());
        });
    }

    @Test
    public void testGetAt() throws Exception
    {
        strings.forEach(instance::add);

        int index = NumberGenerators.integers(0, strings.size()-1).get();

        String result = instance.getAt(index);
        assertThat(result, equalTo(strings.get(index)));
    }

    @Test
    public void testReverseList() throws Exception
    {
        List<String> reversed = new ArrayList<>(strings);
        Collections.reverse(reversed);

        strings.forEach(instance::add);
        Practice.NodeList<String> reversedNodeList = instance.reversed();

        for (int i = 0; i < reversed.size(); ++i)
        {
            String expected = reversed.get(i);
            String result = reversedNodeList.getAt(i);
            assertThat(result, equalTo(expected));
        }
    }

}