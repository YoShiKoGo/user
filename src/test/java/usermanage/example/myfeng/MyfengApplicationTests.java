package usermanage.example.myfeng;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import usermanage.example.myfeng.po.UserPo;
import usermanage.example.myfeng.util.DateUtil;

import java.util.Date;

@SpringBootTest
class MyfengApplicationTests {

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    void sdf() {
        UserPo userPo = new UserPo();
        userPo.setAccount("admin");
        userPo.setAccountId("admin");
        userPo.setPassword("c1a233f4654ed8ac5977639ce753347720b785e84088312931e4001771e7811e28d49");
        userPo.setLevel("1");
        userPo.setNick_name("admin");
        userPo.setRole("admin");
        userPo.setMobilePhone("1552366633");
        userPo.setEmail("3423@qq.com");
        userPo.setCreate_time(DateUtil.parseDateToStr(new Date(),
                DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));

        UserPo userPo2 = mongoTemplate.insert(userPo);
        Query query = new Query(Criteria.where("accountId").is("admin"));
        UserPo userPo1 = mongoTemplate.findOne(query, UserPo.class);

        System.out.println("sdfsdf-------------------");
        System.out.println(userPo1);

    }

}
