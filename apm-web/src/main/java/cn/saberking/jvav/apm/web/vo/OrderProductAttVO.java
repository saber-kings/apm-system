package cn.saberking.jvav.apm.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author wyx
 * @version 1.0
 * @date 2020/10/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductAttVO {
   private Map<String, Object> map;
   private Integer date;
}
