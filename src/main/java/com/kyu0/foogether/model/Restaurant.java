package com.kyu0.foogether.model;

import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.kyu0.foogether.support.RestaurantType;
import static com.kyu0.foogether.utility.RegExpPattern.*;

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
    
    @Size(min = 2, max = 32, message = "가게 이름은 2자 이상, 32자 이하로 입력해주세요.")
    @Column(name = "name")
    private String name;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private RestaurantType type;
    
    @Pattern(regexp = BUSINESS_NUMBER_PATTERN, message = "사업자 등록 번호의 형식이 맞지 않습니다.")
    @Column(name = "business_number")
    private String businessNumber;
    
    @Column(name = "address")
    private String address;
    
    @Pattern(regexp = POST_NUMBER_PATTERN, message = "우편 번호의 형식이 맞지 않습니다.")
    @Column(name = "post_number")
    private String postNumber;

    @Size(max = 1000, message = "가게의 설명글은 1,000자 이하로 입력해주세요.")
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
    public Restaurant(Integer id, String name, RestaurantType type, String businessNumber, String address, String description
                    , String postNumber, Member member, Boolean isUse) {
        
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
