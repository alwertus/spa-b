package com.tretsoft.spa.helpers;

import lombok.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ObjectOperationsTest {

    @Test
    public void testCopyAttributes() {
        TestCopyClass from = TestCopyClass
                .builder()
                .id(1000L)
                .name("Name1")
                .field1("Field1-1")
                .field2("Field2-1")
                .field3("Field3-1")
                .build();

        TestCopyClass to = TestCopyClass.builder().id(5L).field2("Field2-2").field4("original").build();

        ObjectOperations.copyNonNullFields(from, to, List.of("id", "field2"));

        assertEquals(5L, to.getId());
        assertEquals("Name1", to.getName());
        assertEquals("Field1-1", to.getField1());
        assertEquals("Field2-2", to.getField2());
        assertEquals("Field3-1", to.getField3());
        assertEquals("original", to.getField4());
    }

}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
class TestCopyClass {
    Long id;
    String name;
    String field1;
    String field2;
    String field3;
    String field4;
}