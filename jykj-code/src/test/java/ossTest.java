
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.dome.szjykjcompany.SzjykjCompanyApplication;
import com.dome.szjykjcompany.aliyun.oss.PutObjectProgressListener;
import com.dome.szjykjcompany.aliyun.oss.ossTools;
import com.dome.szjykjcompany.pojo.SysUser;
import com.dome.szjykjcompany.pojo.SysUserExtend;
import com.dome.szjykjcompany.service.IsUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.Date;
import java.util.Random;

/**
 * @ClassName ossTest
 * @Deacription TODO
 * @Author MI
 * @Date 2020/12/29 9:00
 * @Version 1.0
 **/
@SpringBootTest(classes = SzjykjCompanyApplication.class)
@RunWith(SpringRunner.class)
public class ossTest {

    @Autowired
    private ossTools oss;

    @Autowired
    private IsUserService isUserService;


    @Test
    public void test_01() {
        OSSClient ossClient = oss.getOSSClient();
        //上传文件
        String files = "E:\\bg.png";
        String[] file = files.split(",");
        for (String filename : file) {
            //System.out.println("filename:"+filename);
            File filess = new File(filename);
            String md5key = oss.uploadObject2OSS(ossClient, filess, oss.bucketName, oss.folder);
            System.out.println("md5key = " + md5key);
            //logger.info("上传后的文件MD5数字唯一签名:" + md5key);
            //上传后的文件MD5数字唯一签名:40F4131427068E08451D37F02021473A
        }
    }

    @Test
    public void test_02() {
        File file = new File("E:\\向绍高.jpg");
        // 创建OSSClient实e例。
        OSS ossClient = new OSSClientBuilder().build(oss.endpoint, oss.accesskeyid, oss.accesskeysecret);
        try {
            // 上传文件的同时指定了进度条参数。
            ossClient.putObject(new PutObjectRequest(oss.bucketName, oss.folder + "/" + file.getName(), file).withProgressListener(new PutObjectProgressListener()));
            Date expiration = new Date(new Date().getTime() + 1000000000 * 1000000000);
            String url = ossClient.generatePresignedUrl(oss.bucketName, oss.folder + "/" + file.getName(), expiration).toString();
            // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
            url.substring(0,54);
            System.out.println(url);

        } catch (Exception e) {
            e.printStackTrace();
        }
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    @Test
    public void test_09() {
        String str="http://szjykj.oss-cn-hangzhou.aliyuncs.com/jykj-image/";
        System.out.println("str.length() = " + str.length());

        System.out.println("new Random().nextInt(100000000) = " + new Random().nextInt(100000000));
    }
}

