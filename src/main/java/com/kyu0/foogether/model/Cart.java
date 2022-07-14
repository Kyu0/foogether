package com.kyu0.foogether.model;

import java.util.List;

import javax.persistence.*;

import lombok.*;

@NoArgsConstructor
@Getter
@Entity
public class Cart {

    @Id @GeneratedValue
    Long id;

    @OneToOne(mappedBy = "cart")
    private Member member;

    @OneToOne
    private Restaurant restaurant;

    @JoinTable(name = "CART_FOOD")
    @OneToMany
    private List<Food> foods;

    @Builder
    public Cart(Member member, Restaurant restaurant, List<Food> foods) {
        this.member = member;
        this.restaurant = restaurant;
        this.foods = foods;
    }

    public void addFood(Food food) {
        foods.add(food);
    }

    public void deleteFood(Food food) {
        foods.remove(food);
    }
}
