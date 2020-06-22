import edu.gdpu.myssm.Application;
import edu.gdpu.myssm.ApplicationContext;
import edu.gdpu.myssm.controller.HelloController;
import org.junit.Test;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月22日 23:55:46
 */
public class TestS {
    @Test
    public void testInject(){
        Application.run(Application.class);
        HelloController hellocontroller = (HelloController) ApplicationContext.getApplicationContext().getBeans().get("hellocontroller");
    }
}
