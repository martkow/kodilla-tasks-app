package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TASKS")
public class Task {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name= "NAME")
    private String title;

    @Column(name = "DESCRIPTION")
    private String content;
}
