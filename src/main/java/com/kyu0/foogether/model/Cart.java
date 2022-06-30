package com.kyu0.foogether.model;

import java.util.List;

import javax.persistence.*;

import lombok.*;

@NoArgsConstructor
@Getter
@Entity
public class Cart {

    @Id @GeneratedValue
    private Long id;

    @OneToOne
    private Member member;

    @OneToOne
    private Restaurant restaurant;

    @JoinTable(name = "CART_FOOD")
    @OneToMany
    private List<Food> foods;

    @Builder
    public Cart(Long id) {
        this.id = id;
    }

    public void addFood(Food food) {
        foods.add(food);
    }

    public void deleteFood(Food food) {
        foods.remove(food);
    }

    @Override
    public String toString() {
        return "Cart [foods=" + foods + ", id=" + id + ", memberId=" + member.getId() + ", restaurantId=" + restaurant.getId() + "]";
    }

}
