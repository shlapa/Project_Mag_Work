package com.amr.project.model.entity;

import com.amr.project.model.entity.report.SalesHistory;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    //TODO: проверить "правильность" параметра unique (могут быть товары с одинаковыми наименованиями у разных Shops, Users, CartItems)
    @Column(name = "name"/*, unique = true*/)
    private String name;

    @Column(name = "base_price")
    private Long basePrice;

    @Column(name = "price")
    private Long price;

    @Column(name = "count")
    private int count; //Вероятно это ненужное поле. Количество товара в корзине хранится в CartItem

    @Column(name = "rating")
    private double rating;

    private String description;
    private int discount;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

//вероятно лишнее поле. Иформация о товарах в корзине хранится в сущности CartItem. Сущности товар д.б. все равно в каких корзинах он лежит
//    @ManyToOne(fetch = FetchType.LAZY)
//    @ToString.Exclude
//    private CartItem cartItem;


    @OneToMany(
            cascade = {CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH,
                    CascadeType.DETACH},
            orphanRemoval = true)
    @JoinColumn(name = "item_id")
    @ToString.Exclude
    private List<Image> images;


    @OneToMany(
            mappedBy = "item",
            cascade = {CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH,
                    CascadeType.DETACH},
            orphanRemoval = true
    )
    @ToString.Exclude
    private List<Review> reviews;


    @ManyToMany(mappedBy = "items", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Favorite> favorites;



    @ManyToMany(mappedBy = "itemsInOrder")
    @OrderBy("orderDate ASC")
    @ToString.Exclude
    private List<Order> orders = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Shop shop;


    @OneToMany(
            mappedBy = "item",
            cascade = {CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH,
                    CascadeType.DETACH},
            orphanRemoval = true
    )
    @ToString.Exclude
    private List<SalesHistory> history;

    @OneToMany(
            mappedBy = "item",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @ToString.Exclude
    private List<Feedback> feedbacks;


    private boolean isModerated;
    private boolean isModerateAccept;
    private String moderatedRejectReason;
    private boolean isPretendedToBeDeleted;

    public Item (String name, Long price, int count, double rating, String description, Category category, Shop shop) {
        this.name = name;
        this.price = price;
        this.count = count;
        this.rating = rating;
        this.description = description;
        this.category = category;
        this.shop = shop;
    }


    public boolean isPretendedToBeDeleted() {
        return isPretendedToBeDeleted;
    }



    public void setPretendedToBeDeleted(boolean pretendedToBeDeleted) {
        isPretendedToBeDeleted = pretendedToBeDeleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Item item = (Item) o;
        return id != null && Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
