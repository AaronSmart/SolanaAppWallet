package com.econet.app.ServerBeans.historydetail;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Auto-generated: 2020-06-06 9:52:52
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Questionlist {

    @JsonProperty("HealthInfo")
    private List<Healthinfo> healthinfo;
    @JsonProperty("BasicInfo")
    private List<Basicinfo> basicinfo;
    public void setHealthinfo(List<Healthinfo> healthinfo) {
         this.healthinfo = healthinfo;
     }
     public List<Healthinfo> getHealthinfo() {
         return healthinfo;
     }

    public void setBasicinfo(List<Basicinfo> basicinfo) {
         this.basicinfo = basicinfo;
     }
     public List<Basicinfo> getBasicinfo() {
         return basicinfo;
     }

}