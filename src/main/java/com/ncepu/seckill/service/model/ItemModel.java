package com.ncepu.seckill.service.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 该model为领域模型
 * 以后先设计领域模型再设计数据库表结构
 */
public class ItemModel {

    private Integer id;

    @NotBlank(message = "商品平成不能为空")
    private String title;

    @NotNull(message = "商品价格不能为空")
    @Min(value = 0, message = "商品价格必须大于0")
    private BigDecimal price; // 这里为什么不使用Double？java的double传送到前端会存在精度问题。

    // 商品的库存
    @NotNull(message = "库存不能不填")
    private Integer stock;

    @NotBlank(message = "描述信息不能为空")
    private String description;

    // 商品的销量
    private Integer sales;

    // 描述商品图片的url
    @NotBlank(message = "图片信息不能为空")
    private String imgUrl;

    // 使用聚合模型
    // 若promoModel不为空，则表示其还拥有未结束的活动
    private PromoModel promoModel;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public PromoModel getPromoModel() {
        return promoModel;
    }

    public void setPromoModel(PromoModel promoModel) {
        this.promoModel = promoModel;
    }
}
