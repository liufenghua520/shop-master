package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.GoodsMapper;
import com.qf.dao.ShopCartMapper;
import com.qf.entity.Goods;
import com.qf.entity.ShopCart;
import com.qf.entity.User;
import com.qf.service.IShopCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/22 17:14
 */
@Service
public class ShopCartServiceImpl implements IShopCartService {

    @Autowired
    private ShopCartMapper shopCartMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public int insert(ShopCart shopCart, User user, String cartToken) {
        //计算购物车小计
        Goods goods = goodsMapper.selectById(shopCart.getGid());

        BigDecimal gprice = goods.getGprice();
        int gnumber = shopCart.getGnumber();

        BigDecimal sprice = gprice.multiply(BigDecimal.valueOf(gnumber));
        shopCart.setSprice(sprice);
        shopCart.setCreatetime(new Date());

        if (user!=null){
            //说明已经的登陆，插入数据库中
            shopCart.setUid(user.getId());

            shopCartMapper.insert(shopCart);
        }else {
            redisTemplate.opsForList().leftPush(cartToken,shopCart);
        }

        return 1;
    }

    /**
     * 查询购物车列表
     * @param user
     * @param cartToken
     * @return
     */
    @Override
    public List<ShopCart> queryCartList(User user, String cartToken) {
        List<ShopCart> cartList = null;

        if (user!=null){
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("uid",user.getId());
            cartList = shopCartMapper.selectList(wrapper);
        }else {
            if (cartToken!=null){
                Long size = redisTemplate.opsForList().size(cartToken);
                cartList = redisTemplate.opsForList().range(cartToken, 0, size);
            }
        }

        if (cartList!=null){
            for (ShopCart shopCart : cartList) {
                Goods goods = goodsMapper.selectById(shopCart.getGid());
                shopCart.setGoods(goods);
            }
        }

        return cartList;
    }

    /**
     * 合并临时购物车
     * @param cartToken
     * @param user
     * @return
     */
    @Override
    public int mergeCarts(String cartToken, User user) {
        
        if (cartToken!=null){
            Long size = redisTemplate.opsForList().size(cartToken);
            List<ShopCart> carts = redisTemplate.opsForList().range(cartToken, 0, size);
            for (ShopCart cart : carts) {
                cart.setUid(user.getId());
                shopCartMapper.insert(cart);
            }
            //清空redis
            redisTemplate.delete(cartToken);
            return 1;
        }
        return 0;
    }

    @Override
    public int deleteCart(User user) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("uid",user.getId());
        return shopCartMapper.delete(wrapper);
    }
}
