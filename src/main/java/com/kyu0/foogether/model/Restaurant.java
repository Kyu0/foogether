package com.kyu0.foogether.model;

import com.kyu0.foogether.support.RestaurantType;
import java.util.*;
import javax.validation.constraints.*;
import javax.persistence.*;

import lombok.*;

import static com.kyu0.foogether.utility.RegExpPattern.*;


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
@NamedEntityGraphs({
    @NamedEntityGraph(name = "Restaurant.withAll", attributeNodes = {@NamedAttributeNode("member"), @NamedAttributeNode("foods")})
    , @NamedEntityGraph(name = "Restaurant.withMember", attributeNodes = {@NamedAttributeNode("member")})
    , @NamedEntityGraph(name = "Restaurant.withFoods", attributeNodes = {@NamedAttributeNode("foods")})
})
@NoArgsConstructor
@Getter
@Entity
public class Restaurant {
    
    @Id @GeneratedValue
    @Column(name = "id")
    private Integer id;
    
    @Size(min = 2, max = 32, message = "가게 이름은 2자 이상, 32자 이하로 입력해주세요.")
    @Column(name = "name", nullable = false, length = 32)
    private String name;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 16)
    private RestaurantType type;
    
    @Pattern(regexp = BUSINESS_NUMBER_PATTERN, message = "사업자 등록 번호의 형식이 맞지 않습니다.")
    @Column(name = "business_number", nullable = false, length = 12)
    private String businessNumber;
    
    @Column(name = "address", nullable = false, length = 128)
    private String address;
    
    @Pattern(regexp = POST_NUMBER_PATTERN, message = "우편 번호의 형식이 맞지 않습니다.")
    @Column(name = "post_number", nullable = false, length = 5)
    private String postNumber;

    @Size(max = 1000, message = "가게의 설명글은 1,000자 이하로 입력해주세요.")
    @Column(name = "description", length = 1000)
    private String description;
    
    @Column(name = "use", nullable = false)
    private Boolean isUse;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
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

    @Override
    public String toString() {
        return "Restaurant [address=" + address + ", businessNumber=" + businessNumber + ", description=" + description
                + ", id=" + id + ", isUse=" + isUse + ", memberId=" + member.getId() + ", name=" + name
                + ", postNumber=" + postNumber + ", type=" + type + "]";
    }
}
