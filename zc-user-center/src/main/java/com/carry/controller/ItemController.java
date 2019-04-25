package com.carry.controller;

import com.carry.mapper.ItemMapper;
import com.carry.pojo.Item;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
@Controller
public class ItemController {

    @Autowired
    private ItemMapper itemMapper;


    @RequestMapping("/project")
    public String project(Model model,Integer id){
        //根据id查询商品详情页面
        Item item = itemMapper.selectByPrimaryKey(id);
        model.addAttribute("item",item);
        return "project.html";
    }

    @RequestMapping("/pay-step-1")
    public String  pay(){
        return "pay-step-1.html";
    }

    @RequestMapping("/pay-step-2")
    public String  pay2(){
        return "pay-step-2.html";
    }

    @RequestMapping("/projects")
    public String  projects(String title,Model model,Integer pageNum,Integer pageSize){
        if (pageNum == null){
            pageNum = 1;
        }
        pageSize = 4;
        PageHelper.startPage(pageNum,pageSize);
        //设置查询条件，根据title进行模糊查询
        Example example = new Example(Item.class);
        if (title==null){
            title = "";
        }
        example.createCriteria().andLike("title","%"+title+"%");
        List<Item> items = itemMapper.selectByExample(example);
        if (items==null || items.size()==0){
            return null;
        }
        List<Item> items1 = itemMapper.selectAll();
        //计算分页条数
        int size = items1.size();
        Integer ceil = (int) Math.ceil(size / pageSize);
        List<Integer> ceilList = new ArrayList<>();
        for (int i = 1;i<=ceil;i++){
            ceilList.add(i);
        }
        model.addAttribute("ceilList",ceilList);
        model.addAttribute("size",size);
        model.addAttribute("title",title);
        model.addAttribute("items",items);
        return "projects.html";
    }






    @GetMapping("/")
    public String index(Model model){
        Item item = new Item();
        item.setType("科技开启智慧未来");
        List<Item> items = itemMapper.select(item);
        model.addAttribute("items",items);
        return "index";
    }

    @GetMapping("/items")
    @ResponseBody
    public List<Item> itemResponses(){
        Item item = new Item();
        item.setType("科技开启智慧未来");

        List<Item> byType = itemMapper.select(item);
        return byType;
      /*  //包装返回对象
        List<ItemResponse> responseList = new ArrayList<>();
        ItemResponse itemResponse = new ItemResponse();
        List<Item> itemList = new ArrayList<>();

        List<Item> items = itemMapper.selectAll();
        String type = "";
        for (Item item : items) {
            if (itemList == null || itemList.size()==0){
                //第一次
                itemResponse.setType(item.getType());
                itemList.add(item);
                itemResponse.setItems(itemList);
            }else {
                //不是第一次,判断类型是不是一样的
                int size = itemList.size();
               for (int i = 0; i<size;i++){
                   if (item.getType() == itemList.get(i).getType()){
                       itemList.add(item);
                       itemResponse.setItems(itemList);
                   }else {
                       itemResponse.setType(item.getType());
                       itemList.add(item);
                       itemResponse.setItems(itemList);
                   }
               }



            }
        }
        responseList.add(itemResponse);
        return responseList;*/
    }




}
