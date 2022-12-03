package com.briup.crmmartketing.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author briup
 * @since 2022-11-28
 */
@TableName("sal_plan")
public class Plan implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "pla_id", type = IdType.AUTO)
      private Long plaId;

    private Long plaChcId;

    private String plaTodo;

    private String plaResult;
    
    public Long getPlaId() {
        return plaId;
    }

      public void setPlaId(Long plaId) {
          this.plaId = plaId;
      }
    
    public Long getPlaChcId() {
        return plaChcId;
    }

      public void setPlaChcId(Long plaChcId) {
          this.plaChcId = plaChcId;
      }
    
    public String getPlaTodo() {
        return plaTodo;
    }

      public void setPlaTodo(String plaTodo) {
          this.plaTodo = plaTodo;
      }
    
    public String getPlaResult() {
        return plaResult;
    }

      public void setPlaResult(String plaResult) {
          this.plaResult = plaResult;
      }

    @Override
    public String toString() {
        return "Plan{" +
              "plaId = " + plaId +
                  ", plaChcId = " + plaChcId +
                  ", plaTodo = " + plaTodo +
                  ", plaResult = " + plaResult +
              "}";
    }
}
