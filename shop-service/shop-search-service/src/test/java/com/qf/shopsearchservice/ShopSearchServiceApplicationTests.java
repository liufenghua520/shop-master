package com.qf.shopsearchservice;

import com.qf.entity.Goods;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopSearchServiceApplicationTests {

    @Autowired
    private SolrClient solrClient;

    @Test       //id相同则为修改不同为添加
    public void add() throws IOException, SolrServerException {

        SolrInputDocument document = new SolrInputDocument();
        document.addField("id",12);  //不加则自动生成一个id
        document.addField("gname","兰博基尼");
        document.addField("ginfo","Lamborghini\n" +
                "对兰博基尼而言，革命性创新深植于品牌核心：由航空科学所激发的设计灵感，以及V12发动机和碳纤维等科技的运用，令突破陈规成为品牌哲学的一部分。"
                + "\n" +
                "全新Aventador的诞生，正因突破自身的表现，令这款旗舰车型迅速成为超跑界的标杆，引领未来汽车潮流。是一个已成传奇的超级跑车家族。");
        document.addField("gimage","group1/M00/00/00/wKh2qF0lyb6AephlAAR_3NTBjmI739.jpg");
        document.addField("gprice",36899860.00);
        document.addField("gsave",50);

        solrClient.add(document);
        solrClient.commit();
    }

    @Test   //id相同则为修改不同为添加
    public void update(){

    }

    @Test
    public void delete() throws IOException, SolrServerException {
        solrClient.deleteById("1");     //根据id删除
//      solrClient.deleteByQuery("*:*");//根据查询结果删除，查询的结果全部删除
        solrClient.commit();
    }

    @Test
    public void query() throws IOException, SolrServerException {
        SolrQuery query = new SolrQuery("*:*");
        QueryResponse response = solrClient.query(query);
        SolrDocumentList results = response.getResults();
        for (SolrDocument document : results) {
            Goods goods = new Goods();

            goods.setId(Integer.parseInt(document.getFieldValue("id")+""));
            goods.setGname(document.getFieldValue("gname")+"");
            goods.setGimage(document.getFieldValue("gimage")+"");
            goods.setGsave(Integer.parseInt(document.getFieldValue("gsave")+""));
            Object gprice = document.getFieldValue("gprice");
        }
    }
}
