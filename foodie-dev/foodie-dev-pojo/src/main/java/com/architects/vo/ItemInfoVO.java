package com.architects.vo;

import com.architects.pojo.Items;
import com.architects.pojo.ItemsImg;
import com.architects.pojo.ItemsParam;
import com.architects.pojo.ItemsSpec;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @ClassName ItemInfoVO
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2021/3/21 0021
 * @Version V1.0
 **/
@Data
@Builder
public class ItemInfoVO {
    /**
     * 商品
     */
    private Items item;

    /**
     * 商品图片
     */
    private List<ItemsImg> itemImgList;

    /**
     * 商品规格
     */
    private List<ItemsSpec> itemSpecList;

    /**
     * 商品参数
     */
    private ItemsParam itemParams;
}
