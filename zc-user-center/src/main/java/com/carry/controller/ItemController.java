package com.carry.controller;

import com.carry.mapper.AmountMappler;
import com.carry.mapper.ItemMapper;
import com.carry.mapper.ProjectMapper;
import com.carry.pojo.Amount;
import com.carry.pojo.Item;
import com.carry.pojo.Project;
import com.carry.vo.EchartsVo;
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

    @Autowired
    private ProjectMapper projectMapper;


    @RequestMapping("/project")
    public String project(Model model,Integer id){
        //根据id查询商品详情页面
        Item item = itemMapper.selectByPrimaryKey(id);
        model.addAttribute("item",item);
        return "project.html";
    }

    @RequestMapping("/pay-step-1")
    public String  pay(Integer money,Model model,Integer itemId){
        System.out.println("111:"+itemId);
        model.addAttribute("money",money);
        model.addAttribute("itemId1",itemId);
        //根据商品id查询商品信息
        Project pj = new Project();
        pj.setItemid(itemId);
        Project project = projectMapper.selectOne(pj);
        model.addAttribute("project",project);
        return "pay-step-1.html";
    }

    @RequestMapping("/pay-step-2")
    public String  pay2(Integer num,Integer money,Model model,Integer itemId){

        System.out.println("---"+itemId);

        //根据商品id查询商品信息
        Project pj = new Project();
        pj.setItemid(itemId);
        Project project2 = projectMapper.selectOne(pj);
        model.addAttribute("project2",project2);
        model.addAttribute("itemId2",itemId);
        model.addAttribute("num2",num);
        model.addAttribute("money2",money);
        return "pay-step-2.html";
    }

    @RequestMapping("/member")
    public String  member(){
        return "member.html";
    }

    @Autowired
    private AmountMappler amountMappler;
    //报表信息
    @RequestMapping("/member/money")
    @ResponseBody
    public EchartsVo echartsVo(){
        //实体类
        EchartsVo echartsVo = new EchartsVo();
        //集合
        List<Integer> data = new ArrayList<>();
        List<String> categories = new ArrayList<>();
        //从数据库获取所有的日期按日期排序
        Example example = new Example(Amount.class);
        Example.Criteria criteria = example.createCriteria();
        example.setOrderByClause("createtime ASC");
        List<Amount> amounts =  amountMappler.selectByExample(example);
        amounts.forEach((amount)->{
            categories.add(amount.getCreatetime());
        });
        for (String name : categories) {
            //根据日期来获取对应的金额
            Amount amount = new Amount();
            amount.setCreatetime(name);
            Amount amount1 = amountMappler.selectOne(amount);
            //放入集合中
            data.add(amount1.getAmount());
        }
        //放到实体类中
        echartsVo.setCategories(categories);
        echartsVo.setData(data);
        return  echartsVo;
    }


    @RequestMapping("/minecrowdfunding")
    public String  minecrowdfunding(){
        return "minecrowdfunding.html";
    }

    @RequestMapping("/projects")
    public String  projects(String title,Model model,Integer pageNum,Integer pageSize){
        if (pageNum == null){
            pageNum = 1;
        } if (pageSize == null){
            pageSize = 4;
        }
       // pageSize = 4;
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


    //跳转到支持金额面
    /*@RequestMapping("/project/support")
    public void support(Integer id,Model model){
        Item item = itemMapper.selectByPrimaryKey(id);
        model.addAttribute("item1",item);
    }*/





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
