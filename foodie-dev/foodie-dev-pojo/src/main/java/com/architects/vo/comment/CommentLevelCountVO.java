package com.architects.vo.comment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentLevelCountVO {
    /**
     * 总评价数
     */
    private Integer totalCounts;

    /**
     * 好评数
     */
    private Integer goodCounts;

    /**
     * 中评数
     */
    private Integer normalCounts;

    /**
     * 差评数
     */
    private Integer badCounts;
}
