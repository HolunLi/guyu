package com.holun.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

public class Test {

    @org.junit.Test
    public void run() {
        Student student = new Student(1, "豪伦", 13, "路虎");
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(student, teacher);
        System.out.println(teacher);
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Teacher {
    private int id;
    private String name;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Student {
    private int id;
    private String name;
    private int age;
    private String car;
}
