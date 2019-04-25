package com.carry.response;

import com.carry.pojo.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponse {

    private String type;
    private List<Item> items;
}
