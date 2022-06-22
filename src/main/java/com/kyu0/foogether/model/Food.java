package com.kyu0.foogether.model;



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
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Builder
    public Food(Integer id, String name, Integer price, String description, boolean isSoldout, Restaurant restaurant) {
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
