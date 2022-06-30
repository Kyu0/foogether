package com.kyu0.foogether.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

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
@Setter
@Entity
public class Food {
    
    @Id @GeneratedValue
    private Long id;

    @Size(max = 32, message = "음식의 이름은 32자 이하로 설정해주세요.")
    @Column(name = "name", nullable = false, length = 32)
    private String name;

    @Min(value = 0, message = "음식의 가격은 0원 이상이어야 합니다.")
    @Column(name = "price", nullable = false)
    private Integer price;

    @Size(max = 255, message = "설명은 255자 이하로 설정해주세요.")
    @Column(name = "description", length = 255)
    private String description;
    
    @Column(name = "soldout", nullable = false)
    private boolean isSoldout;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Builder
    public Food(Long id, String name, Integer price, String description, boolean isSoldout, Restaurant restaurant) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.isSoldout = isSoldout;
        this.restaurant = restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        if (this.restaurant != null) {
            this.restaurant.getFoods().remove(this);
        }

        this.restaurant = restaurant;
        restaurant.getFoods().add(this);
    }
}
