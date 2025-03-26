package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 删除活动管理员请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class DeleteEventAdminRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -5483688345337512304L;

    /**
     * id
     */
    private Long eventAdminId;
}
