package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.entity.Goods;
import com.qf.service.ISearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/11 17:27
 */
@Service
public class SearchServiceImpl implements ISearchService {

    @Autowired
    private SolrClient solrClient;

    @Override
    public List<Goods> searchBykey(String keyword) {

        SolrQuery solrQuery=null;
        if (keyword==null || keyword.equals("")){
            solrQuery=new SolrQuery("*:*");
        }else {
            String str = "gname:%s || ginfo:%s";
            String s = String.format(str, keyword,keyword);
            solrQuery = new SolrQuery(s);
        }

        //设置查询结果的关键字高亮显示
        solrQuery.setHighlight(true);   //开启高亮
        solrQuery.setHighlightSimplePre("<font color='red'>");  //设置关键字的前缀
        solrQuery.setHighlightSimplePost("</font>");        //设置关键字的后缀
        solrQuery.addHighlightField("gname");

        //设置高亮的折叠
        //solrQuery.setHighlightSnippets(3);//折叠几次高亮内容
        //solrQuery.setHighlightFragsize(10);//每次折叠的内容长度

        List<Goods> goodsList = new ArrayList<>();
        try {
            //查询返回的响应对象
            QueryResponse response = solrClient.query(solrQuery);

            //通过响应对象返回的结果集（无高亮效果）
            SolrDocumentList results = response.getResults();

            //获得有高亮效果的结果集
            //Map<有高亮的商品id号, Map<有高亮的字段, List<多个高亮的内容>>>
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();

            for (SolrDocument document : results) {

                //遍历结果集获得字段值
                Goods goods = new Goods();
                int id = Integer.parseInt(document.getFieldValue("id")+"");
                String gname = document.getFieldValue("gname")+"";
                String gimage = document.getFieldValue("gimage")+"";
                BigDecimal gprice = new BigDecimal(document.getFieldValue("gprice")+"");
                int gsave = (int) document.getFieldValue("gsave");

                goods.setId(id);
                goods.setGname(gname);
                goods.setGimage(gimage);
                goods.setGprice(gprice);
                goods.setGsave(gsave);

                if (highlighting.containsKey(id+"")){
                    Map<String,List<String>> stringListMap = highlighting.get(id+"");
                    if (stringListMap.containsKey("gname")){
                        String highlightgname = stringListMap.get("gname").get(0);
                        goods.setGname(highlightgname);
                    }

                }

                goodsList.add(goods);
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return goodsList;
    }

    @Override
    public int add(Goods goods) {
        SolrInputDocument document = new SolrInputDocument();

        document.setField("id",goods.getId());
        document.setField("gname",goods.getGname());
        document.setField("gimage",goods.getGimage());
        document.setField("ginfo",goods.getGinfo());
        document.setField("gprice",goods.getGprice().floatValue());
        document.setField("gsave",goods.getGsave());

        try {
            solrClient.add(document);
            solrClient.commit();
            return 1;
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
