import com.javalearning.utils.MD5Util;
import com.javalearning.utils.UUIDUtil;
import com.javalearning.workbench.activity.domain.Activity;
import com.javalearning.workbench.activity.domain.ActivityRemark;
import com.javalearning.workbench.activity.service.ActivityRemarkService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

public class Test {

    @org.junit.Test
    public void testUUIDUtil(){
        UUIDUtil uuidUtil = new UUIDUtil();
        String uuid = uuidUtil.getUUID();
        System.out.println(uuid);
        System.out.println(uuid.length());

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext();
    }

    @org.junit.Test
    public void testMD5Util(){
        MD5Util md5Util = new MD5Util();
        String pwd = md5Util.getMD5("1234567qwe");
        System.out.println(pwd);
    }

    @org.junit.Test
    public void testAdd(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        ActivityRemarkService activityRemarkService = (ActivityRemarkService) applicationContext.getBean("arsi");
        ActivityRemark activityRemark = new ActivityRemark();
        activityRemark.setId("01b57470bc4643a1a98e446ca106c280");
        activityRemark.setActivityId("1111111");
        activityRemark.setNoteContent("beizhu222");
        activityRemarkService.add(activityRemark);
    }
}
