package k.kk.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import k.kk.domain.TbGoods;
import k.kk.domain.TbGoodsDesc;
import k.kk.domain.TbItem;
import k.kk.domain.TbItemExample;
import k.kk.groupentity.Goods;
import k.kk.mapper.TbGoodsDescMapper;
import k.kk.mapper.TbGoodsMapper;
import k.kk.mapper.TbItemCatMapper;
import k.kk.mapper.TbItemMapper;
import k.kk.service.ItempageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Service
public class ItempageServiceImpl implements ItempageService {

    @Autowired
    private TbGoodsMapper tbGoodsMapper;

    @Autowired
    private TbGoodsDescMapper tbGoodsDescMapper;

    @Autowired
    private TbItemMapper tbItemMapper;

    @Autowired
    private TbItemCatMapper tbItemCatMapper;


    // Find by Goods (SPU) primary key
    @Override
    public Goods findByGoodsId(Long goodsId) {

        // Target: Create Goods object by combining related entities
        Goods goods = new Goods();

        // Get the TbGoods (SPU) object
        TbGoods tbGoods = tbGoodsMapper.selectByPrimaryKey(goodsId);
        goods.setTbGoods(tbGoods);

        // Get the TbGoodsDesc (SPU description) object
        TbGoodsDesc tbGoodsDesc = tbGoodsDescMapper.selectByPrimaryKey(goodsId);
        goods.setTbGoodsDesc(tbGoodsDesc);

        // Get the list of TbItem (SKU) objects
        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andGoodsIdEqualTo(goodsId);
        List<TbItem> itemList = tbItemMapper.selectByExample(example);
        goods.setItemList(itemList);

        // Set category names in a map
        Map<String, String> categoryMap = new HashMap<>();
        categoryMap.put("category1", tbItemCatMapper.selectByPrimaryKey(tbGoods.getCategory1Id()).getName());
        categoryMap.put("category2", tbItemCatMapper.selectByPrimaryKey(tbGoods.getCategory2Id()).getName());
        categoryMap.put("category3", tbItemCatMapper.selectByPrimaryKey(tbGoods.getCategory3Id()).getName());
        goods.setCategoryMap(categoryMap);

        return goods;
    }

    // Get all Goods data
    @Override
    public List<Goods> findAll() {

        // Target: Create a list of Goods objects
        List<Goods> goodsList = new ArrayList<>();

        // Get the list of TbGoods (SPU) objects
        List<TbGoods> tbGoods = tbGoodsMapper.selectByExample(null);

        // Iterate through the TbGoods list
        for (TbGoods tbGood : tbGoods) {

            Goods goods = findByGoodsId(tbGood.getId());
            goodsList.add(goods);
        }
        return goodsList;
    }
}
