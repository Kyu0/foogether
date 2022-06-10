package com.kyu0.foogether.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * @param id
 * @param name 이름
 * @param price 가격
 * @param description 설명
 * @param isSoldout 품절 여부
 */
@NoArgsConstructor
@Getter
@ToString
@Entity
public class Food {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String name;

    @Column
    private Integer price;

    @Column
    private String description;
    
    @Column
    private boolean isSoldout;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
}
