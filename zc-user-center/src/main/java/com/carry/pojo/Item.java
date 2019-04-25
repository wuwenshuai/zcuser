package com.carry.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer number; //支持人数
    private String type;
    private Integer price;
    private String title;
    private String img;
    private String proportion;
    private Date enddate;
    private Integer mount ; //筹到的金额
    private String detail;
    private String detailbodyimg;
    private String detailheadimg;
}
