package com.kyu0.foogether.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.*;

/**
 * 
 * 가게 정보를 담고 있는 클래스
 * 
 * @param id 가게의 식별 번호
 * @param name 이름
 * @param type 음식점 종류 (한식, 일식 등)
 * @param businessNumber 사업자 등록 번호
 * @param address 주소
 * @param postNumber 우편 번호
 * @param userId 사장 id
 * @param isUse 사용 여부
 */
@NoArgsConstructor
@Getter
@ToString
@Entity
public class Restaurant {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "type")
    private Integer type;
    
    @Column(name = "business_number")
    private Integer businessNumber;
    
    @Column(name = "address")
    private String address;
    
    @Column(name = "post_number")
    private Integer postNumber;

    @Column(name = "description")
    private String description;
    
    @Column(name = "use")
    private Boolean isUse;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant", orphanRemoval = true)
    private List<Food> foods = new ArrayList<>();
    
    @Builder
    public Restaurant(Integer id, String name, Integer type, Integer businessNumber, String address, String description
                    , Integer postNumber, Member member, Boolean isUse) {
        
        this.id = id;
        this.name = name;
        this.type = type;
        this.businessNumber = businessNumber;
        this.address = address;
        this.description = description;
        this.postNumber = postNumber;
        this.member = member;
        this.isUse = isUse;
    }

    public void addFood (Food food) {
        this.foods.add(food);
        if (food.getRestaurant() != this) {
            food.setRestaurant(this);
        }
    }
}
