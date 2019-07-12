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
        document.addField("id",1);  //不加则自动生成一个id
        document.addField("gname","小天鹅全自动滚筒洗衣机洗衣机洗衣机洗衣机");
        document.addField("ginfo","省水省电，安静无噪音的一款全自动洗衣机");
        document.addField("gimage","http://mm.midea.com/group1/M00/01/BD/ChAaY1oeUauAae7MAAYdq0AzW3U353.jpg");
        document.addField("gprice","3668.99");
        document.addField("gsave",500);

        solrClient.add(document);
        solrClient.commit();
    }

    @Test   //id相同则为修改不同为添加
    public void update(){

    }

    @Test
    public void delete() throws IOException, SolrServerException {
        solrClient.deleteById("63d50eae-5f89-432a-9051-5b7394dad243");     //根据id删除
        //solrClient.deleteByQuery("*:*");//根据查询结果删除，查询的结果全部删除
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
